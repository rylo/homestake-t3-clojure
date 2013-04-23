class window.Game extends Backbone.Model

  defaults:
    board : new Board
    players : { player1: {type: 'human', marker: 'x'}, player2: {type: 'ultimate', marker: 'o'} }
    currentPlayer : 'x'
    message: "x's turn"
    
  url: -> '/json'

  initialize: ->
    this.listenTo this.get('board'), 'change', => this.trigger('change')

  sync: (callback) ->
    url = this.url()
    dataHash = this.dataHash()
    $.ajax url,
      data: dataHash
      error: (jqXHR, textStatus, errorThrown) => "error"
      success: (data, textStatus, jqXHR) => this.parse(data)

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

  parse: (data) ->
    board = this.get('board')
    board.set('spaces', this.parseBoard(data.board))
    board.unlock()
    this.set('currentPlayer', data.currentPlayer)
    this.set('message', data.message)    

  parseBoard: (board_array) ->
    board = for space_number, value of board_array
      if value == 'nil' then null else value