describe 'Game', ->
  game = null
  mockResponse = 
    {
      newBoard: {
        success: {
          status: 200,
          responseText: '{"currentPlayer" : "x", "currentMove" : "first-move", "player1": {"type":"human", "marker":"x"}, "player2": {"type":"human", "marker":"o"}, "board":["nil", "nil", "nil", "nil", "nil", "nil", "nil", "nil", "nil"], "message": ""}'
        }       
      }
    }
  
  beforeEach ->
    game = new Game
    spy = spyOn($, "ajax").andCallFake ->
      game.parse(mockResponse.newBoard.success)

  it 'constructs a game object', ->
    expect(game).toBeDefined()
    
  it 'has a board, players and a current player', ->
    expect(game.get('board')).toToBeDefined
    expect(game.get('players')).toEqual { player1: 'x', player2: 'o' }
    expect(game.get('currentPlayer')).toEqual { player1: 'x' }
        
  it 'has a default url', ->
    expect(game.url()).toEqual '/json/'
    
  it 'parses the hash returned from the server', ->
    response = JSON.parse mockResponse.newBoard.success.responseText    
    game.sync()
    expect( game.sync ).toHaveBeenCalled
    expect( game.get('board').get('spaces') ).toEqual [null, null, null, null, null, null, null, null, null]
    expect( game.get('currentPlayer') ).toEqual 'x'
    expect( game.get('players') ).toEqual {player1: response.player1, player2: response.player2}
    
    
describe 'GameView', ->
  gameView = null

  beforeEach ->
    game = new Game
    gameView = new GameView(model: game)

  it 'constructs a Board object', ->
    expect(gameView).not.toBe(null)

  it 'renders all of a board\'s spaces', ->
    expect(gameView.renderBoard()).toContain '<div id=\"0\" class=\"space\">null</div>'
    expect(gameView.renderBoard().length).toEqual 9
    
  it 'makes a move on and re-renders the board', ->
    gameView.makeMove({ target : 1 })
    expect( Game.render ).toHaveBeenCalled