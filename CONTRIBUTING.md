# Common patterns

## Exception handling

All exceptions defined in java.time has their corresponding representation in Jiffy's `jiffy.exception` namespace.
Since java.time's exceptions form a hierarchy, the corresponding hierarchy needs to be reflected when porting
the code to Jiffy. To accomodate this, a custom macro called `try*` has been created to support this
behaviour. This should be used as a drop in replacement for porting `try/catch` statements.
Jiffy also ships with a function, `jiffy.exception/ex` for creating exceptions at runtime. Jiffy's `try*` macro also
handles the differences between Clojure and Clojurescript with regards to exception handling.

### Throwing exceptions

Consider this Java code:

```java
throw ZoneRulesException("Problem with zone rules");
```

To port this code to Jiffy, we'll start with requiring the `ex` function (for creating the exception) as well
as the corresponding exception type.

```clj
(:require [jiffy.exception :refer [ZoneRulesException ex]]
```

Porting the `throw` statement, we end up with the following:

```clj
(throw (ex ZoneRulesException "Problem with zone rules"))
```

### Catching exceptions

Let's say we were to port the following code from java.time;

```java
try {
    return getRules();
} catch (ZoneRulesException ex) {
    return null;
}
```

First we'll need to require the `try*`-macro as well as the exception type from `jiffy.exception`;

```clj
[jiffy.exception :refer [ZoneRulesException #?@(:clj try*)] #?@(:cljs [:refer-macros [try*]])]
```

The catch-statement should be ported like this;

```clj
(try*
  (ZoneId/getRules)
  (catch ZoneRulesException ex))
```

Exception classes thrown in java.time, but defined in java.lang and elsewhere (e.g. `IllegalStateException`), are also ported to
Jiffy. These exceptions also reside in the `jiffy.exception` namespace, although they have "Java" as a prefix to their name,
e.g. `jiffy.exception/JavaIllegalStateException`.
