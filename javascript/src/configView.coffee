class window.ConfigView extends Backbone.View
  el: '#config'
  events: 'change input:radio[name=type]' : 'lerg'
    
  updateConfiguration: (event) =>
    player = event.target.parentElement.parentElement.className
    type = event.target.value
    console.log type
  
  render: ->
    data = @model.get('players')
    html = "
    <div class='player1'>
      Player 1 <br />
      <span class='player1marker'>#{data.player1.marker}</span><br />
      <form class='type'>
        <input type='radio' name='type' value='human' checked='checked'>Human</input>
        <input type='radio' name='type' value='easy'>Easy AI</input>
        <input type='radio' name='type' value='ultimate'>Ultimate AI</input>
      </form>
    </div>
    
    <div class='player2'>
      Player 2 <br />
      <span class='player2marker'>#{data.player2.marker}</span><br />
      <form class='type'>
        <input type='radio' name='type' value='human' checked='checked'>Human</input>
        <input type='radio' name='type' value='easy'>Easy AI</input>
        <input type='radio' name='type' value='ultimate'>Ultimate AI</input>
      </form>
    </div>"
    $(this.el).html html