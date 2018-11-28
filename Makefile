.PHONY: test test-clj test-cljs

test: test-clj test-cljs

test-clj:
	clojure -Atest-clj

test-cljs:
	clojure -Atest-cljs
