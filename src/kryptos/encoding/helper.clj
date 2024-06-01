(ns kryptos.encoding.helper)

(defn reverse-map-value [encoder]
  (zipmap (keys encoder)
          (->> (vals encoder)
               (map #(apply str (reverse %))))))

(defn diff [encoder-1 encoder-2]
  (let [keys-1 (keys encoder-1)
        keys-2 (keys encoder-2)]
    (when (not= keys-1 keys-2)
      (throw (Exception. "Key sets do not equal")))
    (loop [difference {}
           key-set keys-1]
      (if (seq key-set)
        (let [lookup (first key-set)
              todo   (rest key-set)
              vals-1 (get encoder-1 lookup)
              vals-2 (get encoder-2 lookup)]
          (if (= vals-1 vals-2)
            (recur difference todo)
            (recur (conj difference {lookup [vals-1 vals-2]})
                   todo)))
        difference))))