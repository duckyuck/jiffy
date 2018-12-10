(ns jiffy.imports)

(defmacro import-def
  "import a single fn or var
   (import-def a b) => (def b a/b)
  "
  [from-ns def-name]
  (let [from-sym# (symbol (str from-ns) (str def-name))]
    `(def ~def-name ~from-sym#)))

(defmacro import-vars
  "import multiple defs from multiple namespaces
   works for vars and fns. not macros.
   (same syntax as potemkin.namespaces/import-vars)
   (import-vars
     [m.n.ns1 a b]
     [x.y.ns2 d e f]) =>
   (def a m.n.ns1/a)
   (def b m.n.ns1/b)
    ...
   (def d m.n.ns2/d)
    ... etc
  "
  [& imports]
  (let [expanded-imports (for [[from-ns & defs] imports
                               d defs]
                           `(import-def ~from-ns ~d))]
    `(do ~@expanded-imports)))
