(ns kryptos.encoding.ascii
  (:require [kryptos.alphabet :as alphabet]))

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

(def ^:static ascii-8-bit (generate-ascii-table 8 alphabet/lower-case))
(def ^:static ascii-7-bit (generate-ascii-table 7 alphabet/lower-case))
(def ^:static ascii-5-bit (generate-ascii-table 5 alphabet/lower-case))