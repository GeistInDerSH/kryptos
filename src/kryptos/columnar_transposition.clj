(ns kryptos.columnar-transposition
  (:require [clojure.math :as math])
  (:import (clojure.lang PersistentHashMap)))

(defn- generate-columnar-matrix
  "Generate a 2d Java array with the text in the columnar order"
  {:tag "[[Ljava.lang.Character;"
   :static true}
  [text n-rows n-cols]
  (let [arr   (make-array Character/TYPE n-rows n-cols)
        index (atom 0)]
    (doseq [col (range n-cols)
            row (range n-rows)]
      (aset arr row col (get text @index))
      (reset! index (inc @index)))
    arr))

(defn- generate-decipher-matrix
  "Generate a 2d Java array to use to decode the cipher text"
  {:tag "[[Ljava.lang.Character;"
   :static true}
  [key key-map n-rows n-cols cipher-matrix]
  (let [arr (make-array Character/TYPE n-rows n-cols)]
    (doseq [col (range n-cols)
            row (range n-rows)
            :let [char  (get key col)
                  index (get key-map char)
                  value (aget cipher-matrix row index)]]
      (aset arr row col value))
    arr))

(defn- ^PersistentHashMap generate-key-map
  "Generate a mapping of the characters with in the key, to their
   ordinal position.
   e.g. foobar -> {\\a 0, \\b 1, \\f 2, \\o 3, \\r 4}"
  [key]
  (->> (zipmap (sort key)
               (range))
       (into {})))

(defn ^String columnar-transposition
  "https://en.wikipedia.org/wiki/Transposition_cipher#Columnar_transposition"
  [^String text ^String key]
  (let [n-cols (count key)
        n-rows (-> (count text)
                   (/ n-cols)
                   (math/ceil)
                   int)
        key-map         (generate-key-map key)
        cipher-matrix   (generate-columnar-matrix text n-rows n-cols)
        decipher-matrix (generate-decipher-matrix key key-map n-rows n-cols cipher-matrix)]
    (loop [row 0
           col 0
           letters []]
      (if (>= row n-rows)
        (apply str letters)
        (if (>= col n-cols)
          (recur (inc row) 0 letters)
          (let [letter (char (aget decipher-matrix row col))]
            (recur row
                   (inc col)
                   (conj letters letter))))))))

(defn ^String double-columnar-transposition
  "Apply the columnar-transposition function on each of the given keys.
   The output of the first transposition will be used as the input for the second"
  [^String text ^String k1 ^String k2]
  (-> text
      (columnar-transposition k1)
      (columnar-transposition k2)))