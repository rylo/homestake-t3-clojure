window.Board = class Board
  
  class Board extends Backbone.Model
    
    defaults:
      urlRoot: '/game'
      spaces: [null, null, null, null, null, null, null, null, null]
    
    fetch: ->
      @parse $.ajax 'http://localhost:5000/json/',
          type: 'GET'
          dataType: 'html'
          error: (jqXHR, textStatus, errorThrown) ->
            "error"
          success: (data, textStatus, jqXHR) ->            
            data.board
    
    parse: (response) ->
      response.board
    
    url: ->
      '/json/'


window.BoardView = class BoardView
  
  class BoardView extends Backbone.View
    el: '#board'
    
    renderSpaces: ->
      spaces = for space of this.model.get('spaces')
        "<div id=\"#{space}\"></div>"
    
    render: ->
      html = @renderSpaces()
      $(this.el).html(html)
