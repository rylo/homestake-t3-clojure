(ns homestake-t3-clojure.json-renderer
  (require 
    [tictactoe.game :as game]))

(defn render-player-turn-message [current-player]
  (format "%s's turn" (:marker current-player)))

(defn render-space [index space-value]
  (let [space 
    (if (nil? space-value)
      "\"nil\""
      (format "\"%s\"" space-value))]
        (if (= index 8)
          (format "%s" space)
          (format "%s, " space))))

(defn render-board [board]
  (format "\"board\":[%s]"
    (apply str (map-indexed #(render-space %1 %2) board))))

(defn render-new-game [board player-list]
  (println "new board")
  (println player-list)
  (str "{
    \"currentPlayer\" : \"x\",
    \"currentMove\" : \"first-move\",
    \"player1\": {\"type\":\"" (class (first player-list)) "\", \"marker\":\"" (:marker (first player-list)) "\"},
    \"player2\": {\"type\":\"" (class (last player-list)) "\", \"marker\":\"" (:marker (last player-list)) "\"},
    "(render-board [nil nil nil nil nil nil nil nil nil])",
    \"message\": \"\"
  }"))
  
(defn render-finished-game [board]
  (println "finished board")
  (str "{"
    (render-board board)",
    \"message\": \""(game/get-ending-message board)"\"}"))

(defn render-current-game [board player-list current-player]
  (println "current board")
  (println "player list")
  (println player-list)
  (str "{
    \"currentPlayer\" : \"" (:marker current-player) "\",
    \"player1\": {\"type\":\"" (class (first player-list)) "\", \"marker\":\"" (:marker (first player-list)) "\"},
    \"player2\": {\"type\":\"" (class (last player-list)) "\", \"marker\":\"" (:marker (last player-list)) "\"},"
    (render-board board)",
    \"message\": \""(render-player-turn-message current-player)"\"
  }"))