(ns protozoa.geometry
  (:refer-clojure :exclude [rand])
  (:use [protozoa.util :only [rand]]))

(defn same-dimensions?
  [a b]
  (= (keys a) (keys b)))

(defn point
  ([x y]
   {:x x, :y y})
  ([x y z]
   {:x x, :y y, :z z})
  ([a b c d]
   {:a a, :b b, :c c, :d d}))

(defn rand-point
  [lower-point upper-point]
  {:pre [(same-dimensions? lower-point upper-point)]}
  (into {} (for [[[dim low] [_ high]] (map vector (seq lower-point)
                                                  (seq upper-point))]
             [dim (rand low high)])))

(defn scale
  [scalar point]
  (into {} (for [[dimension value] (seq point)]
             [dimension (* scalar value)])))

(defn sum
  ([a b]
   {:pre [(same-dimensions? a b)]}
   (into {} (for [[[dim a_i] [_ b_i]] (map vector (seq a) (seq b))]
              [dim (+ a_i b_i)])))
  ([a b & others]
   (loop [result (sum a b), to-sum others]
     (if-let [c (first to-sum)]
       (recur (sum result c) (rest to-sum))
       result))))

(defn sub
  [a b]
  (sum a (scale -1 b)))
