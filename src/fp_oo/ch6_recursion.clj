(ns fp-oo.ch6-recursion)

(def factorial
  (fn [n]
    (if (< n 2)
      1
      (* n 
         (factorial 
           (dec n))))))

(def factorial-n-greater-than-1
  (fn [something so-far]
    (if (< something 2)
      so-far
      (recur (dec something)
             (* something so-far)))))

(def alt-factorial
  (fn [n]
    (factorial-n-greater-than-1 n 1)))

(def add-sequence
  (fn [something so-far]
    (if (empty? something)
      so-far
      (recur (rest something)
             (+ (first something) so-far)))))

;; apparently this is cheating
(def add-sequence-not-recursive
  (fn [something so-far]
    (apply + so-far something)))

(def multiply-sequence
  (fn [something so-far]
    (if (empty? something)
      so-far
      (recur (rest something)
             (* (first something) so-far)))))

;; function named this way to match exercises
;; this is really an implementation of reduce
(def recursive-function
  (fn [combiner something so-far]
    (if (empty? something)
      so-far
      (recur combiner
             (rest something)
             (combiner (first something) so-far)))))
