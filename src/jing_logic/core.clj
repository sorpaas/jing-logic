(ns jing-logic.core
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]))

(defn winner [pl board]
  (fresh [lt mt rt
          lm mm rm
          lb mb rb]
    (== [lt mt rt
         lm mm rm
         lb mb rb] board)
    (conde
     [(== [pl pl pl
           lm mm rm
           lb mb rb] board)]
     [(== [lt mt rt
           pl pl pl
           lb mb rb] board)]
     [(== [lt mt rt
           lm mm rm
           pl pl pl] board)]
     [(== [pl mt rt
           pl mm rm
           pl mb rb] board)]
     [(== [lt pl rt
           lm pl rm
           lb pl rb] board)]
     [(== [lt mt pl
           lm mm pl
           lb mb pl] board)]
     [(== [pl mt rt
           lm pl rm
           lb mb pl] board)]
     [(== [lt mt pl
           lm pl rm
           pl mb rb] board)])))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
