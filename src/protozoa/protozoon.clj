(ns protozoa.protozoon
  (:refer-clojure :exclude [rand])
  (:use [protozoa.util :only [rand]]
        [quil core]))

;;
;; Constants & Globals
;;

(def p-count 25000)
(def a (atom 0))
(def b (atom 0))
(def c (atom 0))
(def d (atom 0))

;;;
;;; Functions
;;;

(defn rand-coord []
  (rand -2 2))

(defn protozoon []
  {:x (rand-coord), :y (rand-coord), :ticks-left (rand 50 150)})

(defn rand-coefficient
  ([] (rand -5 5))
  ([_] (rand-coefficient)))

(defn randomize-coefficients []
  (doseq [coeff [a b c d]]
    (swap! coeff rand-coefficient)))

(defn setup []
  (swap! (state :protozoa) (fn [_] (list)))
  (dotimes [_ p-count]
    (swap! (state :protozoa) conj (protozoon)))
  (randomize-coefficients))

(defn- tick*
  [{:keys [ticks-left x y]}]
  (if (zero? ticks-left)
    (protozoon)
    (let [x' (- (sin (* @a y)) (cos (* @b x)))
          y' (- (cos (* @c x)) (sin (* @d y)))]
      {:x x', :y y', :ticks-left (dec ticks-left)})))

(defn tick []
  (swap! (state :protozoa) #(map tick* %)))

(defn draw []
  (fill 0 1/30)
  (stroke 0 1/30)
  (let [scale (/ (height) 4.1)]
    (doseq [p @(state :protozoa)]
      (point (round (* scale (:x p))) (round (* scale (:y p)))))))
