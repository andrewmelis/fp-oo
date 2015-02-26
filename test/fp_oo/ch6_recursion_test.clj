(ns fp-oo.ch6-recursion-test
  (require [clojure.test :refer :all]
           [fp-oo.ch6-recursion :refer :all]))

(deftest factorial-test
  (testing "single parameter pattern"
    (is (= 1 (factorial 0)))
    (is (= 1 (factorial 1)))
    (is (= 2 (factorial 2)))
    (is (= 120 (factorial 5))))
  (testing "collecting variable pattern"
    (is (= 1 (alt-factorial 0)))
    (is (= 1 (alt-factorial 1)))
    (is (= 2 (alt-factorial 2)))
    (is (= 120 (alt-factorial 5)))))

(deftest add-sequence-test
  (testing "can sum a sequence of numbers using collecting variable pattern"
    (is (= 1 (add-sequence [1] 0)))
    (is (= 3 (add-sequence [1 2] 0)))
    (is (= 10 (add-sequence [1 2 3 4] 0))))
  (testing "cheating"
    (is (= 10 (add-sequence-not-recursive [1 2 3 4] 0)))))

(deftest multiply-sequence-test
  (testing "can find product of sequence of numbers using collecting variable pattern"
    (is (= 1 (multiply-sequence [1] 1)))
    (is (= 2 (multiply-sequence [1 2] 1)))
    (is (= 24 (multiply-sequence [1 2 3 4] 1)))))

(deftest recursive-function-test
  (testing "can pass in combiner function"
    (testing "addition"
      (is (= 1 (recursive-function + [1] 0)))
      (is (= 3 (recursive-function + [1 2] 0)))
      (is (= 10 (recursive-function + [1 2 3 4] 0))))
    (testing "multiplication"
      (is (= 1 (recursive-function * [1] 1)))
      (is (= 2 (recursive-function * [1 2] 1)))
      (is (= 24 (recursive-function * [1 2 3 4] 1)))))
  (testing "alternate wildcard parameters to create map"
    (testing "map zero to each key"
      (is (= {:a 0, :b 0, :c 0}
             (recursive-function zero-mapper ;; combiner
                                 [:a :b :c]
                                 {} ))))  ;; so-far
    (testing "map index to each key"
      (is (= {:a 1, :b 2, :c 3}
             (recursive-function index-mapper ;; combiner
                                 [:a :b :c]
                                 {} ))))))  ;; so-far
(deftest reduce-test
  (testing "works similar to custom implementation"
    (testing "addition"
      (is (= 10 (reduce + 0 [1 2 3 4]))))
    (testing "multiplication"
      (is (= 24 (reduce * 1 [1 2 3 4]))))
    (testing "map zero to each key"
      (is (= {:a 0, :b 0, :c 0}
             (reduce (fn [map val] (assoc map val 0))
                     {} 
                     [:a :b :c]))))
    (testing "map index to each key"
      (is (= {:a 1, :b 2, :c 3}
             (reduce (fn [map val] (assoc map val (inc (count map))))
                     {}
                     [:a :b :c]))))))
