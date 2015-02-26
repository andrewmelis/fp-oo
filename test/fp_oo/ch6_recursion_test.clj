(ns fp-oo.ch6-recursion-test
  (require [clojure.test :refer :all]
           [fp-oo.ch6-recursion :refer :all]))

(deftest factorial-test
  (testing "single parameter pattern"
    (is (= 1 (factorial 0)))
    (is (= 1 (factorial 1)))
    (is (= 2 (factorial 2)))
    (is (= 120 (factorial 5)))))

