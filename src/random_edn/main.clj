(ns random-edn.main
  (:require [clojure.pprint :as pp]
            [clojure.test.check.generators :as gen]
            [clojure.tools.reader.edn :as edn]
            [criterium.core :as criterium])
  (:import (java.io File FileWriter)))

(defn edn-structure [inner]
  (gen/one-of [(gen/list inner)
               (gen/vector inner)
               (gen/map inner inner)
               (gen/fmap set (gen/list inner))]))

(def gen-float
  (gen/fmap (fn [[x y]]
                (/ (double x)
                   (double y)))
              (gen/tuple gen/int gen/int)))

(def edn-leaf
  (gen/one-of
   [gen-float
    gen/int
    gen/char
    gen/string
    gen/ratio
    gen/boolean
    gen/keyword
    gen/keyword-ns
    gen/symbol
    gen/symbol-ns]
    ))

(defn data-file
  ([]
   "data.edn")
  ([nature]
   (str "data-" nature ".edn")))

(defn do-bench [nature]
  (println  (str "Benchmarking " nature))
  (let [data (slurp (data-file nature))]
    (criterium/bench
     (edn/read-string data))))

(defn do-generate [nature]
  (println (str "Generating " nature))
  (let [sample-size (case nature
                      "small" 10
                      "large" 1000)
        data        (gen/sample
                     (gen/recursive-gen edn-structure edn-leaf)
                     sample-size)
        ]
    (with-open [x (FileWriter. (File. (data-file nature)))]
      (pp/pprint data x))))

(defn -main [cmd & args]
  (let [runner (case cmd
                 "generate" do-generate
                 "bench"    do-bench)]
    (apply runner args)))
