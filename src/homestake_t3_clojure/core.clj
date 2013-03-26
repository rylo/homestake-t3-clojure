(ns homestake-t3-clojure.core
  (:import (org.homestake Homestake))
  (:gen-class)
  (:require [tictactoe.board :as board]))

(defn -main []
  (println "Homestake Tic-Tac-Toe server starting...")
  (let [homestake (Homestake.)]
    (doto 
      homestake
        (.startServer))
    (board/make-board 9 "O HI DERE")))
