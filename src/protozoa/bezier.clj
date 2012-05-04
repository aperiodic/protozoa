(ns protozoa.bezier
  (:use [protozoa.geometry :only [scale sum]]))

(defn- binomial
  [n k]
  (int (apply * (for [i (range 1 (inc k))]
                  (/ (- n (- k i)) i)))))

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
