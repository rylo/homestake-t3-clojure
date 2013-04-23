describe 'ConfigView', ->
  configView = null
  game = null

  beforeEach ->
    game = new Game
    configView = new ConfigView({ model : game })
  
  it 'instantiates a ConfigView', ->
    expect( configView ).not.toBe(null)