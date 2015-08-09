# Web-schemas

[Prismatic Schema](https://github.com/Prismatic/schema) extensions for the Web.

## Goals

- symmetric serialization & de-serialization of data types via existing libraries & schema coercion
- extendable way to add support for new data types (`Pattern`, `DateTime` etc.) - for all formats
- extendable way to add support for new wire formats (`JSON`, `Transit` etc.) - for all data types
- out-of-the-box support for all [common data types](https://github.com/metosin/ring-swagger#out-of-the-box-supported-schema-elements) & wire formats
  - including the Schema-records
- test suite to verify all data types are supported in all wire formats (no nasty runtime surprises)
- fast, minimalistic single-sweep coercion without reflection
- sits on top of [schema-tools](https://github.com/metosin/schema-tools)

## Public apis

- introduce wire formats (serialize & deserialize)
- link type-based coercion matchers to wire formats
- serialize and deserialize&coerce based on explicit set of protocols.
- coercion matcher also for clojure2clojure - to coerce at least numbers and dates.
- test suite (for custom extension)

## Why?

Single responsibility principle: besides the swagger-stuff, [ring-swagger](https://github.com/metosin/ring-swagger)
also contains symmetric serialization & de-serialization for most data types, but only for JSON & String-based
protocols. It's serialization & coercion mechanisms are currenly hard extend. Export all the good stuff here,
add more coverage, verify and measure performance, make everything extendable and make ring-swagger depend on
this lib.

This lib also will serve the [ring-middleware-format](https://github.com/ngrunwald/ring-middleware-format),
enabling easy support to new wire formats like XML (via wire-format based coercion). We are currently jointly
developing r-m-f with Nils Grünwald.

## License

Copyright © 2014-2015 [Metosin Oy](http://www.metosin.fi)

Distributed under the Eclipse Public License, the same as Clojure.
