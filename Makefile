.PHONY: test test-clj test-cljs

test: test-clj test-cljs

test-clj:
	clojure -Mtest-clj

test-cljs:
	clojure -Mtest-cljs

watch-clj:
	clojure -Mtest-clj --watch

watch-cljs:
	clojure -Mtest-cljs -x chrome-headless -w src -w dev-resources

clean:
	rm -rf target/* cljs-test-runner-out
