(defproject random-edn "0.1.0-SNAPSHOT"
  :dependencies  [[org.clojure/clojure "1.6.0"]
                  [org.clojure/test.check "0.7.0"]
                  [criterium "0.4.3"]]

  :source-paths  ["src"]
  :target-path   "target"

  :main          random-edn.main

  :profiles {:base    {:dependencies [[org.clojure/tools.reader "0.9.2"]]}
             :patched {:dependencies [[org.clojure/tools.reader "0.9.3-PATCHED"]]}}

  :aliases {"bench"
            ["do"
             "clean"

             ["with-profile" "base" "run" "generate" "small"]
             ["with-profile" "base" "run" "generate" "large"]

             ["with-profile" "base:patched" "run" "bench" "small"]
             ["with-profile" "base:patched" "run" "bench" "large"]
             ]})
