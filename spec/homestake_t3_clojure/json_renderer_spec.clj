(ns homestake-t3-clojure.json-renderer-spec
  (:require [speclj.core :refer :all]
            [homestake-t3-clojure.json-renderer :refer :all]))

(def empty-board [nil nil nil nil nil nil nil nil nil])
(def player-list ["x", "o"])

(defn -contains? [string matcher]
  (not (nil? (re-find matcher string))))

(describe "json-renderer"
  (it "constructs a json board object"
    (should= "\"board\":[\"nil\", \"nil\", \"nil\", \"nil\", \"nil\", \"nil\", \"nil\", \"nil\", \"nil\"]" (render-board empty-board)))
  
  (it "returns a new json game object"
    (let [new-game (render-new-game empty-board player-list)]  
      (should (-contains? new-game #"board"))
      (should (-contains? new-game #"player1"))
      (should (-contains? new-game #"player2"))
      (should (-contains? new-game #"message"))
      (should (-contains? new-game #"currentPlayer"))))
    
  (it "returns a current json game object"
    (let [current-game (render-current-game empty-board [] [])]  
      (should (-contains? current-game #"board"))
      (should (-contains? current-game #"player1"))
      (should (-contains? current-game #"player2"))
      (should (-contains? current-game #"message"))
      (should (-contains? current-game #"currentPlayer"))))

  (it "returns a finished json game object"
    (let [finished-game (render-finished-game empty-board player-list)]
      (should (-contains? finished-game #"board"))
      (should (-contains? finished-game #"player1"))
      (should (-contains? finished-game #"player2"))
      (should (-contains? finished-game #"message"))
      (should (-contains? finished-game #"currentPlayer")))))

(run-specs)
