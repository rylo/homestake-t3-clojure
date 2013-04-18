window.Game = class Game
  class Game extends Backbone.Model
    defaults:
      board: new Board
      players: { player1: 'x', player2: 'o' }
      currentPlayer: { player1: 'x' }