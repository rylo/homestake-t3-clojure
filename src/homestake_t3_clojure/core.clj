(ns homestake-t3-clojure.core
  (:import (org.homestake Homestake)
           (org.homestake.response FileResponse)
           (homestake_t3_clojure HomestakeWrapper))
  (:require [tictactoe.game :as game]
            [tictactoe.board :as board]))

(defn -main []
  (println "Homestake Tic-Tac-Toe server starting...")
  (let [homestake (Homestake.)]
    (doto 
      homestake
        (.registerRoute "/json/" (HomestakeWrapper.))
        (.registerRoute "/" (FileResponse. "/Users/rylan/Documents/Apprenticeship/homestake-t3-clojure/public/" "game.html"))
        (.registerRoute "/test/" (FileResponse. "/Users/rylan/Documents/Apprenticeship/homestake-t3-clojure/public/" "test.html"))
        (.startServer))))
