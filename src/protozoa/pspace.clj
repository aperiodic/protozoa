(ns protozoa.pspace
  "No, not the complexity class. This handles traversal through the parameter
  space to determine the values of the coefficients of the de jong mapping."
  (:refer-clojure :exclude [rand])
  (:require [protozoa.bezier :as bez]
            [protozoa.geometry :as geom :exclude [rand-point]])
  (:use [protozoa.util :only [invert rand]]
        [quil core]))

(def hi-limit 5)
(def lo-limit (invert hi-limit))

(defn rand-point []
  (let [hi-point (zipmap [:a :b :c :d] (repeat hi-limit))
        lo-point (zipmap [:a :b :c :d] (repeat lo-limit))]
    (protozoa.geometry/rand-point lo-point hi-point)))

(defn rand-2d-point []
  {:x (rand 0 (width))
   :y (rand 0 (height))})

(defn rand-path []
  (apply bez/cubic (repeatedly 4 rand-2d-point)))

(defn find-next-path []
  (swap! (state :pspace-start) (fn [_] (frame-count)))
  (swap! (state :pspace-stop) (fn [_] (+ (frame-count) 100)))
  (swap! (state :pspace-path) (fn [_] (rand-path))))

(defn setup []
  (find-next-path))

(defn tick []
  (let [start-frame @(state :pspace-start)
        stop-frame @(state :pspace-stop)
        t (/ (- (frame-count) start-frame) (- stop-frame start-frame))
        curve @(state :pspace-path)
        {:keys [x y]} (curve t)]
    (if (> t 1.0)
      (do (find-next-path)
          (recur))
      ;else
      (do (swap! (state :pspace-x) (constantly x))
          (swap! (state :pspace-y) (constantly y))
          ;(swap! (state :a) a)
          ;(swap! (state :b) b)
          ;(swap! (state :c) c)
          ;(swap! (state :d) d)
          ))))
