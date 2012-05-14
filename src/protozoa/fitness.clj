(ns protozoa.fitness)

;; The point of this namespace is to estimate how interesting any given path
;; through De Jong parameter space is going to be.
;;
;; The estimations are done with far fewer particles, so they're much cheaper,
;; and we can race ahead of the graphics subsystem (which seems to be the
;; bottleneck currently) to find them.
