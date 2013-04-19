class window.GameView extends Backbone.View
  el: '#board'
  
  renderBoard: =>
    spaces = @model.get('board').get('spaces')
    result = for space_number, value of spaces
      "<div id=\"#{space_number}\" class=\"space\">#{value}</div>"
  
  render: ->
    html = this.renderBoard()
    $(this.el).html html
    
  makeMove: (event) ->
    space_index = event.target.id
    player_marker = @model.get('currentPlayer')
    @model.get('board').setSpace(space_index, player_marker)
    @model.fetch()
    this.render()

  events: 'click .space' : 'makeMove'