(ns kryptos.core
  (:require [clojure.string :as string]
            [kryptos.ciphers.vigenere :as vigenere]
            [kryptos.ciphers.columnar-transposition :as transposition])
  (:gen-class))

(defn- solution-format [text]
  (->> text
      string/lower-case
      (filter #(Character/isLetter ^Character %))
      (apply str)))

(def ^:static k1 "emufphzlrfaxyusdjkzldkrnshgnfivjyqtquxqbqvyuvlltrevjyqtmkyrdmfd")
(def ^:static k2 "vfpjudeehzwetzyvgwhkkqetgfqjnceggwhkkdqmcpfqzdqmmiagpfxhqrlgtimvmzjanqlvkqedagdvfrpjungeunaqzgzlecgyuxueenjtbjlbqcrtbjdfhrryizetkzemvdufksjhkfwhkuwqlszftihhddduvhdwkbfufpwntdfiycuqzereevldkfezmoqqjlttugsyqpfeunlavidxflggtezfkzbsfdqvgogipufxhhdrkffhqntgpuaecnuvpdjmqclqumunedfqelzzvrrgkffvoeexbdmvpnfqxezlgrednqfmpnzglflpmrjqyalmgnuvpdxvkpdqumebedmhdafmjgznuplgeswjllaetg")
(def ^:static k3 "endyahrohnlsrheocpteoibidyshnaiachtnreyuldsllsllnohsnosmrwxmnetprngatihnrarpeslnneleblpiiacaewmtwnditeenrahcteneudretnhaeoetfolsedtiwenhaeioyteyqheenctaycreiftbrspamhhewenatamategyeerlbteefoasfiotuetuaeotoarmaeertnrtibseddniaahttmstewpieroagriewfebaectddhilceihsitegoeaosddrydloritrklmlehagtdhardpneohmgfmfeuheecdmripfeimehnlssttrtvdohw")
(def ^:static k4 "obkruoxoghulbsolifbbwflrvqqprngkssotwtqsjqssekzzwatjkludiawinfbnypvttmzfpkwgdkzxtjcdigkuhuauekcar")

(def ^:static k1-solution (solution-format "BETWEEN SUBTLE SHADING AND THE ABSENCE OF LIGHT LIES THE NUANCE OF IQLUSION"))
(def ^:static k2-solution (solution-format "IT WAS TOTALLY INVISIBLE HOWS THAT POSSIBLE ? THEY USED THE EARTHS MAGNETIC FIELD X THE INFORMATION WAS GATHERED AND TRANSMITTED UNDERGRUUND TO AN UNKNOWN LOCATION X DOES LANGLEY KNOW ABOUT THIS ? THEY SHOULD ITS BURIED OUT THERE SOMEWHERE X WHO KNOWS THE EXACT LOCATION ? ONLY WW THIS WAS HIS LAST MESSAGE X THIRTY EIGHT DEGREES FIFTY SEVEN MINUTES SIX POINT FIVE SECONDS NORTH SEVENTY SEVEN DEGREES EIGHT MINUTES FORTY FOUR SECONDS WEST X LAYER TWO"))
(def ^:static k3-solution (solution-format "SLOWLY DESPARATLY SLOWLY THE REMAINS OF PASSAGE DEBRIS THAT ENCUMBERED THE LOWER PART OF THE DOORWAY WAS REMOVED WITH TREMBLING HANDS I MADE A TINY BREACH IN THE UPPER LEFT HAND CORNER AND THEN WIDENING THE HOLE A LITTLE I INSERTED THE CANDLE AND PEERED IN THE HOT AIR ESCAPING FROM THE CHAMBER CAUSED THE FLAME TO FLICKER BUT PRESENTLY DETAILS OF THE ROOM WITHIN EMERGED FROM THE MIST X CAN YOU SEE ANYTHING Q ?"))
(def ^:static k4-solution "¯\\_(ツ)_/¯")

(defn get-solutions []
  (let [key    "kryptos"
        sol-k1 (vigenere/decode k1 "palimpsest" key)
        sol-k2 (vigenere/decode k2 "abscissa" key)
        sol-k3 (transposition/double-columnar-transposition k3 "utsrqponmlkjihgfedcba" "~}zyxwvutsrqponmlkjihgfedcba")]
  {:solutions {:k1 sol-k1
               :k2 sol-k2
               :k3 sol-k3}
   :is-correct? {:k1 (= sol-k1 k1-solution)
                 :k2 (= sol-k2 k2-solution)
                 :k3 (= sol-k3 k3-solution)}}))

(defn -main [& _]
  (let [solutions (get-solutions)]
    (doseq [[k v] (:solutions solutions)]
      (printf "%s: %s\n" (name k) v))
    (doseq [[k v] (:is-correct? solutions)]
      (when-not v
        (printf "%s is incorrect\n" (name k)))))
  (println))