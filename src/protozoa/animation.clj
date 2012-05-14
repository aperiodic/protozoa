(ns protozoa.animation
  (:refer-clojure :exclude [eval])
  (:require [protozoa.bezier :as bez])
  (:use [quil core]))

(defn animation
  [curve start stop]
  {:curve curve, :start start, :stop stop})

(defn eval
  [{:keys [start stop curve]}]
  (let [t (/ (- (frame-count) start) (- stop start))]
    (bez/eval curve t)))
