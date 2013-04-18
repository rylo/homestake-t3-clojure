class window.Board extends Backbone.Model
    
  defaults:
    spaces: [null, null, null, null, null, null, null, null, null]
          
  setSpace: (space_index, player_marker) ->
    spaces = this.get('spaces')
    spaces[space_index] = player_marker
    this.set(spaces: spaces)
    
  save: ->
    console.log 'lol'