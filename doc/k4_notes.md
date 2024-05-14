# K4 Notes

## Websites
1) https://kryptos.hoerenberg.com/index.php?cat=Kryptos%20K4&page=Introduction
2) https://numberworld.blogspot.com/2020/07/kryptos-cipher-part-4.html
3) http://www.thekryptosproject.com/kryptos/k0-k5/k4.php
4) https://en.wikipedia.org/wiki/Kryptos#Clues_given_for_passage_4

## Baudot
(1) Talks about using 5-bit ASCII codes and XORing them with some value. This is interesting, but ASCII isn't the only
encoding system. Baudot (aka ITA1 and later ITA2) codes are 5-bit encodings for typewriters. 

The original, ITA1, was used mainly in Europe and because all text in K4 seems to be ASCII complaint, we can focus on
the UK variant of the codes. The bits are the same, however for some of the characters in the Europe variant make
space for [Diacritics](https://en.wikipedia.org/wiki/Diacritic).

ITA2 covers Baudot-Murray codes that have a slightly different bit pattern. This was done to reduce ware
on commonly used characters, e.g. e, t, a, etc.

### Websites
* https://en.wikipedia.org/wiki/Baudot_code

## Baudot + Vigenere

The goal here isn't to solve it (well, that would be nice though). Instead, we want to filter for some known words
to help narrow search spaces. We can do the same thing with `berlin` but we don't get any results.
Expanding this with an extended dictionary of 
[370k words](https://raw.githubusercontent.com/dwyl/english-words/master/words_alpha.txt) doesn't seem to help much
either. 
Maybe it's not a Vigenere? It's likely worth doing some kind of frequency analysis on the `pmap` result to get a 
confidence rating to back that up
```clojure
(ns kryptos.example
  (:require [clojure.string :as string] 
            [kryptos.vigenere :as virgnere]
            [kryptos.crack.dictonary :as dictonary]))

(def dict (->> (dictionary/load-word-dictionary)
               (map #'string/lower-case))

(let [text (foo k4 ita1)
      key "kryptos"]
  (->> dict
       (pmap #(vigenere/decode text % key))
       (filterv #(clojure.string/includes? % "east"))))
```