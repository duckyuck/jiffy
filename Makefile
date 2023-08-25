.PHONY: test test-clj test-cljs

test: test-clj test-cljs

test-clj:
	clojure -Mtest-clj

test-cljs:
	clojure -Mtest-cljs

clean:
	rm -rf target/* cljs-test-runner-out
