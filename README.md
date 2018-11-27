# jiffy

Jiffy is a port of java.time to Clojurescript and Clojure. Jiffy aims at being at parity with java.time API.

Jiffy is currently work in progress!

## I need your help

Porting the java.time API to Clojurescript is not done in a jiffy. Luckily, java.time ships as an
immutable API, making the job tolerable. Porting the API to Clojurescript should be an exercise in mechanically
rewriting Java to Clojurescript, _without even the need for you to understand the logic behind the code_.

The skeletal structure of the complete API has already been generated from the OpenJDK source code. All the corresponding
constructs for java.time's classes, interface, static methods and fields are already in place and stubbed out.

* For every class file in the java.time, Jiffy has a corresponding namespace. E.g. `jiffy.instant` corresponds to `java.time.Instant`.
* For every interface in java.time, Jiffy has a corresponding protocol. The name of the protocol is named after the corresponding
java.time interface with one tiny exception; Jiffy's protocols are prefixed with the letter 'I', e.g. `ITemporal`.
* For every public static methods and fields in java.time, Jiffy has a corresponding function.
* For every (concrete) class in java.time, Jiffy has it's corresponding record.
* For every public method in java.time, Jiffy has it's corresponding function.

What's left is replacing the function bodies with actual working, beautiful Clojurescript code.

### Stop the rambling. I wanna contribute already!

0. Install [Clojure CLI](https://clojure.org/guides/getting_started) and launch a Clojurescript REPL via `clj -A:dev`
1. Have a look at the [contribution guideline](CONTRIBUTING.md).
2. Pick any function in this repository missing an implementation (preferrably in the `jiffy` and `jiffy.temporal` namespaces)
3. Navigate to the corresponding java.time implementation by following the link above the function decleration.
4. Type corresponding Clojurescript that compiles!
5. Repeat steps 1 - 4 until exhaustion
6. Submit pull request

If you stumble upon problems and end up actually needing to think while porting, please open an issue describing why.

This port is based on OpenJDK 11+28 (of august 22. 2018). The complete source code can be found [here](https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time).
Please make use this version as reference for your porting efforts.

### I'm not able to run my code without some other parts of the code throwing an exception

Yeah, that's rather unfortunate, but your contribution takes us one step closer to have actual running code.

### What about testing?

Testing will be accomplished in two parts;

* Porting of java.time's unit tests
* Generative tests checking parity between Jiffy and java.time

Feel free to start porting tests, but my main concern at the moment is mechnically porting the java.time implementation; we won't
be able to exercise any base API until the vital parts are in place.

OpenJDK's test classes can be found [here](https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/test/jdk/java/time)
