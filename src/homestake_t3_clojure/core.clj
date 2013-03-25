(ns homestake-t3-clojure.core
  (:import (org.homestake Homestake))
  (:gen-class)
  (:require [tictactoe.board :as board]
            [homestake-server :as server]))

(defn -main []
  (println "Hello, World!")
  (server.)
  (board/make-board 9 "O HI DERE"))
