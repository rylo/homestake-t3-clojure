(ns homestake-t3-clojure.core
  (:import (org.homestake Homestake)
           (homestake_t3_clojure HomestakeWrapper))
  (:require [tictactoe.game :as game]
            [tictactoe.board :as board]))

(defn -main []
  (println "Homestake Tic-Tac-Toe server starting...")
  (let [homestake (Homestake.)]
    (doto 
      homestake
        (.registerRoute "/" (HomestakeWrapper.))
        (.startServer))))
