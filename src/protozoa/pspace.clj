(ns protozoa.pspace
  "No, not the complexity class. This handles traversal through the parameter
  space of the de jong mapping."
  (:refer-clojure :exclude [rand])
  (:require [protozoa.animation :as anim]
            [protozoa.bezier :as bez]
            [protozoa.geometry :as geom :exclude [rand-point]])
  (:use [protozoa.util :only [invert rand]]
        [quil core]))

(def hi-limit 5)
(def lo-limit (invert hi-limit))
(def step-2d 300)
(def anim-duration 120)

(defn rand-point []
  (let [hi-point (zipmap [:a :b :c :d] (repeat hi-limit))
        lo-point (zipmap [:a :b :c :d] (repeat lo-limit))]
    (protozoa.geometry/rand-point lo-point hi-point)))

(defn rand-2d-point []
  {:x (rand 0 (width))
   :y (rand 0 (height))})

(defn rand-path []
  (apply bez/cubic (repeatedly 4 rand-2d-point)))

(defn rand-2d-step
  [{:keys [x y]}]
  (let [lo-point {:x (max (- x step-2d) 0), :y (max (- y step-2d) 0)}
        hi-point {:x (min (+ x step-2d) (width))
                  :y (min (+ y step-2d) (height))}]
    (protozoa.geometry/rand-point lo-point hi-point)))

(defn find-next-path*
  [anims]
  (let [[_ _ handle target] (:points (:curve (first anims)))
        handle' (geom/sum target (geom/sub target handle))
        curve (apply bez/cubic target handle'
                               (repeatedly 2 #(rand-2d-step target)))
        start (frame-count)
        stop (+ start anim-duration)]
    (conj anims (anim/animation curve start stop))))

(defn find-next-path []
  (swap! (state :pspace-paths) find-next-path*))

(defn setup []
  (swap! (state :pspace-paths) conj (anim/animation (rand-path) 0 anim-duration))
  (find-next-path))

(defn tick []
  (let [param-anim (first @(state :pspace-paths))
        {:keys [x y]} (anim/eval param-anim)]
    (if (> (frame-count) (:stop param-anim))
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
