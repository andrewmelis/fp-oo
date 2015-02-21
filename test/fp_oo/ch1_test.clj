(ns fp-oo.ch1-test
  (require [clojure.test :refer :all]
           [fp-oo.ch1-exercises :refer :all]))

(deftest second-test
  (testing "return second element of a list"
    (is (= 2 (my-second '(1 2 3 4))))
    (is (nil? (my-second '())))
    (is (nil? (my-second '(1))))))
    ;;(is (throw (my-second))))) how test error case?

(deftest third-a-test
  (testing "return third element of a list"
    (is (= 2 (third-a '(0 1 2))))
    (is (= nil (third-a '())))
    (is (= nil (third-a '(1))))))
    
(deftest third-b-test
  (testing "return third element of a list"
    (is (= 2 (third-b '(0 1 2))))))
    

