(ns homestake-t3-clojure.core
  (:import (org.homestake Homestake))
  (:require [homestake-t3-clojure.homestake-wrapper :refer :all]
            [tictactoe.game :as game]
            [tictactoe.board :as board]))

(defn -main []
  (println "Homestake Tic-Tac-Toe server starting...")
  (let [homestake (Homestake.)]
    (doto 
      homestake
        (.registerRoute "/" (homestake-t3-clojure.homestake-wrapper.))
        (.startServer))))
