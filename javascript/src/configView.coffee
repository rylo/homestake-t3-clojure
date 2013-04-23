class window.ConfigView extends Backbone.View
  
  el: '#config'
  events: 
    'change input:radio[name=type]' : 'updateConfiguration'
    'click .new-game' : 'newGame'
  
  initialize: ->
    this.render()
  
  updateConfiguration: (event) =>
    player = event.target.parentElement.parentElement.className
    type = event.target.value
    if player == 'player1'
      @model.get('players').player1.type = type
    else
      @model.get('players').player2.type = type

  render: ->
    if @model.get('finished')
      data = @model.get('players')
      html = "New Game
      <div class='player1'>
        Player 1 <br />
        #{this.renderOptions(data.player1.type, data.player1.marker)}
      </div>
  
      <div class='player2'>
        Player 2 <br />
        #{this.renderOptions(data.player2.type, data.player2.marker)}
      </div>
      <div class='new-game'>Start</div>"
      $(this.el).html html
      
  renderOptions: (playerType, marker) ->
    "<span class='player2marker'>#{marker}</span><br />
    <form class='type'>
      <input type='radio' name='type' value='human'#{this.isChecked(playerType, 'human')}>Human</input>
      <input type='radio' name='type' value='easy'#{this.isChecked(playerType, 'easy')}>Easy AI</input>
      <input type='radio' name='type' value='ultimate'#{this.isChecked(playerType, 'ultimate')}>Ultimate AI</input>
    </form>"
    
  isChecked: (playerType, checkboxType) ->
    if playerType == checkboxType
      " checked='checked'"
      
  newGame: ->
    @model.newGame()
    @model.get('board').unlock()
    $(this.el).html ""
    
  initialize: ->
    @model.on('change', this.render, this)