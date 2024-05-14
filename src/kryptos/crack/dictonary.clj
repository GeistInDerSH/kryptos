(ns kryptos.crack.dictonary)

(def ^:private dict-file "/etc/dictionaries-common/words")

(defn- load-word-dictionary
  "Load words from a file that can be used as a dictionary for trying to break ciphers.
   If no arguments are given, the default unix dictionary will be used.
   If you wish to provide your own dictionary, it must be in the same format (one word per line)"
  ([] (load-word-dictionary dict-file))
  ([dict-file]
   (with-open [fp (clojure.java.io/reader dict-file)]
     (->> (line-seq fp)
          (filter #(= (re-find #"[\x41-\x5A\x61-\x7A]+" %) %))
          (filterv #(not (clojure.string/includes? % "'")))
          (filterv #(> (count %) 1))))))
