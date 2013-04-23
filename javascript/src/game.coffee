class window.Game extends Backbone.Model
    
  url: -> '/json'

  initialize: ->
    this.set('board', new Board)
    this.set('players', { player1: {type: 'human', marker: 'x'}, player2: {type: 'human', marker: 'o'} })
    this.set('message', 'x\'s turn')
    this.set('currentPlayer', 'x')
    this.set('finished', true)

    this.listenTo this.get('board'), 'change', => this.trigger('change')

  sync: (callback) ->
    url = this.url()
    dataHash = this.dataHash()
    this.trigger('waiting')
    $.ajax url,
      data: dataHash
      error: (jqXHR, textStatus, errorThrown) => "error"
      success: (data, textStatus, jqXHR) => 
        this.parse(data)
        this.trigger('notwaiting')

  dataHash: ->
    $.extend {
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
    this.trigger('change')
    if this.gameIsOver()
      this.endGame()

  parseBoard: (board_array) ->
    board = for space_number, value of board_array
      if value == 'nil' then null else value
  
  endGame: ->
    this.set('finished', true)
    this.get('board').lock()
      
  gameIsOver: ->
    this.get('message').match(/wins|tie/)
    
  newGame: ->
    this.set('board', new Board)
    this.set('finished', false)
    this.set('message', "x's turn")
    this.trigger('change')
    this.sync() if this.computerGoesFirst()
      
  computerGoesFirst: ->
    this.get('players').player1.type.match(/easy|ultimate/)
