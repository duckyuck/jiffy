(ns jiffy.helper.macros
  #?(:cljs (:require [cljs.reader]
                     [jiffy.edn.readers])))

#?(:clj
   (defmacro load-edn-file [filename]
     `(cljs.reader/read-string
       {:readers jiffy.edn.readers/data-readers}
       (str "[" ~(slurp filename) "]"))))

