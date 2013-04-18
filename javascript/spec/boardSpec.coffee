describe 'Board', ->
  board = null
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
    spy = spyOn($, "ajax")
    spy.andCallFake ->
      mockResponse.newBoard.success
    board = new Board
		fakeData = "You can put your return data here";
    
  it 'constructs a Board object', ->
    expect(board).not.toBe(null)
    
  it 'has a spaces array, filled with nulls', ->
    expect(board.get('spaces')).toEqual [null, null, null, null, null, null, null, null, null]
    
  it 'has a default url', ->
    expect(board.url()).toEqual '/json/'
    
  it 'fetches remote data', ->
    expect(board.fetch()).toEqual mockResponse.newBoard.success.responseText.board
    
    
describe 'BoardView', ->
  boardView = null
  
  beforeEach ->
    board = new Board
    boardView = new BoardView({ model: board })
    
  it 'constructs a Board object', ->
    expect(boardView).not.toBe(null)
    
  it 'renders all of a board\'s spaces', ->
    expect(boardView.renderSpaces()).toContain '<div id=\"0\"></div>'
    expect(boardView.renderSpaces().length).toEqual 9