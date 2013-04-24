// Generated by CoffeeScript 1.6.2
(function() {
  var _ref,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  window.ConfigView = (function(_super) {
    __extends(ConfigView, _super);

    function ConfigView() {
      this.updateConfiguration = __bind(this.updateConfiguration, this);      _ref = ConfigView.__super__.constructor.apply(this, arguments);
      return _ref;
    }

    ConfigView.prototype.el = '#config';

    ConfigView.prototype.events = {
      'change input:radio[name=type]': 'updateConfiguration',
      'click .new-game': 'newGame'
    };

    ConfigView.prototype.initialize = function() {
      return this.render();
    };

    ConfigView.prototype.updateConfiguration = function(event) {
      var player, type;

      player = event.target.parentElement.parentElement.parentElement.id;
      type = event.target.value;
      console.log("" + player + " " + type);
      if (player === 'player1') {
        return this.model.get('players').player1.type = type;
      } else {
        return this.model.get('players').player2.type = type;
      }
    };

    ConfigView.prototype.render = function() {
      var data, html;

      if (this.model.get('finished')) {
        data = this.model.get('players');
        html = "      <div class='column' id='player1'>" + (this.renderPlayerOptions(data.player1.type, data.player1.marker)) + "</div>      <div class='column' id='player2'>" + (this.renderPlayerOptions(data.player2.type, data.player2.marker)) + "</div>      <div class='new-game button'>Start</div>";
        return $(this.el).html(html);
      }
    };

    ConfigView.prototype.renderPlayerOptions = function(playerType, marker) {
      return "<h3>Player <span class='marker'>" + marker + "</span></h3>      <form class='type'>        <label><input class='regular-checkbox' type='radio' name='type' value='human'" + (this.isChecked(playerType, 'human')) + ">Human</label><br />        <label><input class='regular-checkbox' type='radio' name='type' value='easy'" + (this.isChecked(playerType, 'easy')) + ">Easy AI</label><br />        <label><input class='regular-checkbox' type='radio' name='type' value='ultimate'" + (this.isChecked(playerType, 'ultimate')) + ">Ultimate AI</label><br />      </form>";
    };

    ConfigView.prototype.isChecked = function(playerType, checkboxType) {
      if (playerType === checkboxType) {
        return " checked='checked'";
      }
    };

    ConfigView.prototype.newGame = function() {
      this.model.newGame();
      this.model.get('board').unlock();
      return $(this.el).html("");
    };

    ConfigView.prototype.initialize = function() {
      return this.model.on('change', this.render, this);
    };

    return ConfigView;

  })(Backbone.View);

}).call(this);
