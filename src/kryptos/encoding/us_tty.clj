(ns kryptos.encoding.us-tty
  (:require [kryptos.encoding.helper :as helper]))

(def tty-codes {\a "00011" \A "00011"
                \b "11001" \B "11001"
                \c "01110" \C "01110"
                \d "01001" \D "01001"
                \e "00001" \E "00001"
                \f "01101" \F "01101"
                \g "11010" \G "11010"
                \h "10100" \H "10100"
                \i "00110" \I "00110"
                \j "01011" \J "01011"
                \k "01111" \K "01111"
                \l "01010" \L "01010"
                \m "11100" \M "11100"
                \n "01100" \N "01100"
                \o "11000" \O "11000"
                \p "10110" \P "10110"
                \q "10111" \Q "10111"
                \r "01010" \R "01010"
                \s "00101" \S "00101"
                \t "10000" \T "10000"
                \u "00111" \U "00111"
                \v "11110" \V "11110"
                \w "10011" \W "10011"
                \x "11101" \X "11101"
                \y "10101" \Y "10101"
                \z "10001" \Z "10001"})
(def reverse-tty-codes (helper/reverse-map-value tty-codes))