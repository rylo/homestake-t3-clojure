(ns homestake-t3-clojure.core
  (:import (org.homestake Homestake))
  (:require [tictactoe.game :as game]
            [tictactoe.board :as board]))

; (defn -main []
;   (println "Homestake Tic-Tac-Toe server starting...")
;   (with-redefs-fn {#'tictactoe.io/prompt nil}
;   (game/start 
;     [(board/make-board 9 nil) [(tictactoe.player.Human. "x") (tictactoe.player.UltimateComputer. "o")]]))
;   (let [homestake (Homestake.)]
;     (doto 
;       homestake
;         (.startServer))))
