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
