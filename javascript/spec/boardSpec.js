// Generated by CoffeeScript 1.6.2
(function() {
  describe('Board', function() {
    var board;

    board = null;
    beforeEach(function() {
      return board = new Board;
    });
    it('constructs a Board object', function() {
      return expect(board).not.toBe(null);
    });
    it('has a spaces array, filled with nulls', function() {
      return expect(board.get('spaces')).toEqual([null, null, null, null, null, null, null, null, null]);
    });
    return it('sets a marker on a board with a given index', function() {
      board.setSpace(1, 'x');
      return expect(board.get('spaces')).toEqual([null, 'x', null, null, null, null, null, null, null]);
    });
  });

}).call(this);
