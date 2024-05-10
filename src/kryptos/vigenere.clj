(ns kryptos.vigenere
  (:require [clojure.string :as string]
            [clojure.math :as math]))

(def ^:private ^:static default-alphabet "abcdefghijklmnopqrstuvwxyz")

(defn- ^String generate-keyed-alphabet
  "Generate an alphabet that starts with the given key.
   The key must not have any repeated characters, and must
   not add any new characters to the given-alphabet"
  ([key] (generate-keyed-alphabet key default-alphabet))
  ([key given-alphabet]
   {:pre [(= (count given-alphabet) ;; Check that no chars are added to the alphabet by the key
             (->> (concat key given-alphabet)
                  (into #{})
                  count))
          (= (count key) ;; Ensure the key doesn't repeat characters
             (count (into #{} key)))]}
   (loop [chrs     (seq key)
          alphabet given-alphabet]
     (if-let [[chr & rst] chrs]
       (recur rst
              (string/replace alphabet
                              (str chr)
                              ""))
       (str key alphabet)))))

(defn- ^String extend-key-to-text
  "Repeatedly extend the key until the length matches the
   given text."
  [text key]
  (let [text-len     (count text)
        repeat-count (math/ceil (/ text-len
                                   (count key)))]
  (->> key
       (repeat repeat-count)
       (take text-len)
       (apply str))))

(defn- ^String worker
  "Worker does the actual work of encoding/decoding a Virgenere
   cipher.
   Because the logic is the same for encoding/decoding, the only
   change is the reducer function to get the position of the
   character."
  [text key alpha-key reducer]
  {:pre [(some? (get #{#'- #'+} reducer))]}
  (let [keyed-alphabet (generate-keyed-alphabet alpha-key)
        pos-map        (zipmap keyed-alphabet (range))
        extended-key   (extend-key-to-text text key)
        pairs          (mapv vector text extended-key)]
    (loop [plain-text []
           pairs      pairs]
      (if (seq pairs)
        (let [[pair & remainder] pairs
              t-char (first pair)
              k-char (peek pair)
              t-pos  (get pos-map t-char)
              k-pos  (get pos-map k-char)
              pos    (-> (reducer t-pos k-pos)
                         (mod 26))]
          (recur (conj plain-text (get keyed-alphabet pos))
                 remainder))
        (apply str plain-text)))))

(defn ^String encode
  ([^String text ^String key] (encode text key ""))
  ([^String text ^String key ^String alpha-key] (worker text key alpha-key #'+)))

(defn ^String decode
  ([^String text ^String key] (decode text key ""))
  ([^String text ^String key ^String alpha-key] (worker text key alpha-key #'-)))