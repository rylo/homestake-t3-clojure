class window.ConfigView extends Backbone.View
  
  el: '#config'
  events: 
    'change input:radio[name=type]' : 'updateConfiguration'
    'click .new-game' : 'newGame'
  
  initialize: ->
    this.render()
  
  updateConfiguration: (event) =>
    player = event.target.parentElement.parentElement.parentElement.id
    type = event.target.value
    if player == 'player1'
      @model.get('players').player1.type = type
    else if player == 'player2'
      @model.get('players').player2.type = type

  render: ->
    if @model.get('finished')
      data = @model.get('players')
      html = "
      <div class='column' id='player1'>#{this.renderPlayerOptions(data.player1.type, data.player1.marker)}</div>
      <div class='column' id='player2'>#{this.renderPlayerOptions(data.player2.type, data.player2.marker)}</div>
      <div class='new-game button'>Start</div>"
      $(this.el).html html
      
  renderPlayerOptions: (playerType, marker) ->
    "<h3>Player <span class='marker'>#{marker}</span></h3>
      <form class='type'>
        <label><input class='regular-checkbox' type='radio' name='type' value='human'#{this.isChecked(playerType, 'human')}>Human</label><br />
        <label><input class='regular-checkbox' type='radio' name='type' value='easy'#{this.isChecked(playerType, 'easy')}>Easy AI</label><br />
        <label><input class='regular-checkbox' type='radio' name='type' value='ultimate'#{this.isChecked(playerType, 'ultimate')}>Ultimate AI</label><br />
      </form>"
    
  isChecked: (playerType, checkboxType) ->
     if playerType == checkboxType 
       " checked='checked'"
     else
       ""

  newGame: ->
    @model.newGame()
    @model.get('board').unlock()
    $(this.el).html ""
    
  initialize: ->
    @model.on('change', this.render, this)