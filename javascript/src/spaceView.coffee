class window.Planet
	constructor: ->
    @sphere = new THREE.SphereGeometry(60, 200, 200)
    texture = new THREE.ImageUtils.loadTexture('/images/earth.jpg')
    @material = new THREE.MeshLambertMaterial({map: texture})
    return new THREE.Mesh(@sphere, @material)
    
class window.Clouds
	constructor: ->
    @sphere = new THREE.SphereGeometry(60.1, 200, 200)
    texture = new THREE.ImageUtils.loadTexture('/images/clouds.png')
    @material = new THREE.MeshLambertMaterial({map: texture, transparent: true, castShadow: true})
    return new THREE.Mesh(@sphere, @material)

class window.SpaceView
  constructor: ->
    @scene = new THREE.Scene
    @camera = this.setupCamera()
    @planet = new Planet
    @clouds = new Clouds
    @renderer = new THREE.WebGLRenderer
    
    this.prepareScene()
    this.prepareDOM()
    
  prepareScene: =>
    @scene.add this.sunlight()
    @scene.add @planet
    @scene.add @clouds
     
  prepareDOM: =>
    @renderer.setSize($(window).innerWidth(), $(window).innerHeight())
    $('body').append @renderer.domElement

  render: =>
    callback = (=> @render())
    @planet.rotation.y += 0.0002
    @clouds.rotation.y += 0.00025
    @renderer.render @scene, @camera
    requestAnimationFrame(callback)
  
  setupCamera: ->
    camera = new THREE.PerspectiveCamera(75, $(window).innerWidth() / $(window).innerHeight(), 1, 1000)
    camera.position.z = 90
    camera.position.x = 15
    camera.position.y = 15
    camera
    
  sunlight: =>
    directionalLight = new THREE.DirectionalLight(0xfff5ec, 2, 20)
    directionalLight.position.set(-30, -20, 30).normalize()
    directionalLight.target = @planet
    directionalLight.showCascade = true
    directionalLight
