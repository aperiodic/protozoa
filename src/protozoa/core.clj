(ns protozoa.core
  (:require [protozoa.bezier :as bez]
            [protozoa.geometry :as geom]
            [protozoa.protozoon :as zoon]
            [protozoa.pspace :as pspace]
            :reload-all)
  (:use [protozoa.util :only [draw-and-preserve-matrix]]
        [quil core]))

(set! *warn-on-reflection* true)

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
  (no-stroke)
  (fill 1 0.15)
  (rect 0 0 (width) (height))

  (pspace/tick)
  (zoon/tick)
  (draw-and-preserve-matrix zoon/draw)

  (let [fr-ratio (/ (current-frame-rate) 30)
        rw (* (- (width) 40) fr-ratio)]
    (no-stroke)
    (fill 0.077 0.82 0.87 0.5)
    (rect 20 20 rw 45)))

(defsketch protozoa
  :title "Protozoa"
  :setup setup
  :draw draw
  :size [1680 1038]
  :renderer :p2d
  :key-pressed #(do (background 1)
                    (pspace/setup)))
