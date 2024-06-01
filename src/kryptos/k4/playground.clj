(ns kryptos.k4.playground
  (:require [clojure.string :as string]
            [kryptos.core :refer [k4]]
            [kryptos.crack.dictionary :as crack]
            [kryptos.encoding.decoder :as decoder]
            [kryptos.encoding.baudot :as baudot]
            [kryptos.encoding.ascii :as ascii]
            [kryptos.encoding.us-tty :as us-tty]
            [kryptos.ciphers.vigenere :as vigenere]))

(def known-words "Known words in the k4 solution" ["clock" "berlin" "east" "northeast"])
(def dict (->> (clojure.java.io/resource "extended-words")
               (crack/load-word-dictionary)))
(def encoders ['baudot/ita1 'baudot/ita2 'baudot/reverse-ita1 'baudot/reverse-ita2
               'ascii/ascii-5-bit
               'us-tty/tty-codes 'us-tty/reverse-tty-codes])

(defn frequency-analysis
  ([] (frequency-analysis  k4 encoders))
  ([text quoted-encoders]
   {:pre [(every? symbol? quoted-encoders)]}
   (->> quoted-encoders
        (map (fn [e]
               (let [encoder-name (name e)
                     encoder      (eval e) ;; Yes I know this is dangerous
                     txt (decoder/decode-string-with-bit-encoder-as-str text encoder)]
                 {encoder-name (frequencies txt)})))
        (into {}))))

(defn brute-force-with-encoder
  "Attempt to brute force with the vigenere cipher and some encoder"
  ([known-word encoder] (brute-force-with-encoder known-word encoder dict))
  ([known-word encoder dictionary]
   (let [text (decoder/decode-string-with-bit-encoder-as-str k4 encoder)
         possible-key "kryptos"]
     (->> dictionary
          (pmap #(vigenere/decode text % possible-key))
          (filterv #(string/includes? % known-word))))))

(defn brute-force-all-known-words-with-encoder [encoder]
  (->> known-words
       (mapv (fn [word]
               {word (brute-force-with-encoder word encoder)}))
       (into {})))