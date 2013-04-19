describe 'GameView', ->
  game = null
  gameView = null
  mockResponse = 
    {
      newBoard: {
        success: {
          status: 200,
          responseText: '{"marker" : "x", "move" : "first-move", "player1": {"type":"human", "marker":"x"}, "player2": {"type":"human", "marker":"o"}, "board":["nil", "nil", "nil", "nil", "nil", "nil", "nil", "nil", "nil"], "message": ""}'
        }       
      }
    }

  beforeEach ->
    game = new Game
    gameView = new GameView(model: game)
    spy = spyOn($, "ajax").andCallFake ->
      game.parse(mockResponse.newBoard.success)

  it 'constructs a Board object', ->
    expect( gameView ).not.toBe(null)

  it 'renders all of a board\'s spaces', ->
    expect( gameView.renderBoard() ).toContain '<div id=\"0\" class=\"space\">null</div>'
    expect( gameView.renderBoard().length ).toEqual 9
    
  it 'makes a move on and re-renders the board', ->
    gameView.makeMove({ target : 1 })
    expect( Game.render ).toHaveBeenCalled