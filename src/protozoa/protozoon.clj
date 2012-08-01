(ns protozoa.protozoon
  (:refer-clojure :exclude [rand])
  (:use [protozoa.util :only [rand]]
        [quil core]))

;;
;; Constants & Globals
;;

(def p-count 2000)
(def coeffs [:a :b :c :d])

;;
;; Private Functions
;;

(defn- coeff-atoms []
  (map state coeffs))

(defn- coeff-vals []
  (map deref (coeff-atoms)))

;;;
;;; Functions
;;;

(defn rand-coord []
  (rand -2 2))

(defn protozoon []
  {:x (rand-coord), :y (rand-coord), :ticks-left (int (rand 50 150))})

(defn setup []
  (swap! (state :protozoa) (fn [_] (list)))
  (dotimes [_ p-count]
    (swap! (state :protozoa) conj (protozoon))))

(defn- tick*
  [{:keys [ticks-left x y]}]
  (if (zero? ticks-left)
    (protozoon)
    (let [[a b c d] (coeff-vals)
          x' (- (sin (* a y)) (cos (* b x)))
          y' (- (cos (* c x)) (sin (* d y)))]
      {:x x', :y y', :ticks-left (dec ticks-left)})))

(defn tick []
  (swap! (state :protozoa) #(map tick* %)))

(defn draw []
  (translate (/ (width) 2) (/ (height) 2))
  (fill 0)
  (if @(state :paused)
    (stroke 0 0.15)
    (stroke 0))
  (let [scale (/ (height) 4.1)]
    (doseq [p @(state :protozoa)]
      (point (round (* scale (:x p))) (round (* scale (:y p)))))))
