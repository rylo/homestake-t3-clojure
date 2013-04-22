class window.Board extends Backbone.Model
  defaults:
    spaces: [null, null, null, null, null, null, null, null, null]

  setSpace: (spaceIndex, playerMarker) ->
    spaces = this.get('spaces')
    spaces[spaceIndex] = playerMarker if this.spaceOpen(spaceIndex)
    this.set(spaces: spaces)
    
  spaceOpen: (spaceIndex) ->
    this.get('spaces')[spaceIndex] == null
    
  clear: ->
    this.set('spaces', [null, null, null, null, null, null, null, null, null])