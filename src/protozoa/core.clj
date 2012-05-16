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
              :pspace-x (atom 0)
              :pspace-y (atom 0))
  (pspace/setup)
  (zoon/setup)
  (background 0.33))

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

  (stroke 1)
  (stroke-weight 2)
  (fill 0.611 0.71 0.61)
  (ellipse @(state :pspace-x) @(state :pspace-y) 30 30))

(defsketch protozoa
  :title "Protozoa"
  :setup setup
  :draw draw
  :size [1680 1038]
  ;:renderer :p2d
  ;:key-pressed #(do (background 1)
  ;                  (zoon/setup))
  )
