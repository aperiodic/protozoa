(ns protozoa.core
  (:require [protozoa.bezier :as bez]
            [protozoa.geometry :as geom]
            [protozoa.protozoon :as zoon]
            [protozoa.pspace :as pspace]
            :reload-all)
  (:use [quil core]))

(defn setup []
  (smooth)
  (color-mode :hsb 1.0)
  (frame-rate 30)
  (background 1)
  (set-state! :protozoa (atom ())
              :pspace-path (atom nil)
              :a (atom 0), :b (atom 0), :c (atom 0), :d (atom 0))
  (pspace/setup)
  (zoon/setup))

(defn draw []
  ; Draw a mostly transparent white rect instead of clearing the background, in
  ; order to present the illusion of there being more particles than is actually
  ; the case.
  ;(no-stroke)
  ;(fill 1 0.15)
  ;(rect 0 0 (width) (height))

  ;(translate (/ (width) 2) (/ (height) 2))

  ;(zoon/tick)
  ;(zoon/draw)

  (pspace/tick)
  (background 0.33)

  (no-stroke)
  (fill 0.85)
  (doseq [[coeff i] (map vector [:a :b :c :d] (range))]
    (let [v @(state coeff)
          start-x (- (/ (width) 2) 110)]
      (rect (+ start-x (* i 60)) (/ (height) 2)
            40 (* v (/ (height) 11))))))

(defsketch protozoa
  :title "Protozoa"
  :setup setup
  :draw draw
  :size [1680 1038]
  ;:renderer :p2d
  ;:key-pressed #(do (background 1)
  ;                  (zoon/setup))
  )
