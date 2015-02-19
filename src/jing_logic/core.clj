;;; A naive implementation of Tic Tac Toe, whose core part written in miniKanren

(ns jing-logic.core
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]
        [clojure.tools.macro]))

(defsymbolmacro _ (lvar))

(defn winner [p board]
  (!= p nil)
  (conde
   [(== [p p p
         _ _ _
         _ _ _] board)]
   [(== [_ _ _
         p p p
         _ _ _] board)]
   [(== [_ _ _
         _ _ _
         p p p] board)]
   [(== [p _ _
         p _ _
         p _ _] board)]
   [(== [_ p _
         _ p _
         _ p _] board)]
   [(== [_ _ p
         _ _ p
         _ _ p] board)]
   [(== [p _ _
         _ p _
         _ _ p] board)]
   [(== [_ _ p
         _ p _
         p _ _] board)])) ; All eight conditions that players might win.

(defn plus [a b c]
  (conde
   [(nilo a) (== b c)]
   [(nilo b) (== a c)]))

(defn surpose [a b c]
  (conde
   [(== a []) (== b []) (== c [])]
   [(!= a []) (!= b []) (!= c [])
    (fresh [cara carb carc
            cdra cdrb cdrc]
      (conso cara cdra a)
      (conso carb cdrb b)
      (conso carc cdrc c)
      (plus cara carb carc)
      (surpose cdra cdrb cdrc))]))

;;; Boring stuff for printing and reading from console.

(defn println-winner-or-exit [board]
  (let [winners (run* [x]
                  (winner x board))] ; Run the above logic, to find out whether there is a winner.
    (if (= 0 (count winners))
      (println "No winner yet. ")
      (do
        (println winners "has won the game. ")
        (System/exit 0)))))

(defn println-board [board]
  (newline)
  (println (format "%5s%5s%5s" (get board 0) (get board 1) (get board 2)))
  (println (format "%5s%5s%5s" (get board 3) (get board 4) (get board 5)))
  (println (format "%5s%5s%5s" (get board 6) (get board 7) (get board 8)))
  (newline))

(defn select [board player]
  (print "Player" player "(0-8) > ")
  (flush)
  (let [x (read)]
    (if (and (number? x) (nil? (get board x)) (>= x 0) (<= x 8))
      (assoc board x player)
      (do
        (println "Not available. Try again!")
        (select board player)))))

(defn play [board players]
  (cond
    (= 0 (count players)) board
    :else (let [nb ((first players) board)]
            (println-winner-or-exit nb)
            (play nb (rest players)))))

(defn game [board players]
  (println-board board)
  (let [nb (play board players)]
    (game nb players)))

(defn -main
  [& args]
  (println "A naive implementation of Tic Tac Toe game, by Brendan. ")
  (game [nil nil nil
         nil nil nil
         nil nil nil]
        [(fn [board] (select board 0))
         (fn [board] (select board 1))]))
