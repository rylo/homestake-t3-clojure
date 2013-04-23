class window.GameView extends Backbone.View
  el: '#board'
  messageElement: '#message'
  events: 'click .space' : 'makeMove'
  
  render: ->
    $(this.el).html this.renderBoard()
    $(this.messageElement).html this.renderMessage()
  
  renderBoard: =>
    spaces = @model.get('board').get('spaces')
    result = for space_number, value of spaces
      value = '' if value == null
      "<div id=\"#{space_number}\" class=\"space\">#{value}</div>"
    
  renderMessage: ->
    "<h2>#{@model.get('message')}</h2>"

  makeMove: (event) ->
    spaceIndex = event.target.id
    board = @model.get('board')
    
    if board.spaceOpen(spaceIndex) && !board.get('locked')
      board.lock()
      board.setSpace(spaceIndex, @model.get('currentPlayer'))
      @model.sync()
      this.render()

  initialize: ->
    @model.on('change', this.render, this)