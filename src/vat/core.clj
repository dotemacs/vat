(ns vat.core
  (:require [org.httpkit.client :as http]
            [clojure.xml :as xml]
            [clojure.data.xml :refer [element emit-str]]))

(defn soap-envelope
  "return soap envelope for VIAS SOAP service"
  []
  {:xmlns:xsd "http://www.w3.org/2001/XMLSchema"
   :xmlns:xsi "http://www.w3.org/2001/XMLSchema-instance"
   :xmlns:tns1 "urn:ec.europa.eu:taxud:vies:services:checkVat"
   :xmlns:env "http://schemas.xmlsoap.org/soap/envelope/"
   :xmlns:ins0 "urn:ec.europa.eu:taxud:vies:services:checkVat:types"})

(defn soap-body
  "prepare the SOAP body with `country-code` & `vat-number`"
  [country-code vat-number]
  (element :env:Body {}
           (element :ins0:checkVat {}
                    (element :ins0:countryCode {} country-code)
                    (element :ins0:vatNumber {} vat-number))))

(defn soap-request
  [country-code vat-number]
  (emit-str
   (element :env:Envelope (soap-envelope)
            (soap-body country-code vat-number))))

(defn post-to-vias
  [soap-request]
  (http/post "http://ec.europa.eu/taxation_customs/vies/services/checkVatService"
            {:headers {"Content-Type" "text/xml;charset=UTF-8"
                       "SOAPAction" "checkVat"}
             :body soap-request}))


(defn xml-to-hashmap
  "convert XML to a hashmap"
  [xml]
  (xml/parse (java.io.ByteArrayInputStream. (.getBytes xml))))

(defn is-valid?
  "parse the VIAS response"
  [result]
  (-> (xml-to-hashmap result)
      :content
      first
      :content
      first
      :content
      (nth 3)
      :content
      first
      Boolean/valueOf
      boolean))

(defn look-up-number
  "looks up a `vat-number` for a given `country-code`
  returns either true or false if the request succeeds
  or a hash map with {:error {:status status :body body}}"
  [& {:keys [country-code vat-number]}]
  (let [response @(post-to-vias (soap-request country-code vat-number))]
    (if (= 200 (:status response))
      (is-valid? (:body response))
      {:error {:status (:status response) :body (:body response)}})))
