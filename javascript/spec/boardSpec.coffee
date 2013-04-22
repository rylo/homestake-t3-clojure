describe 'Board', ->
  board = null

  beforeEach ->
    board = new Board
    board.clear()

  it 'constructs a Board object', ->
    expect(board).not.toBe(null)
    
  it 'has a spaces array, filled with nulls', ->
    expect(board.get('spaces')).toEqual [null, null, null, null, null, null, null, null, null]

  it 'sets a marker on a board with a given index', ->
    board.setSpace(1, 'x')
    expect(board.get('spaces')).toEqual [null, 'x', null, null, null, null, null, null, null]    
    board.clear()
    expect(board.get('spaces')).toEqual [null, null, null, null, null, null, null, null, null]
    
  it 'checks to see if a space is open', ->
    expect( board.spaceOpen(1) ).toEqual true