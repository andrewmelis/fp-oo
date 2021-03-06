(ns fp-oo.ch9-higher-order-functions)

;; scratch space for in-chapter stuff

;; closing over values
(def manual-inc5
  (fn [x] (+ 5 x)))

(def make-incrementer
  (fn [increment]
    (fn [x] (+ increment x))))

(def inc5 (make-incrementer 5))

(def inc5-from-partial (partial + 5))

(def increment-all (partial map inc))

(def incish
  (partial map + [100 200 300]))

;; lifting functions
(def value-odd?
  (fn [x] (not (even? x)))) ;; not operates on eval of (even? x)

(def complement-odd?
  (complement even?)) ;; complement operates on the function even?

(def my-complement
  (fn [function]
    (fn [x] (not (function x))))) ;; this is the "same" as my-odd?

(def my-complement-odd?
  (my-complement even?))

(def one-off-negate
  (fn [function]
    (fn [& args] (- (apply function args)))))

(def honest-return
  (fn [& args] 3.0))

(def one-off-madoffize
  (fn [function]
    (fn [& args] ( * 1.05 (apply function args)))))

(def lift
  (fn [modifier]
    (fn [base-function]
      (fn [& args] (modifier (apply base-function args))))))

(def lifted-negate (lift -))

(def lifted-madoffize (lift (partial * 1.05)))

;; point-free definitions

(def named-inc5
  (fn [x] (+ 5 x)))

(def point-free-inc5 (partial + 5))

;; exercises
(def separate (juxt filter remove))

(def old-separate
  (fn [predicate sequence]
    [(filter predicate sequence) (remove predicate sequence)]))

(def closure
  ( (fn [x]
      (fn [] x)) 3)   ;; this is all the body
  )    ;; this was tricky; was worred about accidentally accepting args to top-level fn

(def atom-equal-to-33
  (fn [& throwaways] 33))

(def always       ;; this is author's solution -- how is this point-free?
  (fn [x]
    (fn [& throwaways] x)))

(def point-free-always
  (fn [x]
    (comp first (partial list x))))

;; reversed-digits  provided by author, uses Java interoperability
(def reversed-digits
  (fn [string]
    (map (fn [digit-char]
           (-> digit-char str Integer.))
         (reverse string))))

(def check-sum
  (fn [seq checker-sequence]
    (apply + (map *
                  checker-sequence
                  seq))))

(def number-checker
  (fn [checker-sequence divisor]
    (fn [candidate]
      (-> candidate
          reversed-digits
          (check-sum checker-sequence)
          (rem divisor)
          zero?))))

(def isbn-checksum-sequence (map inc (range)))   ;; range is lazy, but can't start at 0

(def isbn?
  (number-checker isbn-checksum-sequence 11))

(def upc-checksum-sequence (flatten (repeat [1 3])))   ;; repeat is lazy

(def upc?
  (number-checker upc-checksum-sequence 10))
