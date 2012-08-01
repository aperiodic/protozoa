(ns protozoa.util
  (:refer-clojure :exclude [rand])
  (use [quil core]))

(defn binomial
  [n k]
  (int (apply * (for [i (range 1 (inc k))]
                  (/ (- n (- k i)) i)))))

(defn rand
  ([] (clojure.core/rand))
  ([n] (clojure.core/rand n))
  ([l h]
   (+ l (clojure.core/rand (- h l)))))

(defn invert [x] (* x -1))

(defn draw-and-preserve-matrix
  [f]
  (push-matrix)
  (f)
  (pop-matrix))
