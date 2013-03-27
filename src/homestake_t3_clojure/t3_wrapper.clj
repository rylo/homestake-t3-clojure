(ns homestake-t3-clojure.t3-wrapper
  (:require
    [tictactoe.game :as game]
    [tictactoe.board :as board]
    [tictactoe.game-rules :as rules]))
    
(defn get-move [request]
  (first request))

(defn get-marker [request] 
  (last request))
  
(def css
  "<style>
    div {width: 600px; border:1px solid #333; overflow:auto;}
    body div {margin: 0 auto;}
    div div {
      width: 33%; 
      background-color:#ccc; 
      height:200px; 
      float:left;
      font-family:'Arial'; 
      font-size:5em; 
      line-height:200px; 
      text-align:center;
    }
    div div:hover {
      background-color:#69799A;
      color: #fff;
    }
  </style>")

(defn render-config-page []
  "<div><form action='/' method='get'>
    Player 1 Marker: <input type='text' name='player1' value='x'><br />
    Player 1 Type: <input type='text' name='player1type' value='human'><br />
    Player 2 Marker: <input type='text' name='player2' value='o'><br />
    Player 2 Type: <input type='text' name='player2type' value='human'><br />
    
    <input type='hidden' name='marker' value='x'>
    <input type='hidden' name='move' value='nil'>
    
    <input type='hidden' name='0' value='nil'>
    <input type='hidden' name='1' value='nil'>
    <input type='hidden' name='2' value='nil'>
    <input type='hidden' name='3' value='nil'>
    <input type='hidden' name='4' value='nil'>
    <input type='hidden' name='5' value='nil'>
    <input type='hidden' name='6' value='nil'>
    <input type='hidden' name='7' value='nil'>
    <input type='hidden' name='8' value='nil'>
    
    <input type='submit' value='Submit'>
  </form></div>")

(defn render-space [space-value]
  (if (nil? space-value)
    "<div></div>"
    (format "<div>%s</div>" space-value)))

(defn render-board [board]
  (format "<div>%s</div>" (apply str (map #(render-space %) board))))

(defn process-request [requested-move game]
  (let [board (first game)
        player-list (last game)]
    (if (or (nil? (get-move requested-move)) (= "nil" (get-move requested-move)))
      (str (render-board board) (render-config-page) css)
      (let [altered-board (board/set-marker board (get-marker requested-move) (Integer/valueOf (get-move requested-move)))]
    		(if (or (rules/winning-row-present? altered-board) (rules/game-over-with-tie? altered-board))
    			(str (game/get-ending-message altered-board) (render-board altered-board))
          (str (render-board altered-board) css))))))
