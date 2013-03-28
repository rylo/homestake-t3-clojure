(ns homestake-t3-clojure.t3-wrapper-spec
  (:require [speclj.core :refer :all]
            [tictactoe.player :refer :all]
            [homestake-t3-clojure.t3-wrapper :refer :all]))

(def requested-move [5 "x"])
(def players [(tictactoe.player.EasyComputer. "o") (tictactoe.player.EasyComputer. "x")])
(def empty-board [nil nil nil nil nil nil nil nil nil])
(def almost-won-board [nil nil nil "x" "x" nil nil nil nil])

(describe "t3-wrapper"

  (it "returns a move from a request"
    (should= 5 (-get-move requested-move)))
    
  (it "returns a marker from a request"
    (should= "x" (-get-marker requested-move)))
    
  (it "processes a request and returns a string"
    (should (string? (process-request requested-move [empty-board players]))))
    
  (it "processes a request and returns the altered board"
    (should (doto 
      (java.lang.String. (process-request requested-move [almost-won-board players]))
      (.contains "Player x wins!"))))
  
  (it "returns a player with a given marker"
    (should= (last players) (get-player-by-marker players "x"))
    (should= (first players) (get-player-by-marker players "o")))
    
  (it "returns true if it's the first move"
    (should (first-move? ["first-move" "x"])))
  
  (it "returns the player type from t3"
    (should= tictactoe.player.Human (class (create-player "human" "x")))
    (should= tictactoe.player.EasyComputer (class (create-player "easy" "x")))
    (should= tictactoe.player.UltimateComputer (class (create-player "ultimate" "x"))))
    
  (it "generates a list of players"
    (should= [#tictactoe.player.Human{:marker "x"} #tictactoe.player.Human{:marker "o"}] (generate-player-list ["x" "human" "o" "human"]))))

(run-specs)
