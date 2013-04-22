describe 'Board', ->
  board = null
  
  beforeEach ->
    board = new Board
    
  it 'constructs a Board object', ->
    expect(board).not.toBe(null)
    
  it 'has a spaces array, filled with nulls', ->
    expect(board.get('spaces')).toEqual [null, null, null, null, null, null, null, null, null]
    
  it 'sets a marker on a board with a given index', ->
    board.setSpace(1, 'x')
    expect(board.get('spaces')).toEqual [null, 'x', null, null, null, null, null, null, null]
    board.setSpace(1, null)
    expect(board.get('spaces')).toEqual [null, null, null, null, null, null, null, null, null]
