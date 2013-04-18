describe 'Game', ->
  game = null
  
  beforeEach ->
    game = new Game

  it 'constructs a game object', ->
    expect(game).toBeDefined()
    
  it 'has a board, players and a current player', ->
    expect(game.get('board')).toToBeDefined
    expect(game.get('players')).toEqual { player1: 'x', player2: 'o' }
    expect(game.get('currentPlayer')).toEqual { player1: 'x' }