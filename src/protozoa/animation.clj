(ns protozoa.animation
  (:refer-clojure :exclude [eval])
  (:require [protozoa.bezier :as bez])
  (:use [quil core]))

(defn animation
  [curve start stop]
  {:curve curve, :start start, :stop stop})

(defn t
  [{:keys [start stop]}]
  (/ (- (frame-count) start) (- stop start)))

(defn eval
  [anim]
  (bez/eval (:curve anim) (t anim)))
