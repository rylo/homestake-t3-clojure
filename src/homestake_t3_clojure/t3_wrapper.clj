(ns homestake-t3-clojure.t3-wrapper
  (:require
    [homestake-t3-clojure.json-renderer :as renderer]
    [tictactoe.game :as game]
    [tictactoe.board :as board]
    [tictactoe.player :as player]
    [tictactoe.game-rules :as rules]))
    
(defn -get-move [request]
  (first request))

(defn -get-marker [request] 
  (last request))
  
(defn get-player-by-marker [player-list marker]
  (println player-list)
  (let [first-player (first player-list)
        second-player (last player-list)]
    (if (= marker (:marker first-player))
      first-player
      (if (= marker (:marker second-player))
        second-player))))

(defn alter-board [board requested-move player]
  (let [marker (-get-marker requested-move)
        move (-get-move requested-move)]
    (if (player/is-human? player)
      (if (rules/valid-move? board move)
        (board/set-marker board (-get-marker requested-move) (Integer/valueOf move))
        board)
      (board/set-marker board (:marker player) (player/get-move player board)))))

(defn new-game? [game]
  (= 1 (count (distinct (flatten game)))))

(defn first-move? [requested-move]
  (= "first-move" (-get-move requested-move)))

(defn create-player [player marker]
  (case player
    "human" (tictactoe.player.Human. marker)
    "class+tictactoe.player.Human" (tictactoe.player.Human. marker)
    "easy" (tictactoe.player.EasyComputer. marker)
    "class+tictactoe.player.EasyComputer" (tictactoe.player.EasyComputer. marker)
    "ultimate" (tictactoe.player.UltimateComputer. marker)
    "class+tictactoe.player.UltimateComputer" (tictactoe.player.UltimateComputer. marker)
    player))
    
(defn generate-player-list [player-information]
  (let [players (partition 2 player-information)
        player1 (first players)
        player2 (last players)]
    (vector
      (create-player (last player1) (first player1))
      (create-player (last player2) (first player2)))))
      
(defn process-request [requested-move game]
  (let [board (first game)
        player-list (generate-player-list (last game))
        current-player (get-player-by-marker player-list (-get-marker requested-move))
        other-player (game/alternate-players player-list current-player)]
    (if (new-game? game)
      (renderer/render-new-game board player-list)
      (if (and (first-move? requested-move) (player/is-human? current-player)) 
      (renderer/render-current-game board player-list current-player)
        (let [altered-board (alter-board board requested-move current-player)]
      		(if (rules/game-over? altered-board) 
          (renderer/render-finished-game altered-board)
            (if (player/is-human? other-player) 
            (renderer/render-current-game altered-board player-list other-player)
              (process-request ["_" (:marker other-player)] [altered-board (last game)]))))))))
