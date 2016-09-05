# `vat`

A Clojure library designed to look up VAT numbers on
[VAT Information Exchange System](http://ec.europa.eu/taxation_customs/vies/)

## Usage

```clojure
(require '[vat.core :as vat])
(vat/look-up-number "GB" "678")
```

Or if you just care of the VAT numbers in United Kingdom:

```clojure
(vat/look-up-number "678")
```
