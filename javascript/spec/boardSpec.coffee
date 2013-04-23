describe 'Board', ->
  board = null

  beforeEach ->
    board = new Board

  it 'constructs a Board object', ->
    expect( board ).not.toBe(null)
    
  it 'has a spaces array, filled with nulls', ->
    expect( board.get('spaces') ).toEqual [null, null, null, null, null, null, null, null, null]

  it 'sets a marker on a board with a given index', ->
    board.setSpace(1, 'x')
    expect( board.get('spaces') ).toEqual [null, 'x', null, null, null, null, null, null, null]    
  
  it 'clears the board', ->
    board.clear()
    expect( board.get('spaces') ).toEqual [null, null, null, null, null, null, null, null, null]
    
  it 'checks to see if a space is open', ->
    expect( board.spaceOpen(1) ).toEqual true
    
  it 'locks/unlocks the board', ->
    board.lock()
    expect( board.get('locked') ).toEqual true
    board.unlock()
    expect( board.get('locked') ).toEqual false