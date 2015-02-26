(ns fp-oo.ch5-class-test
  (require [clojure.test :refer :all]
           [fp-oo.ch5-class :refer :all]))


(deftest make-test
  (testing "can make an 'instance' of an object as a hash with keys"
    (let [point (make Point 1 2)]
      (is (= 'Point (:__class_symbol__ point)))
      (is (= 1 (:x point)))
      (is (= 2 (:y point))))))

(deftest send-to-test
  (testing "can call methods on instances that don't hold  direct reference to methods"

    ;(use 'fp-oo.ch5-class)    ;; don't need this here...

    (let [point (make Point 1 2)]
      (is (= 'Point (send-to point :class-name)))
      (is (= (make Point -1 -1) (send-to point :shift -2 -3))))))

(deftest class-name-test
  (testing "class-name returns a class's symbol"

    (use 'fp-oo.ch5-class)    ;; need this for some namespacing/context issue

    (let [point (make Point 1 2)]
      (is (= 'Point (send-to point :class-name))))))

(deftest class-test
  (testing "class returns the class map"

    ;(use 'fp-oo.ch5-class)    ;; don't need this here...

    (let [point (make Point 1 2)]
      (is (= Point (send-to point :class))))))

(deftest method-missing-instance-variables-test
  (testing "apply-message-to searches for instance variables if no method found"
    (is (= "stuff" (send-to (make Holder "stuff") :held)))
    (is (nil? (send-to (make Holder "stuff") :not-held)))))

(deftest method-missing-returns-nil-test
  (testing "sending an unknown message returns nil instead of throwing error"
    (is (nil? (send-to (make Point 1 2) :some-unknown-message)))))



