// Generated by CoffeeScript 1.6.2
(function() {
  var _ref,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  window.GameView = (function(_super) {
    __extends(GameView, _super);

    function GameView() {
      this.renderBoard = __bind(this.renderBoard, this);      _ref = GameView.__super__.constructor.apply(this, arguments);
      return _ref;
    }

    GameView.prototype.el = '#board';

    GameView.prototype.messageElement = '#message';

    GameView.prototype.events = {
      'click .space': 'makeMove'
    };

    GameView.prototype.render = function() {
      $(this.el).html(this.renderBoard());
      return $(this.messageElement).html(this.renderMessage());
    };

    GameView.prototype.renderBoard = function() {
      var result, space_number, spaces, value;

      spaces = this.model.get('board').get('spaces');
      return result = (function() {
        var _results;

        _results = [];
        for (space_number in spaces) {
          value = spaces[space_number];
          if (value === null) {
            value = '';
          }
          _results.push("<div id=\"" + space_number + "\" class=\"space\">" + value + "</div>");
        }
        return _results;
      })();
    };

    GameView.prototype.renderMessage = function() {
      return "<h2>" + (this.model.get('message')) + "</h2>";
    };

    GameView.prototype.makeMove = function(event) {
      var board, spaceIndex;

      spaceIndex = event.target.id;
      board = this.model.get('board');
      if (board.spaceOpen(spaceIndex) && !board.get('locked')) {
        board.lock();
        board.setSpace(spaceIndex, this.model.get('currentPlayer'));
        this.model.sync();
        return this.render();
      }
    };

    GameView.prototype.initialize = function() {
      return this.model.on('change', this.render, this);
    };

    return GameView;

  })(Backbone.View);

}).call(this);
