(ns kryptos.encoding.decoder
  (:require [kryptos.alphabet :as alphabet]))

(defn- get-converter-func [conversion-map]
  (fn [chr]
    (let [code (get conversion-map chr)
          as-int (Integer/valueOf code 2)
          pos (-> (bit-xor (int chr) as-int)
                  (mod 26))]
      (nth alphabet/lower-case pos))))

(defn decode-string-with-bit-encoder
  "Attempt to decode the given text using the conversion map"
  [text conversion-map]
  (let [func (get-converter-func conversion-map)]
    (->> text
         (mapv func))))

(defn decode-string-with-bit-encoder-as-str
  [text conversion-map]
  (apply str
         (decode-string-with-bit-encoder-as-str text conversion-map)))