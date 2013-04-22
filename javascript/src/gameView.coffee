class window.GameView extends Backbone.View
  el: '#board'
  
  initialize: ->
    @model.get('board').on('change', this.render, this)
  
  renderBoard: =>
    spaces = @model.get('board').get('spaces')
    # console.log "spaces for rendering: #{spaces}"
    result = for space_number, value of spaces
      "<div id=\"#{space_number}\" class=\"space\">#{value}</div>"
  
  render: ->
    # console.log 'rendering '
    html = this.renderBoard()
    $(this.el).html html
    
  makeMove: (event) ->
    space_index = event.target.id
    player_marker = @model.get('currentPlayer')
    @model.get('board').setSpace(space_index, player_marker)
    @model.set('currentMove', space_index)
    @model.sync()

  events: 'click .space' : 'makeMove'