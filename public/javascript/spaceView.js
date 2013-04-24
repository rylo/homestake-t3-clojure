// Generated by CoffeeScript 1.6.2
(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  window.Planet = (function() {
    function Planet() {
      var texture;

      this.sphere = new THREE.SphereGeometry(60, 200, 200);
      texture = new THREE.ImageUtils.loadTexture('/images/earth.jpg');
      this.material = new THREE.MeshLambertMaterial({
        map: texture
      });
      return new THREE.Mesh(this.sphere, this.material);
    }

    return Planet;

  })();

  window.Clouds = (function() {
    function Clouds() {
      var texture;

      this.sphere = new THREE.SphereGeometry(60.1, 200, 200);
      texture = new THREE.ImageUtils.loadTexture('/images/clouds.png');
      this.material = new THREE.MeshLambertMaterial({
        map: texture,
        transparent: true,
        castShadow: true
      });
      return new THREE.Mesh(this.sphere, this.material);
    }

    return Clouds;

  })();

  window.SpaceView = (function() {
    function SpaceView() {
      this.sunlight = __bind(this.sunlight, this);
      this.render = __bind(this.render, this);
      this.prepareDOM = __bind(this.prepareDOM, this);
      this.prepareScene = __bind(this.prepareScene, this);      this.scene = new THREE.Scene;
      this.camera = this.setupCamera();
      this.planet = new Planet;
      this.clouds = new Clouds;
      this.renderer = new THREE.WebGLRenderer;
      this.prepareScene();
      this.prepareDOM();
    }

    SpaceView.prototype.prepareScene = function() {
      this.scene.add(this.sunlight());
      this.scene.add(this.planet);
      return this.scene.add(this.clouds);
    };

    SpaceView.prototype.prepareDOM = function() {
      this.renderer.setSize($(window).innerWidth(), $(window).innerHeight());
      return $('body').append(this.renderer.domElement);
    };

    SpaceView.prototype.render = function() {
      var callback,
        _this = this;

      callback = (function() {
        return _this.render();
      });
      this.planet.rotation.y += 0.0002;
      this.clouds.rotation.y += 0.00025;
      this.renderer.render(this.scene, this.camera);
      return requestAnimationFrame(callback);
    };

    SpaceView.prototype.setupCamera = function() {
      var camera;

      camera = new THREE.PerspectiveCamera(75, $(window).innerWidth() / $(window).innerHeight(), 1, 1000);
      camera.position.z = 90;
      camera.position.x = 15;
      camera.position.y = 15;
      return camera;
    };

    SpaceView.prototype.sunlight = function() {
      var directionalLight;

      directionalLight = new THREE.DirectionalLight(0xfff5ec, 2, 20);
      directionalLight.position.set(-30, -20, 30).normalize();
      directionalLight.target = this.planet;
      directionalLight.showCascade = true;
      return directionalLight;
    };

    return SpaceView;

  })();

}).call(this);
