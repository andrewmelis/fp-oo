(ns fp-oo.ch4-embedded-functions-test
  (require [clojure.test :refer :all]
           [fp-oo.ch4-embedded-functions :refer :all]))

(deftest embedded-function-test
  (testing "passing in embedded functions"
    (def sut (make Point 1 2))
    (is (= (:x (make Point -1 -1)) ;; must test state bc = fails with diff mem address
           (:x ((:shift (:__methods__ sut)) sut -2 -3))))))

(deftest send-to-test
  (testing "send-to serves as convenient way to call embedded methods"
    (def point (make Point 1 2))
    (is (= (:x ((:shift (:__methods__ point)) point -2 -3))
           (:x (send-to point :shift -2 -3))))
    (is (= (:y ((:shift (:__methods__ point)) point -2 -3))
           (:y (send-to point :shift -2 -3))))))

(deftest embedded-getters-test
  (testing "embedded getters function properly"
    (testing "x"
      (def point (make Point 1 2))
      (is (= (:x point)
             (send-to point :x))))
    (testing "y"
      (def point (make Point 1 2))
      (is (= (:y point)
             (send-to point :y))))
    (testing "coordinates"
      (def point (make Point 1 2))
      (is (= '(1 2) [(send-to point :x) (send-to point :y)] (send-to point :coordinates))))))

(deftest embedded-add-test
  (testing "embedded add returns new point"
    (def sut (make Point 1 2))
    (def other (make Point -2 -3))
    (is (= (send-to (make Point -1 -1) :coordinates)
           (send-to (send-to sut :add other) :coordinates)))))
