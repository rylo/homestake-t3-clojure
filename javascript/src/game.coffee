class window.Game extends Backbone.Model
  url: -> '/json'
  
  defaults:
    board : new Board
    players : { player1: {type: 'human', marker: 'x'}, player2: {type: 'human', marker: 'o'} }
    currentPlayer : 'x'
    currentMove : 'first-move'
    message: "x's turn"

  sync: (callback) ->
    url = this.url()
    dataHash = this.dataHash()
    $.ajax url,
      data: dataHash
      error: (jqXHR, textStatus, errorThrown) =>
        "error"
      success: (data, textStatus, jqXHR) =>
        this.parse(data)
        
  dataHash: ->
    $.extend {
      move: this.get('currentMove'),
      marker: this.get('currentPlayer'),
      player1 : this.get('players').player1.marker, 
      player1type: this.get('players').player1.type, 
      player2 : this.get('players').player2.marker, 
      player2type: this.get('players').player2.type
    }, this.boardHash()
    
  boardHash: ->
    hash = for space_number, value of this.get('board').get('spaces')
      "\"#{space_number}\":\"#{value}\""
    $.parseJSON "{#{hash}}"
    
  parse: (data, callback) ->
    this.get('board').set('spaces', this.parseBoard(data.board))
    this.set('currentPlayer', data.currentPlayer)
    this.set('message', data.message)
    this.set('players', { player1: { marker: data.player1.marker, type: data.player1.type}, player2: {marker: data.player2.marker, type: data.player2.type} })
    
  parseBoard: (board_array) ->
    board = for space_number, value of board_array
      if value == 'nil' then null else value