class window.Planet
	constructor: ->
    @sphere = new THREE.SphereGeometry(60, 200, 200)
    texture = new THREE.ImageUtils.loadTexture('/images/earth.jpg')
    bumpMap = new THREE.ImageUtils.loadTexture('/images/earth_bumpmap.jpg')
    @material = new THREE.MeshPhongMaterial({map: texture, bumpMap: bumpMap, bumpScale: 2})
    return new THREE.Mesh(@sphere, @material)
    
class window.Luna
  constructor: ->
    @sphere = new THREE.SphereGeometry(40, 30, 30)
    @texture = new THREE.ImageUtils.loadTexture('/images/moon.jpg')
    bumpMap = new THREE.ImageUtils.loadTexture('/images/moon_bumpmap.jpg')
    @material = new THREE.MeshLambertMaterial({
      map: @texture, 
      castShadow: false, 
      receiveShadow: true,
      bumpMap: bumpMap, 
      bumpScale: 5
    })
    @moon = new THREE.Mesh(@sphere, @material)
    @moon.position.z = -200
    @moon.position.x = 200
    return @moon

# class window.ISS
#   constructor: ->
#     model = new THREE.Object3D()       
#     loader = new THREE.GeometryLoader()
#     loader.load('/images/ISSComplete1.json')
    
class window.Clouds
	constructor: ->
    @sphere = new THREE.SphereGeometry(60.5, 200, 200)
    texture = new THREE.ImageUtils.loadTexture('/images/clouds.png')
    bumpMap = new THREE.ImageUtils.loadTexture('/images/clouds.png')
    @material = new THREE.MeshLambertMaterial({
      map: texture, 
      transparent: true, 
      castShadow: true, 
      bumpMap: bumpMap, 
      bumpScale: 5
    })
    return new THREE.Mesh(@sphere, @material)

class window.SpaceView
  constructor: ->
    @scene = new THREE.Scene
    @planet = new Planet
    @luna = new Luna
    @clouds = new Clouds
    @renderer = new THREE.WebGLRenderer
    @camera = this.setupCamera()
    
    this.prepareDOM()
    this.prepareScene()
    
  prepareScene: =>
    @scene.add this.sunlight()
    @scene.add @planet
    @scene.add @luna
    @scene.add @clouds
     
  prepareDOM: =>
    @renderer.setSize($(window).innerWidth(), $(window).innerHeight())
    $('body').append @renderer.domElement

  render: =>
    callback = (=> @render())
    @planet.rotation.y += 0.0002
    @clouds.rotation.y += 0.00025
    this.animateLuna()
    # this.animateCamera()
    @renderer.render @scene, @camera
    requestAnimationFrame(callback)

  animateCamera: ->    
    theta = 0.01
    x = @camera.position.x
    z = @camera.position.z
    @camera.position.x = x * Math.cos(theta) + z * Math.sin(theta)
    @camera.position.z = z * Math.cos(theta) - x * Math.sin(theta)
    @camera.lookAt(@planet.position)
    
  animateLuna: ->
    @luna.rotation.y += 0.002
    
    theta = 0.002
    x = @luna.position.x
    z = @luna.position.z
    @luna.position.x = x * Math.cos(theta) + z * Math.sin(theta)
    @luna.position.z = z * Math.cos(theta) - x * Math.sin(theta)
  
  setupCamera: ->
    camera = new THREE.PerspectiveCamera(75, $(window).innerWidth() / $(window).innerHeight(), 1, 1000)
    camera.position.z = 80
    camera.position.y = 100
    camera.lookAt(@planet.position)
    camera.position.x = 80
    camera
    
  sunlight: =>
    directionalLight = new THREE.DirectionalLight(0xffffff, 1, 2)
    directionalLight.position.set(100, 0, 100).normalize()
    directionalLight.target = @planet
    directionalLight.showCascade = true
    directionalLight
