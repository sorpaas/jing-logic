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
     [(== pl 0)]
     [(== pl 1)])
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

(defn select [board player]
  (print player "(0-8) > ") (flush)
  (let [x (read)]
    (if (and (number? x) (nil? (get board x)) (>= x 0) (<= x 8))
      (assoc board x player)
      (do
        (println "Not available. Try again!")
        (select board player)))))

(defn println-winner [board]
  (let [winners (run* [x]
                  (winner x board))]
    (if (= 0 (count winners))
      (println "No winner yet. ")
      (do
        (println winners "has won the game. ")
        (System/exit 0)))))

(defn println-board [board]
  (println (format "%5s%5s%5s" (get board 0) (get board 1) (get board 2)))
  (println (format "%5s%5s%5s" (get board 3) (get board 4) (get board 5)))
  (println (format "%5s%5s%5s" (get board 6) (get board 7) (get board 8))))

(declare play1)
(declare play2)

(defn play0 [board]
  (newline)
  (println-board board)
  (newline)
  (let [nb (select board 0)]
    (println-winner nb)
    (play1 nb)))

(defn play1 [board]
  (newline)
  (println-board board)
  (newline)
  (let [nb (select board 1)]
    (println-winner nb)
    (play0 nb)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "A naive implementation of Tic Tac Toe game, by Brendan. ")
  (play1 [nil nil nil
          nil nil nil
          nil nil nil]))
