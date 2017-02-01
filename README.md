# `vat`

[![Build Status](https://semaphoreci.com/api/v1/dotemacs/vat/branches/master/shields_badge.svg)](https://semaphoreci.com/dotemacs/vat)

A Clojure library designed to look up VAT numbers on
[VAT Information Exchange System](http://ec.europa.eu/taxation_customs/vies/)

## Install

```clojure
[vat "0.1.1"]
```

## Usage

```clojure
(require '[vat.core :as vat])
(vat/look-up-number "GB" "678")
```

Or if you just care of the VAT numbers in United Kingdom:

```clojure
(vat/look-up-number "678")
```
