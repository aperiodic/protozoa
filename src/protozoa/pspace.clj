(ns protozoa.pspace
  "No, not the complexity class. This handles traversal through the parameter
  space of the de jong mapping."
  (:refer-clojure :exclude [rand])
  (:require [protozoa.animation :as anim]
            [protozoa.bezier :as bez]
            [protozoa.geometry :as geom :exclude [rand-point]])
  (:use [protozoa.util :only [invert rand]]
        [quil core]))

(def coeffs [:a :b :c :d])
(def hi-limit 5)
(def lo-limit (invert hi-limit))
(def step 1.5)
(def anim-duration 720)

(defn rand-point []
  (let [hi-point (zipmap coeffs (repeat hi-limit))
        lo-point (zipmap coeffs (repeat lo-limit))]
    (protozoa.geometry/rand-point lo-point hi-point)))

(defn rand-path []
  (apply bez/cubic (repeatedly 4 rand-point)))

(defn rand-step
  [{:keys [a b c d]}]
  (let [lo-point (zipmap coeffs (for [coeff [a b c d]]
                                  (max (- coeff step) lo-limit)))
        hi-point (zipmap coeffs (for [coeff [a b c d]]
                                  (min (+ coeff step) hi-limit)))]
    (protozoa.geometry/rand-point lo-point hi-point)))

(defn find-next-path*
  [prior-anim]
  (let [[_ _ handle end] (:points (:curve prior-anim))
        handle' (geom/sum end (geom/sub end handle))
        curve (apply bez/cubic end handle' (repeatedly 2 #(rand-step end)))
        start (frame-count)
        stop (+ start anim-duration)]
    (anim/animation curve start stop)))

(defn find-next-path []
  (swap! (state :pspace-path) find-next-path*))

(defn setup []
  (swap! (state :pspace-path) (fn [_] (anim/animation (rand-path) 0 anim-duration)))
  (find-next-path))

(defn tick []
  (let [param-anim @(state :pspace-path)
        pos (anim/eval param-anim)]
    (if (> (frame-count) (:stop param-anim))
      (do (find-next-path)
          (recur))
      ;else
      (doseq [[coeff val] pos]
        (reset! (state coeff) val)))))
