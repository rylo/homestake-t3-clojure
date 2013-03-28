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
    #invisible-form {
      display: none;
    }
  </style>")
  
(def javascript
  "<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js\"></script>
  <script>
    $('#board').children('div').click(function(){
      move = $(this).attr('id');
      $('#invisible-form input[name=\"move\"]').val(move);
      $('#invisible-form input[name=\"marker\"]').val('x');
      $('#invisible-form').submit();
    });
  </script>")

(defn render-form-space [index space-value]
  (if (nil? space-value)
    (format "<input type='hidden' name='%s' value='nil'>" index)
    (format "<input type='hidden' name='%s' value='%s'>" index space-value)))

(defn render-board-form [board]
  (apply str (map-indexed #(render-form-space %1 %2) board)))

(defn invisible-form [board]
  (apply str "<form id='invisible-form' action='/' method='get'>
    Player 1 Marker: <input type='text' name='player1' value='x'><br />
    Player 1 Type: <input type='text' name='player1type' value='human'><br />
    Player 2 Marker: <input type='text' name='player2' value='o'><br />
    Player 2 Type: <input type='text' name='player2type' value='human'><br />
  
    <input type='hidden' name='marker' value='x'>
    <input type='hidden' name='move' value='nil'>"
    (render-board-form board)
    "<input type='submit' value='Submit'>
  </form>"))

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

(defn render-space [index space-value]
  (if (nil? space-value)
    (format "<div id='%s'></div>" index)
    (format "<div id='%s'>%s</div>" index space-value)))

(defn render-board [board]
  (format "<div id='board'>%s</div>"
    (apply str (map-indexed #(render-space %1 %2) board))))

(defn get-altered-board [board requested-move]
  (board/set-marker board (get-marker requested-move) (Integer/valueOf (get-move requested-move))))
  
(defn render-finished-game [board]
  (str (game/get-ending-message board) (render-board board)))

(defn render-current-game [board]
  (str (render-board board) (invisible-form board) css javascript))

(defn render-new-game [board]
  (str (render-board board) (render-config-page) (invisible-form board) css javascript))

(defn new-game? [requested-move]
  (or (nil? (get-move requested-move)) (= "nil" (get-move requested-move))))

(defn process-request [requested-move game]
  (let [board (first game)
        player-list (last game)]
    (if (new-game? requested-move)
      (render-new-game board)
      (let [altered-board (get-altered-board board requested-move)]
    		(if (or (rules/winning-row-present? altered-board) (rules/game-over-with-tie? altered-board))
    			(render-finished-game altered-board)
          (render-current-game altered-board))))))
