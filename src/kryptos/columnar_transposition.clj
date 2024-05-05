(ns kryptos.columnar-transposition
  (:require [clojure.math :as math]))

(defn- generate-columnar-matrix
  "Generate a 2d Java array with the text in the columnar order"
  [text rows cols]
  (let [raw (-> (for [_ (range rows)]
                  (vec (repeat cols \space)))
                vec)
        arr (clojure.core/to-array-2d raw)
        index (atom 0)]
    (dotimes [col cols]
      (dotimes [row rows]
        (aset arr row col (get text @index))
        (reset! index (inc @index))))
    arr))

(defn- generate-decipher-matrix
  "Generate a 2d Java array to use to decode the cipher text"
  [key key-map rows cols cipher-matrix]
  (let [raw (-> (for [_ (range rows)]
                  (vec (repeat cols \space)))
                vec)
        arr (clojure.core/to-array-2d raw)]
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

(defn columnar-transposition
  "https://en.wikipedia.org/wiki/Transposition_cipher#Columnar_transposition"
  [text key]
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

(defn double-columnar-transposition
  "Apply the columnar-transposition function on each of the given keys.
   The output of the first transposition will be used as the input for the second"
  [text k1 k2]
  (-> text
      (columnar-transposition k1)
      (columnar-transposition k2)))