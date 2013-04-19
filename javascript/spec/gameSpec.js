// Generated by CoffeeScript 1.6.2
(function() {
  describe('Game', function() {
    var game, mockResponse;

    game = null;
    mockResponse = {
      newBoard: {
        success: {
          status: 200,
          responseText: '{"marker" : "x", "move" : "first-move", "player1": {"type":"human", "marker":"x"}, "player2": {"type":"human", "marker":"o"}, "board":["nil", "nil", "nil", "nil", "nil", "nil", "nil", "nil", "nil"], "message": ""}'
        }
      }
    };
    beforeEach(function() {
      var spy;

      game = new Game;
      return spy = spyOn($, "ajax").andCallFake(function() {
        return game.parse(mockResponse.newBoard.success);
      });
    });
    it('constructs a game object', function() {
      return expect(game).toBeDefined();
    });
    it('has a board, players and a current player', function() {
      expect(game.get('board')).toToBeDefined;
      expect(game.get('players')).toEqual({
        player1: {
          type: 'human',
          marker: 'x'
        },
        player2: {
          type: 'easy',
          marker: 'o'
        }
      });
      return expect(game.get('currentPlayer')).toEqual('x');
    });
    it('has a default url', function() {
      return expect(game.url()).toEqual('/json');
    });
    it('parses the hash returned from the server', function() {
      var response;

      response = JSON.parse(mockResponse.newBoard.success.responseText);
      game.sync();
      expect(game.sync).toHaveBeenCalled;
      expect(game.get('board').get('spaces')).toEqual([null, null, null, null, null, null, null, null, null]);
      expect(game.get('currentPlayer')).toEqual('x');
      return expect(game.get('players')).toEqual({
        player1: response.player1,
        player2: response.player2
      });
    });
    it('returns a hash of the object\'s data', function() {
      return expect(game.dataHash()).toEqual({
        0: 'null',
        1: 'null',
        2: 'null',
        3: 'null',
        4: 'null',
        5: 'null',
        6: 'null',
        7: 'null',
        8: 'null',
        move: 'first-move',
        marker: 'x',
        player1: {
          type: 'human',
          marker: 'x'
        },
        player1type: 'human',
        player2: {
          type: 'easy',
          marker: 'o'
        },
        player2type: 'easy'
      });
    });
    return it('returns a hash representation of the board', function() {
      return expect(game.boardHash()).toEqual({
        0: 'null',
        1: 'null',
        2: 'null',
        3: 'null',
        4: 'null',
        5: 'null',
        6: 'null',
        7: 'null',
        8: 'null'
      });
    });
  });

}).call(this);
