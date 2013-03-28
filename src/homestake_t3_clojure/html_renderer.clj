(ns homestake-t3-clojure.html-renderer
  (require 
    [tictactoe.game :as game]))

(def css
  "<style>
    div {width: 600px; border:1px solid #333; overflow:auto;}
    body div {margin: 0 auto;}
    div div {
      cursor: pointer;
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
      if ($(this).html() == '') {
        currentPlayer = $('#invisible-form input[name=\"marker\"]').val();
        move = $(this).attr('id');
        $(this).html(currentPlayer);
        $('#invisible-form input[name=\"move\"]').val(move);
        $('#invisible-form').submit();
      }
    });
  </script>")

(def new-game-link
  "<a href='/'>New Game</a>")
  
(def render-config-page
  "<div><form action='/' method='get'>

    <input type='hidden' name='player1' value='x'>
    Player X Type: 
    <select name='player1type'>
      <option>human</option>
      <option>easy</option>
      <option>ultimate</option>
    </select>
    <br />
  
    <input type='hidden' name='player2' value='o'>
    Player O Type: 
    <select name='player2type'>
      <option>human</option>
      <option>easy</option>
      <option>ultimate</option>
    </select>
    <br />
  
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
    (format "<div id='%s'>%s</div>" index space-value)))

(defn render-board [board]
  (format "<div id='board'>%s</div>"
    (apply str (map-indexed #(render-space %1 %2) board))))
    
(defn render-finished-game [board]
  (str (render-board board) (format "<h1>%s</h1>" (game/get-ending-message board)) new-game-link css))

(defn render-current-game [board player-list current-player]
  (str (render-board board) (invisible-form board player-list current-player) css javascript))

(defn render-new-game [board player-list]
  (str (render-board board) render-config-page css))
