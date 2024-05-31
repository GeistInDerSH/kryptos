(ns kryptos.ciphers.vigenere-test
  (:require [clojure.test :refer :all]
            [kryptos.ciphers.vigenere :as vigenere]))

(deftest vigenere
  (testing "encoding"
    (are [plain cipher] (= plain cipher)
      (vigenere/encode "secretmessage" "hidden") "zmfuigtmvvetl")
      (vigenere/encode "secretmessage" "hidden" "kryptos") "qknevwskjjmsz"))
  (testing "decoding"
    (are [plain cipher] (= plain cipher)
      (vigenere/decode "zmfuigtmvvetl" "hidden") "secretmessage"
      (vigenere/decode "qknevwskjjmsz" "hidden" "kryptos") "secretmessage"))