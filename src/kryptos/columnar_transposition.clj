(ns kryptos.columnar-transposition
  (:require [clojure.math :as math])
  (:import (clojure.lang PersistentHashMap)))

(defn- generate-columnar-matrix
  "Generate a 2d Java array with the text in the columnar order"
  {:tag "[[Ljava.lang.Character;"
   :static true}
  [text rows cols]
  (let [arr (make-array Character/TYPE rows cols)
        index (atom 0)]
    (dotimes [col cols]
      (dotimes [row rows]
        (aset arr row col (get text @index))
        (reset! index (inc @index))))
    arr))

(defn- generate-decipher-matrix
  "Generate a 2d Java array to use to decode the cipher text"
  {:tag "[[Ljava.lang.Character;"
   :static true}
  [key key-map rows cols cipher-matrix]
  (let [arr (make-array Character/TYPE rows cols)]
    (dotimes [col cols]
      (let [chr (get key col)
            col-index (get key-map chr)]
        (dotimes [row rows]
          (aset arr row col (aget cipher-matrix row col-index)))))
    arr))

(defn- generate-key-map [key]
  (let [index (atom 0)]
    (->> (for [entry (zipmap key (range))
               :let [[k _] entry
                     value [k @index]
                     _ (reset! index (inc @index))]]
           value)
         (into {}))))

(defn ^String columnar-transposition
  "https://en.wikipedia.org/wiki/Transposition_cipher#Columnar_transposition"
  [^String text ^String key]
  (let [cols (count key)
        rows (-> (count text)
                 (/ cols)
                 (math/ceil)
                 int)
        key-map (generate-key-map key)
        cipher-matrix (generate-columnar-matrix text rows cols)
        decipher-matrix (generate-decipher-matrix key key-map rows cols cipher-matrix)]
    (loop [row 0
           col 0
           letters []]
      (if (>= row rows)
        (apply str letters)
        (if (>= col cols)
          (recur (inc row) 0 letters)
          (let [letter (aget decipher-matrix row col)]
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