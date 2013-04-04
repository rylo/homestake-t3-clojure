(ns homestake-t3-clojure.json-renderer-spec
  (:require [speclj.core :refer :all]
            [homestake-t3-clojure.json-renderer :refer :all]))

(def empty-board [nil nil nil nil nil nil nil nil nil])
(def player-list ["x", "o"])

(defn -contains? [string matcher]
  (not (nil? (re-find matcher string))))

(describe "json-renderer"
  (it "constructs a json board object"
    (should= "'board':['nil', 'nil', 'nil', 'nil', 'nil', 'nil', 'nil', 'nil', 'nil']" (render-board empty-board)))
  
  (it "returns a new json game object"
    (should (-contains? (render-new-game empty-board player-list) #"board"))
    (should (-contains? (render-new-game empty-board player-list) #"player1"))
    (should (-contains? (render-new-game empty-board player-list) #"player2"))
    (should (-contains? (render-new-game empty-board player-list) #"message"))
    (should (-contains? (render-new-game empty-board player-list) #"currentMove"))
    (should (-contains? (render-new-game empty-board player-list) #"currentPlayer")))
    
  (it "returns a current json game object"
    (should (-contains? (render-current-game empty-board [] []) #"board"))
    (should (-contains? (render-current-game empty-board [] []) #"player1"))
    (should (-contains? (render-current-game empty-board [] []) #"player2"))
    (should (-contains? (render-current-game empty-board [] []) #"message"))
    (should (-contains? (render-current-game empty-board [] []) #"currentMove"))
    (should (-contains? (render-current-game empty-board [] []) #"currentPlayer")))

  (it "returns a finished json game object"
    (should (-contains? (render-finished-game empty-board) #"board"))
    (should (-contains? (render-finished-game empty-board) #"player1"))
    (should (-contains? (render-finished-game empty-board) #"player2"))
    (should (-contains? (render-finished-game empty-board) #"message"))
    (should (-contains? (render-finished-game empty-board) #"currentMove"))
    (should (-contains? (render-finished-game empty-board) #"currentPlayer"))))

(run-specs)
