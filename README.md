# `vat`

A Clojure library designed to look up VAT numbers on
[VAT Information Exchange System](http://ec.europa.eu/taxation_customs/vies/)

## Usage

```clojure
(require '[vat.core :as vat])
(vat/look-up-number :country-code "GB" :vat-number "678")
```
