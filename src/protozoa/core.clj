(ns protozoa.core
  (:require [protozoa.animation :as anim]
            [protozoa.bezier :as bez]
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
              :pspace-paths (atom ())
              :pspace-x (atom 0)
              :pspace-y (atom 0))
  (pspace/setup)
  (zoon/setup))

(defn draw-path
  [path & {:keys [t] :or {t 1.0}}]
  (let [[begin handle-0 handle-1 end] (-> path :curve :points)]
    (no-fill)
    (stroke 0.1 t)
    (stroke-weight 3)
    (line (:x begin) (:y begin) (:x handle-0) (:y handle-0))
    (line (:x handle-1) (:y handle-1) (:x end) (:y end))

    (fill 0.1 t)
    (no-stroke)
    (ellipse (:x handle-0) (:y handle-0) 10 10)
    (ellipse (:x handle-1) (:y handle-1) 11 11)

    (no-fill)
    (stroke 0.1 t)
    (stroke-weight 4)
    (bezier (:x begin) (:y begin), (:x handle-0) (:y handle-0)
            (:x handle-1) (:y handle-1), (:x end) (:y end))))

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

  (background 0.33)
  (pspace/tick)

  (let [paths @(state :pspace-paths)]
    (let [curr (first paths)]
      (draw-path curr :t (anim/t curr)))

    (dorun (map draw-path (drop 1 (take (dec pspace/path-length) paths))))

    (when (= (count paths) pspace/path-length)
      (let [curr (first paths)
            tail (anim/animation (:curve (last paths))
                                 (:start curr) (:stop curr))
            t (- 1.0 (anim/t tail))]
        (draw-path tail :t t))))

  (stroke 1)
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
