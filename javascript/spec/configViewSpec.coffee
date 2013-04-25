describe 'ConfigView', ->

  configView = null
  game = null

  beforeEach ->
    game = new Game
    configView = new ConfigView({ model : game })
  
  it 'instantiates a ConfigView', ->
    expect( configView ).not.toBe null
  
  it 'renders a player\'s configuration options', ->
    expect( configView.renderPlayerOptions('easy', 'x') ).toContain "<span class='marker'>x</span>"
    expect( configView.renderPlayerOptions('easy', 'x') ).toContain "value='easy' checked='checked'>"

  it 'updates the game\'s configuration', ->
    mockEvent = { target : { value : 'easy', parentElement: { parentElement: { parentElement: { id : 'player1' } } } } }
    expect( configView.model.get('players').player1.type ).toEqual "human"
    configView.updateConfiguration(mockEvent)
    expect( configView.model.get('players').player1.type ).toEqual "easy"
    expect( configView.model.get('players').player2.type ).not.toEqual "easy"
    
  it 'creates a new game and unlocks the board', ->
    spy = spyOn(game, 'newGame')    
    configView.newGame()
    
    expect( configView.model.get('board').get('locked') ).toEqual false
    expect( spy ).toHaveBeenCalled()