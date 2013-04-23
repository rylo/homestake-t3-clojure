class window.Board extends Backbone.Model
    
  initialize: ->
    this.set('spaces', [null, null, null, null, null, null, null, null, null])
    this.set('locked', true)

  setSpace: (spaceIndex, playerMarker) ->
    spaces = this.get('spaces')
    spaces[spaceIndex] = playerMarker
    this.set(spaces: spaces)
    
  spaceOpen: (spaceIndex) ->
    this.get('spaces')[spaceIndex] == null

  clear: ->
    this.set('spaces', [null, null, null, null, null, null, null, null, null])
    
  lock: ->
    this.set('locked', true)
    
  unlock: ->
    this.set('locked', false)
