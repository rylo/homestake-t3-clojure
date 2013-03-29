(ns homestake-t3-clojure.html-renderer
  (require 
    [tictactoe.game :as game]))

(def css
  "<style>
  @keyframes fadein {
      from {
          opacity:0;
      }
      to {
          opacity:1;
      }
  }
  @-moz-keyframes fadein { /* Firefox */
      from {
          opacity:0;
      }
      to {
          opacity:1;
      }
  }
  @-webkit-keyframes fadein { /* Safari and Chrome */
      from {
          opacity:0;
      }
      to {
          opacity:1;
      }
  }
  @-o-keyframes fadein { /* Opera */
      from {
          opacity:0;
      }
      to {
          opacity: 1;
      }
  }
  
  body {
    background: #283036;
    margin:0;
    font-size: 100%;
    font-family: 'Arial';
  }
  
  div {
    width: 600px; 
    overflow:auto;
  }
  
  #board {
    margin: 4em auto;
  }
  
  #board div {
    color: #283036;
    border: 9px solid #283036; 
    text-decoration: none;
  	text-shadow: none;
    cursor: pointer;
    width: 30%; 
    background: #fff; 
    height: 170px; 
    float: left;
    font-size: 8em; 
    line-height: 150px; 
    text-align: center;
  }
  
  #board .o {
    background: #d3c4c4;
  }
  
  #board .x {
    background: #c4cbd3;
  }
  
  #board div:hover {
  	-webkit-transition: 200ms linear 0s;
  	-moz-transition: 200ms linear 0s;
  	-o-transition: 200ms linear 0s;
  	transition: 200ms linear 0s;
    background-color: #496255;
  }
  
  #invisible-form {
    display: none;
  }
  
  .message {
    color: #fffbf8;
    animation-delay: 20s;
    -webkit-animation-delay: 2s;
    animation: fadein 2s;
    -moz-animation: fadein 2s; /* Firefox */
    -webkit-animation: fadein 2s; /* Safari and Chrome */
    -o-animation: fadein 2s; /* Opera */
    margin: 4em auto;
    background: #5f707f;
    border: 1px solid #333;
    border-radius: 5px;
    width: 30%;
    padding: 20px;
    margin-top: 3em;
  }
  
  input[type=\"submit\"],
  .button {
    cursor: pointer;
    background: #97a4b1;
    border-radius: 5px;
  	-webkit-transition: 200ms linear 0s;
  	-moz-transition: 200ms linear 0s;
  	-o-transition: 200ms linear 0s;
  	transition: 200ms linear 0s;
    color: #fff;
    font-size: 1.2em;
    -webkit-appearance: none;
    margin: 0 auto;
    border: none;
    text-decoration: none;
    text-align: center;
    display: block;
    width: 40%;
    padding: 0.5em;
    line-height: 1.75em;
  }
    
  input[type=\"submit\"],
  .button:hover {
    background: #8091a1;
  }
  
  .message h1 {
    font-size: 2em;
    text-align: center;
    margin: 0 0 1em 0;
  }
  
  .welcome {
    font-family: 'Molle', cursive;
    font-size: 10em;
  }
  
  .centered {
    font-size: 2em;
    width: 56%;
    margin: 2em auto;
  }
  
  </style>")
  
(def javascript
  "<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js\"></script>
  <script>
    $('#board').children('div').click(function(){
      if ($(this).html() == '') {
        currentPlayer = $('#invisible-form input[name=\"marker\"]').val();
        move = $(this).attr('id');
        $(this).html(currentPlayer);
        $('#invisible-form input[name=\"move\"]').val(move);
        $('#invisible-form').submit();
      }
    });
  </script>")

(def welcome-message 
  "<h1 class='welcome'>Welcome to<span>Tic-Tac-Toe</span></h1>")

(def new-game-link
  "<a href='/' class='button'>New Game</a>")
  
(def render-config-page
  "<div class='message'>
    <form action='/' method='get'>

    <input type='hidden' name='player1' value='x'>
    
    <div class='centered'>
      Player X is: 
      <select name='player1type'>
        <option>human</option>
        <option>easy</option>
        <option>ultimate</option>
      </select>
      <br />
  
      <input type='hidden' name='player2' value='o'>
      Player O is:
      <select name='player2type'>
        <option>human</option>
        <option>easy</option>
        <option>ultimate</option>
      </select>
    </div>
  
    <input type='hidden' name='marker' value='x'>
    <input type='hidden' name='move' value='first-move'>
  
    <input type='hidden' name='0' value='nil'>
    <input type='hidden' name='1' value='nil'>
    <input type='hidden' name='2' value='nil'>
    <input type='hidden' name='3' value='nil'>
    <input type='hidden' name='4' value='nil'>
    <input type='hidden' name='5' value='nil'>
    <input type='hidden' name='6' value='nil'>
    <input type='hidden' name='7' value='nil'>
    <input type='hidden' name='8' value='nil'>
  
    <input type='submit' value='Start new game!'>
  </form></div>")

(defn render-form-space [index space-value]
  (if (nil? space-value)
    (format "<input type='hidden' name='%s' value='nil'>" index)
    (format "<input type='hidden' name='%s' value='%s'>" index space-value)))

(defn render-board-form [board]
  (apply str (map-indexed #(render-form-space %1 %2) board)))

(defn invisible-form [board player-list next-player]
  (apply str "<form id='invisible-form' action='/' method='get'>
    <input type='hidden' name='player1' value='"(:marker (first player-list))"'>
    <input type='hidden' name='player1type' value='"(str (class (first player-list)))"'>
    <input type='hidden' name='player2' value='"(:marker (last player-list))"'>
    <input type='hidden' name='player2type' value='"(str (class (last player-list)))"'>
  
    <input type='hidden' name='marker' value='"(:marker next-player)"'>
    <input type='hidden' name='move' value='nil'>"
    (render-board-form board)
    "<input type='submit' value='Submit'>
  </form>"))

(defn render-space [index space-value]
  (if (nil? space-value)
    (format "<div id='%s'></div>" index)
    (format "<div id='%s' class='%s'>%s</div>" index space-value space-value)))

(defn render-board [board]
  (format "<div id='board'>%s</div>"
    (apply str (map-indexed #(render-space %1 %2) board))))

(defn render-message-box [message]
  (format "<div class='message'>%s</div>" message))

(defn render-end-message [message]
  (render-message-box (str (format "<h1>%s</h1>" message) new-game-link)))

(defn render-player-turn-message [player]
  (render-message-box (format "<h1>%s's turn" (:marker player))))
    
(defn render-finished-game [board]
  (str 
    css 
    (render-board board) 
    (render-end-message (game/get-ending-message board))))

(defn render-current-game [board player-list current-player]
  (str 
    css 
    (render-board board) 
    (render-player-turn-message current-player) 
    (invisible-form board player-list current-player) javascript))

(defn render-new-game [board player-list]
  (str 
    css 
    render-config-page))
