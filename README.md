# jiffy

Jiffy is a port of Java Time to Clojurescript. As far as possibble it aims at being at parity with Java API.

Jiffy is currently work in progress!

## I need your help

Porting the Java Time API to Clojurescript is not done in a jiffy (pun intended). Luckily, Java Time ships as an
immutable API, making the job tolerable. Porting the API to Clojurescript should be an exercise in mechanically
rewriting Java to Clojurescript, _without even the need for you to understand the logic behind the code_.

The skeletal structure of the complete API has already been generated from the OpenJDK source code. All the corresponding
constructs for Java Time's classes, interface, static methods and fields are already in place and stubbed out.

* For every class file in the Java Time, Jiffy has a corresponding namespace. E.g. `jiffy.instant` corresponds to `java.time.Instant`.
* For every interface in Java Time, Jiffy has a corresponding protocol. The name of the protocol is named after the corresponding
Java interface with one tiny exception; Jiffy's protocols are prefixed with the letter 'I', e.g. `ITemporal` (see Differences for rationale)
* For every public static methods and fields in Java Time, Jiffy has a corresponding function.
* For every (concrete) class in Java Time, Jiffy has it's corresponding record.
* For every public method in Java Time, Jiffy has it's corresponding function.

What's left is replacing the function bodies with actual working, beautiful Clojurescript code.

### Stop the rambling. I wanna contribute already!

1. Pick any function in this repository missing an implementation (preferrably in the `jiffy` and `jiffy.temporal` namespaces)
2. Navigate to the corresponding Java implementation by following the link above the function decleration.
3. Type corresponding Clojurescript that compiles!
4. Repeat!

Have a look at the `jiffy.instant` namespace. It contains a complete port of the `java.time.Instant`.

### I'm not able to run my code without some other parts of the code throwing an exception

Yeah, that's rather unfortunate, but your contribution makes is one step closer to have actual running code.

### What about testing?

Testing will be accomplished in two parts;

* Porting of Java Time's unit tests
* Generative tests checking parity between Jiffy and Java Time

Feel free to start porting tests, but my main concern at the moment is mechnically porting the Java Time implementation; we won't
be able to exercise any base API until the vital parts are in place.
