(ns kryptos.encoding.ascii
  (:require [kryptos.alphabet :as alphabet]
            [kryptos.encoding.helper :as helper]))

(defn generate-ascii-table
  ([] (generate-ascii-table 8 alphabet/lower-case))
  ([n-bits] (generate-ascii-table n-bits alphabet/lower-case))
  ([n-bits alphabet]
   (->> alphabet
        (map (fn [c]
               (let [binary (-> (int c)
                                Integer/toBinaryString)
                     bits   (->> (take n-bits binary)
                                 (apply str))]
                 {c bits})))
        (into {}))))

(defn int->binary-string
  ([i] (int->binary-string i 5))
  ([i length]
   (let [b-string (Integer/toBinaryString i)
         pad-len  (- length (count b-string))
         padding  (apply str (repeat pad-len "0"))]
     (str padding b-string))))

(def ^:static ascii-8-bit (generate-ascii-table 8 alphabet/lower-case))
(def ^:static ascii-7-bit (generate-ascii-table 7 alphabet/lower-case))
(def ^:static ascii-5-bit (generate-ascii-table 5 alphabet/lower-case))
(def ^:static reverse-ascii-5-bit (helper/reverse-map-value ascii-5-bit))
(def ^:static int-bits (zipmap alphabet/lower-case
                               (map int->binary-string
                                    (range))))
(def ^:static reverse-int-bits (helper/reverse-map-value int-bits))