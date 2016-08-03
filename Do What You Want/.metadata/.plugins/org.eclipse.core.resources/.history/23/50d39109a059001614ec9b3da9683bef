package game;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Controller;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import audio.AudioMaster;
import buttons.AbstractButton;
import entities.Camera;
import entities.Entity;
import entities.GameObject;
import entities.Lamp;
import entities.Light;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import input.ControllerInput;
import particles.ComplexParticleSystem;
import particles.ParticleMaster;
import particles.ParticleTexture;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		GameStateManager state_manager = new GameStateManager();
		TitleScreen titleScreen = new TitleScreen();
		TextMaster.init(loader);
		
		List<Entity> entities = new ArrayList<Entity>();
		GameObject character = new GameObject("bluedevil", loader, "bluedevil", 10, 1, false, false, false);
		
		// All Players / Cameras Go Below
		Player player = new Player(character, new Vector3f(-600, 0, -600), 0, 230, 0, 1);
		entities.add(player);
		Camera camera = new Camera(player);
		
		MasterRenderer renderer = new MasterRenderer(loader, camera);
		WaterShader waterShader = new WaterShader();
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		AudioMaster.init();
		AudioMaster.setListenerData(0, 0, 0);

		// Controller
		Controller controller = ControllerInput.getController();

		// Particles!!
		ParticleTexture fireParticleTexture = new ParticleTexture(loader.loadGameTexture("fire"), 8);
		ParticleTexture smokeParticleTexture = new ParticleTexture(loader.loadGameTexture("smoke"), 8);

		ComplexParticleSystem smoke = new ComplexParticleSystem(smokeParticleTexture, 25, 5, 0, 15, 15);
		smoke.randomizeRotation();
		smoke.setDirection(new Vector3f(0, 1, 0), 0.18f);
		smoke.setSpeedError(0.5f);
		smoke.setScaleError(0.7f);
		ComplexParticleSystem barrelFire = new ComplexParticleSystem(fireParticleTexture, 25, 5, 0, 5, 15);
		barrelFire.randomizeRotation();
		barrelFire.setDirection(new Vector3f(0, 1, 0), 0.15f);
		barrelFire.setLifeError(0.1f);
		barrelFire.setSpeedError(0.25f);
		barrelFire.setScaleError(0.5f);

		// Create Lists
		List<Entity> normalMapEntities = new ArrayList<Entity>();
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		List<Light> lights = new ArrayList<Light>();
		List<Terrain> terrains = new ArrayList<Terrain>();
		List<WaterTile> waters = new ArrayList<WaterTile>();
		List<AbstractButton> buttons = new ArrayList<AbstractButton>();

		// All Water Created Below
		WaterTile water = new WaterTile(-690, -682, 0, 110);
		waters.add(water);

		// All Game Objects Go Below
		GameObject grass = new GameObject("grassModel", loader, "grassTexture", 10, 0, true, true);
		GameObject fern = new GameObject("fern", loader, "fern", 10, 0, true, true, 2);
		GameObject tree = new GameObject("pine", loader, "pine", 10, 0, true, false);
		GameObject lamp = new GameObject("lamp", loader, "lamp", 10, 0, false, true);
		GameObject barrel = new GameObject("barrel", loader, "barrel", "barrelNormal", 10, 0);

		// Font goes below
		FontType titleFont = new FontType(loader.loadFontTexture("arial"), "arial");

		GUIText title = new GUIText("Do What You Want!", 4, titleFont, new Vector2f(0, 0.03f), 1f, true);
		title.setColour(2, 0, 0);

		// All GUIs Go Below

		// Add GUIs to list

		// All Terrain Textures Go Below
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadGameTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadGameTexture("sand"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadGameTexture("flower"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadGameTexture("stonePath"));
		TerrainTexture blendMap = new TerrainTexture(loader.loadGameTexture("blendMap"));
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

		// Terrain Should Be Created Below
		Terrain terrain = new Terrain(-1, -1, loader, texturePack, blendMap, "heightMap");

		// Add terrains to list
		terrains.add(terrain);

		// Normal Mapped Entities Created Below
		normalMapEntities.add(
				new Entity(barrel, new Vector3f(-600, terrain.getHeightOfTerrain(-600, -672) + 5, -672), 0, 0, 0, 1f));
		normalMapEntities.add(new Entity(barrel,
				new Vector3f(-600, terrain.getHeightOfTerrain(-600, -668) + 4.8f, -663), 0, 0, 0, 1f));
		normalMapEntities.add(
				new Entity(barrel, new Vector3f(-600, terrain.getHeightOfTerrain(-600, -670) + 17, -668), 0, 0, 0, 1f));
		normalMapEntities.add(
				new Entity(barrel, new Vector3f(-711, terrain.getHeightOfTerrain(-713, -557) + 5, -557), 0, 0, 0, 1f));
		
		entities.add(new Entity(tree, new Vector3f(-640, terrain.getHeightOfTerrain(-640, -720), -720), 0, 0, 0, 2));

		// Create Amounts Of Items
		GameObject.createAmount(200, entities, terrain, grass, 1);
		GameObject.createAmount(200, entities, terrain, fern, 1);
		GameObject.createAmount(75, entities, terrain, tree, 2);

		// TODO test blendMap color to prevent spawn in blue or red colored
		// areas
		// TODO Trees don't belong in water

		// Lamps Created Below
		new Lamp(lamp, -640, -710, new Vector3f(0, 0, 0), 1, lights, entities, terrain, new Vector3f(2, 0, 0),
				new Vector3f(1, 0.01f, 0.002f));
		new Lamp(lamp, -620, -670, new Vector3f(0, 0, 0), 1, lights, entities, terrain, new Vector3f(0, 2, 0),
				new Vector3f(1, 0.01f, 0.002f));
		new Lamp(lamp, -630, -640, new Vector3f(0, 0, 0), 1, lights, entities, terrain, new Vector3f(0, 0, 4),
				new Vector3f(1, 0.01f, 0.002f));

		// Lights Should Be Created Below
		Light sun = new Light(new Vector3f(1000000, 1500000, -1000000), new Vector3f(2, 2, 2));

		// All lights should be added to the list below
		lights.add(sun);
		GUIText version = new GUIText("In-dev" + " Version 0.5 ALPHA", 2, titleFont, new Vector2f(0, 0.03f), 1f, true);
		version.setColour(0, 0, 2);
		title.remove();
		
		//TODO Put in own class
		//int buffer = AudioMaster.loadSound("audio/blip.wav");
		//Source source = new Source();
		//source.setLoop(true);
		//source.play(buffer);
		//float xPos = 8;
		//source.setPosition(xPos, 0, 2);
		titleScreen.createButtons(buttons, loader, state_manager, guis);
		while (state_manager.getState() == State.MAIN_MENU && !Display.isCloseRequested()) {
			for (AbstractButton button : buttons) {
				button.show(guis);
				button.update();
			}
			guiRenderer.render(guis);
			TextMaster.render();
			DisplayManager.updateDisplay();
		}
		for (AbstractButton button1 : buttons) {
			button1.hide(guis);
		}
		
		while (state_manager.getState() == State.IN_GAME && !Display.isCloseRequested()) {
			//AUDIO CODE BELOW
			//xPos -= 0.03f;
			//source.setPosition(xPos, 0, 2);
			//AUDIO CODE ABOVE
			player.move(terrain, controller);
			camera.move(controller);
			smoke.generateParticles(new Vector3f(-711, terrain.getHeightOfTerrain(-711, -557) + 5, -557), true);
			barrelFire.generateParticles(new Vector3f(-711, terrain.getHeightOfTerrain(-711, -557) - 3, -557), false);
			ParticleMaster.update(camera);
			renderer.renderShadowMap(entities, sun);
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			buffers.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera,
					new Vector4f(0, 1, 0, -water.getHeight() + 1f));
			camera.getPosition().y += distance;
			camera.invertPitch();
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera,
					new Vector4f(0, -1, 0, water.getHeight() + 1f));
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			buffers.unbindCurrentFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 100000));
			waterRenderer.render(waters, camera, sun);
			ParticleMaster.renderParticles(camera);
			guiRenderer.render(guis);
			TextMaster.render();
			DisplayManager.updateDisplay();
		}
		ParticleMaster.cleanUp();
		TextMaster.cleanUp();
		AudioMaster.cleanUp();
		buffers.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
