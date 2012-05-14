(ns protozoa.bezier
  (:use [protozoa.geometry :only [scale sum]]
        [protozoa.util :only [binomial]]))

(defmacro order-n
  [n]
  (let [sigma-terms (for [i (range (inc n))]
                      `(scale (* ~(binomial n i)
                                 (Math/pow (- 1 ~'t) (- ~n ~i))
                                 (Math/pow ~'t ~i))
                              (nth ~'points ~i)))]
    `(fn [& ~'points]
       (fn [~'t]
         (sum ~@sigma-terms)))))

(def linear (order-n 1))
(def quadratic (order-n 2))
(def cubic (order-n 3))
(def quartic (order-n 4))
