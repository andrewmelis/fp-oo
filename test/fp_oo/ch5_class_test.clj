(ns fp-oo.ch5-class-test
  (require [clojure.test :refer :all]
           [fp-oo.ch5-class :refer :all]))

(deftest make-test
  (testing "can make an 'instance' of an object as a hash with keys"
    (testing "verbose make"
      (let [point (verbose-make Point 1 2)]
        (is (= 'Point (:__class_symbol__ point)))
        (is (= 1 (:x point)))
        (is (= 2 (:y point)))))
    (testing "better make"
      (let [point (make Point 1 2)]
        (is (= 'Point (:__class_symbol__ point)))
        (is (= 1 (:x point)))
        (is (= 2 (:y point)))))))

(deftest send-to-test
  (testing "can call methods on instances that don't hold  direct reference to methods"

    (use 'fp-oo.ch5-class)    ;; need this for some namespacing/context issue

    (testing "verbose send-to"
      (let [point (make Point 1 2)]
        (is (= 'Point (send-to point :class)))
        (is (= (make Point -1 -1) (send-to point :shift -2 -3)))))))
