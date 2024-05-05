(ns kryptos.core
  (:require [clojure.string :as string]
            [kryptos.vigenere :as vigenere]))

(defn- solution-format [text]
  (->> text
      string/lower-case
      (filter #(Character/isLetter ^Character %))
      (apply str)))

(def k1 "emufphzlrfaxyusdjkzldkrnshgnfivjyqtquxqbqvyuvlltrevjyqtmkyrdmfd")
(def k2 "vfpjudeehzwetzyvgwhkkqetgfqjnceggwhkkdqmcpfqzdqmmiagpfxhqrlgtimvmzjanqlvkqedagdvfrpjungeunaqzgzlecgyuxueenjtbjlbqcrtbjdfhrryizetkzemvdufksjhkfwhkuwqlszftihhddduvhdwkbfufpwntdfiycuqzereevldkfezmoqqjlttugsyqpfeunlavidxflggtezfkzbsfdqvgogipufxhhdrkffhqntgpuaecnuvpdjmqclqumunedfqelzzvrrgkffvoeexbdmvpnfqxezlgrednqfmpnzglflpmrjqyalmgnuvpdxvkpdqumebedmhdafmjgznuplgeswjllaetg")
(def k3 "endyahrohnlsrheocpteoibidyshnaiachtnreyuldsllsllnohsnosmrwxmnetprngatihnrarpeslnneleblpiiacaewmtwnditeenrahcteneudretnhaeoetfolsedtiwenhaeioyteyqheenctaycreiftbrspamhhewenatamategyeerlbteefoasfiotuetuaeotoarmaeertnrtibseddniaahttmstewpieroagriewfebaectddhilceihsitegoeaosddrydloritrklmlehagtdhardpneohmgfmfeuheecdmripfeimehnlssttrtvdohw")
(def k4 "obkruoxoghulbsolifbbwflrvqqprngkssotwtqsjqssekzzwatjkludiawinfbnypvttmzfpkwgdkzxtjcdigkuhuauekcar")

(def k1-solution (solution-format "BETWEEN SUBTLE SHADING AND THE ABSENCE OF LIGHT LIES THE NUANCE OF IQLUSION"))
(def k2-solution (solution-format "IT WAS TOTALLY INVISIBLE HOWS THAT POSSIBLE ? THEY USED THE EARTHS MAGNETIC FIELD X THE INFORMATION WAS GATHERED AND TRANSMITTED UNDERGRUUND TO AN UNKNOWN LOCATION X DOES LANGLEY KNOW ABOUT THIS ? THEY SHOULD ITS BURIED OUT THERE SOMEWHERE X WHO KNOWS THE EXACT LOCATION ? ONLY WW THIS WAS HIS LAST MESSAGE X THIRTY EIGHT DEGREES FIFTY SEVEN MINUTES SIX POINT FIVE SECONDS NORTH SEVENTY SEVEN DEGREES EIGHT MINUTES FORTY FOUR SECONDS WEST X LAYER TWO"))
(def k3-solution (solution-format "SLOWLY DESPARATLY SLOWLY THE REMAINS OF PASSAGE DEBRIS THAT ENCUMBERED THE LOWER PART OF THE DOORWAY WAS REMOVED WITH TREMBLING HANDS I MADE A TINY BREACH IN THE UPPER LEFT HAND CORNER AND THEN WIDENING THE HOLE A LITTLE I INSERTED THE CANDLE AND PEERED IN THE HOT AIR ESCAPING FROM THE CHAMBER CAUSED THE FLAME TO FLICKER BUT PRESENTLY DETAILS OF THE ROOM WITHIN EMERGED FROM THE MIST X CAN YOU SEE ANYTHING Q ?"))
(def k4-solution "¯\\_(ツ)_/¯")

(defn -main [& _]
  (let [sol-k1 (vigenere/decode k1 "palimpsest" "kryptos")
        sol-k2 (vigenere/decode k2 "abscissa" "kryptos")]
  (println sol-k1)
  (println sol-k2)
  (printf "K1 is correct: %s\n" (= k1-solution sol-k1))
  (printf "K2 is correct: %s\n" (= k2-solution sol-k2))))