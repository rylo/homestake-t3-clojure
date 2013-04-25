// Generated by CoffeeScript 1.6.2
(function() {
  describe('GameView', function() {
    var game, gameView, mockResponse;

    game = null;
    gameView = null;
    mockResponse = {
      newBoard: {
        success: {
          status: 200,
          responseText: {
            marker: "x",
            move: "first-move",
            player1: {
              type: "human",
              marker: "x"
            },
            player2: {
              type: "human",
              marker: "o"
            },
            board: ["nil", "nil", "nil", "nil", "nil", "nil", "nil", "nil", "nil"],
            message: ""
          }
        }
      }
    };
    beforeEach(function() {
      var spy;

      game = new Game;
      gameView = new GameView({
        model: game
      });
      return spy = spyOn($, "ajax").andCallFake(function() {
        return mockResponse.newBoard.success;
      });
    });
    it('constructs a Board object', function() {
      return expect(gameView).not.toBe(null);
    });
    it('renders all of a board\'s spaces', function() {
      expect(gameView.renderBoard()).toContain('<div id=\"0\" class=\"space\"></div>');
      return expect(gameView.renderBoard().length).toEqual(9);
    });
    it('makes a move on and re-renders the board', function() {
      gameView.makeMove({
        target: 1
      });
      return expect(Game.render).toHaveBeenCalled;
    });
    return it('renders a game message', function() {
      expect(gameView.renderMessage()).toEqual('<h2>New Game</h2>');
      game.set('message', 'o hai dere');
      return expect(gameView.renderMessage()).toEqual('<h2>o hai dere</h2>');
    });
  });

}).call(this);
