(ns protozoa.bezier
  (:refer-clojure :exclude [eval])
  (:use [protozoa.geometry :only [scale sum]]
        [protozoa.util :only [binomial]]))

(defmacro of-order
  [n]
  (let [sigma-terms (for [i (range (inc n))]
                      `(scale (* ~(binomial n i)
                                 (Math/pow (- 1 ~'t) (- ~n ~i))
                                 (Math/pow ~'t ~i))
                              (nth ~'points ~i)))]
    `(fn [& ~'points]
       {~:fn (fn [~'t] (sum ~@sigma-terms))
        ~:points ~'points})))

(def linear (of-order 1))
(def quadratic (of-order 2))
(def cubic (of-order 3))
(def quartic (of-order 4))

(defn eval
  [curve t]
  ((:fn curve) t))
