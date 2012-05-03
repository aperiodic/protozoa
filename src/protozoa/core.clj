(ns protozoa.core
  (:require [protozoa.protozoon :as protozoon] :reload-all)
  (:use [quil core]))

(defn setup []
  (smooth)
  (color-mode :hsb 1.0)
  (frame-rate 30)
  (background 1)
  (set-state! :protozoa (atom ()))
  (protozoon/setup))

(defn draw []
  ; Draw a mostly transparent white rect instead of clearing the background, in
  ; order to present the illusion of there being more particles than is actually
  ; the case.
  ;(no-stroke)
  ;(fill 1 0.15)
  ;(rect 0 0 (width) (height))

  (translate (/ (width) 2) (/ (height) 2))

  (protozoon/tick)
  (protozoon/draw))

(defsketch protozoa
  :title "Protozoa"
  :setup setup
  :draw draw
  :size [1680 1038]
  :renderer :p2d
  :key-pressed #(do (background 1)
                    (protozoon/setup)))
