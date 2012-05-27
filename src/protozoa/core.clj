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
              :a (atom 0), :b (atom 0), :c (atom 0), :d (atom 0)
              :fps (atom false), :paused (atom false))
  (pspace/setup)
  (zoon/setup))

(defn draw []
  ; Draw a mostly transparent white rect instead of clearing the background, in
  ; order to present the illusion of there being more particles than is actually
  ; the case.
  (when-not @(state :paused)
    (no-stroke)
    (fill 1 0.15)
    (rect 0 0 (width) (height)))

  (when-not @(state :paused)
    (pspace/tick))

  (zoon/tick)
  (draw-and-preserve-matrix zoon/draw)

  (when @(state :fps)
    (let [fr-ratio (/ (current-frame-rate) 30)
          bottom (- (height) 20)
          curr-height (* (- (height) 40) fr-ratio -1)]
      (no-stroke)
      (fill 1)
      (rect 0 0 60 (height))
      (fill 0.077 0.82 0.87 0.5)
      (rect 20 bottom 20 curr-height))))

(defn key-pressed []
  (case (raw-key)
    \f (swap! (state :fps) not)
    \p (do
         (when-not @(state :paused)
           (background 1))
         (swap! (state :paused) not))
    \space (do (background 1) (pspace/setup))
    :default)
  )

(defsketch protozoa
  :title "Protozoa"
  :setup setup
  :draw draw
  :size [1680 1038]
  :renderer :p2d
  :key-pressed key-pressed)
