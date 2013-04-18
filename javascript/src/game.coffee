class window.Game extends Backbone.Model
  url: -> '/json/'
  
  defaults:
    board: new Board
    players: { player1: 'x', player2: 'o' }
    currentPlayer: { player1: 'x' }
    
  sync: () ->
    url = this.url()
    $.ajax url, ->
        type: 'GET'
        dataType: 'html'
        error: (jqXHR, textStatus, errorThrown) ->
          "error"
        success: (data, textStatus, jqXHR) ->            
          this.parse(data)
          
  parse: (data) ->
    json = JSON.parse data.responseText
    this.get('board').set('spaces', this.parseBoard(json.board))
    this.set('currentPlayer', json.currentPlayer)
    this.set('players', { player1: json.player1, player2: json.player2 })
    
  parseBoard: (board_array) ->
    board = for space_number, value of board_array
      if value == 'nil' then null else value


class window.GameView extends Backbone.View
  el: '#board'
  
  renderBoard: =>
    spaces = for space_number, value of @model.get('board').get('spaces')
      "<div id=\"#{space_number}\" class=\"space\">#{value}</div>"
  
  render: ->
    html = this.renderBoard()
    $(this.el).html(html)
    
  makeMove: (event) ->
    space_index = event.target.id
    player_marker = @model.get('currentPlayer')
    @model.get('board').setSpace(space_index, player_marker)
    this.render()

  events: 'click .space' : 'makeMove'