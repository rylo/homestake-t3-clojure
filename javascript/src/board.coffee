class window.Board extends Backbone.Model
  defaults:
    spaces: [null, null, null, null, null, null, null, null, null]
    locked: false

  setSpace: (spaceIndex, playerMarker) ->
    spaces = this.get('spaces')
    spaces[spaceIndex] = playerMarker
    this.set(spaces: spaces)
    
  spaceOpen: (spaceIndex) ->
    this.get('spaces')[spaceIndex] == null
    
  clear: ->
    this.set('spaces', [null, null, null, null, null, null, null, null, null])
    
  lock: ->
    $('#loading').fadeIn()
    this.set('locked', true)
    
  unlock: ->
    $('#loading').fadeOut()
    this.set('locked', false)