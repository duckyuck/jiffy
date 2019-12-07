(ns jiffy.helper.macros
  #?(:cljs (:require [cljs.reader])))

#?(:clj
   (defmacro load-edn-file [filename]
     `(cljs.reader/read-string (str "[" ~(slurp filename) "]"))))

