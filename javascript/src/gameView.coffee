class window.GameView extends Backbone.View
  el: '#board'
  messageElement: '#message'
  
  initialize: ->
    @model.on('change', this.render, this)
  
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
    playerMarker = @model.get('currentPlayer')
    @model.get('board').setSpace(spaceIndex, playerMarker)
    @model.set('currentMove', spaceIndex)
    @model.sync()
    this.render()

  events: 'click .space' : 'makeMove'