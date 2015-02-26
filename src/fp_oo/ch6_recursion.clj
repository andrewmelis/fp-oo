(ns fp-oo.ch6-recursion)

(def factorial
  (fn [n]
    (if (< n 2)
      1
      (* n 
         (factorial 
           (dec n))))))
