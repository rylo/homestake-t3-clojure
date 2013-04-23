describe 'Game', ->
  game = null
  mockResponse = 
    {
      newBoard: {
        success: {
          status: 200,
          responseText: { 
            marker : "x", 
            player1 : { type : "human", marker : "x"}, 
            player2 : { type : "human", marker : "o"}, 
            board :["nil", "nil", "nil", "nil", "nil", "nil", "nil", "nil", "nil"], 
            message : ""
          }
        }       
      }
    }
          
  beforeEach ->
    game = new Game
    spy = spyOn($, "ajax").andCallFake ->
      mockResponse.newBoard.success

  it 'constructs a game object', ->
    expect(game).toBeDefined()
    
  it 'has a board, players and a current player', ->
    response = mockResponse.newBoard.success.responseText
    playerList = { player1 : response.player1, player2 : response.player2}
    expect( game.get('board') ).toToBeDefined
    expect( game.get('players') ).toEqual playerList
    expect( game.get('currentPlayer') ).toEqual 'x'
        
  it 'has a default url', ->
    expect(game.url()).toEqual '/json'
    
  it 'parses the hash returned from the server', ->
    response = mockResponse.newBoard.success.responseText
    game.sync()
    expect( game.parse ).toHaveBeenCalled
    expect( game.get('board').get('spaces') ).toEqual [null, null, null, null, null, null, null, null, null]
    expect( game.get('currentPlayer') ).toEqual 'x'
    expect( game.get('players') ).toEqual {player1: response.player1, player2: response.player2}
  
  it 'returns a hash of the game\'s data', ->
    hash = { 
      0 : 'null', 1 : 'null', 2 : 'null', 3 : 'null', 4 : 'null', 5 : 'null', 6 : 'null', 7 : 'null', 8 : 'null', 
      marker : 'x', 
      player1 : 'x', 
      player1type : 'human', 
      player2 : 'o', 
      player2type : 'human' 
    }
    expect( game.dataHash() ).toEqual hash
  
  it 'returns a hash representation of the board', ->
    expect( game.boardHash() ).toEqual { 0 : 'null', 1 : 'null', 2 : 'null', 3 : 'null', 4 : 'null', 5 : 'null', 6 : 'null', 7 : 'null', 8 : 'null' }