(ns vat.core-test
  (:require [clojure.test :refer :all]
            [vat.core :refer :all]))

(deftest soap-request-test
  (testing "soap request"
    (let [soap "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:tns1=\"urn:ec.europa.eu:taxud:vies:services:checkVat\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ins0=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\"><env:Body><ins0:checkVat><ins0:countryCode>foo</ins0:countryCode><ins0:vatNumber>bar</ins0:vatNumber></ins0:checkVat></env:Body></env:Envelope>"]
(is (= soap (soap-request "foo" "bar"))))))

(deftest vias-response-parsing
  (testing "vias response"
    (let [response {:opts {:headers {"Content-Type" "text/xml;charset=UTF-8", "SOAPAction" "checkVat"}, :body "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:tns1=\"urn:ec.europa.eu:taxud:vies:services:checkVat\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ins0=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\"><env:Body><ins0:checkVat><ins0:countryCode>GB</ins0:countryCode><ins0:vatNumber>123</ins0:vatNumber></ins0:checkVat></env:Body></env:Envelope>", :method :post, :url "http://ec.europa.eu/taxation_customs/vies/services/checkVatService"}, :body "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><checkVatResponse xmlns=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\"><countryCode>GB</countryCode><vatNumber>123</vatNumber><requestDate>2016-09-05+02:00</requestDate><valid>true</valid><name>SOME COMPANY LIMITED</name><address>145-157 SOME STREET\nLONDON\n\n\n\nSW1</address></checkVatResponse></soap:Body></soap:Envelope>", :headers {:connection "Keep-Alive", :content-encoding "gzip", :content-type "text/xml; charset=UTF-8", :date "Mon, 05 Sep 2016 16:50:52 GMT", :proxy-connection "Keep-Alive", :server "Europa", :transfer-encoding "chunked"}, :status 200}]
        (is (true? (is-valid? (:body response)))))))
