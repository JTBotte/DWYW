package entities;

import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import terrains.Terrain;
import textures.ModelTexture;

public class GameObject {
	private String fileName;
	private Loader loader;
	private ModelData data;
	private RawModel rawModel;
	private TexturedModel texturedModel;
	private String textureName;
	private ModelTexture texture;
	private int shine;
	private int reflectivity;
	private boolean transparent;
	private boolean fakeLighting;

	public GameObject(String fileName, Loader loader, String textureName, int shine, int reflectivity,
			boolean transparent, boolean fakeLighting, boolean firstPerson) {
		this.fileName = fileName;
		this.loader = loader;
		this.shine = shine;
		this.reflectivity = reflectivity;
		this.transparent = transparent;
		this.fakeLighting = fakeLighting;
		this.textureName = textureName;
		this.data = OBJFileLoader.loadOBJ(this.fileName);
		this.rawModel = this.loader.loadToVao(this.data.getVertices(), this.data.getTextureCoords(),
				this.data.getNormals(), this.data.getIndices());
		this.texturedModel = new TexturedModel(this.rawModel,
				new ModelTexture(loader.loadGameTexture(this.textureName)));
		this.texture = this.texturedModel.getTexture();
		this.texture.setShineDamper(this.shine);
		this.texture.setReflectivity(this.reflectivity);
		this.texture.setHasTransparency(this.transparent);
		this.texture.setUseFakeLighting(this.fakeLighting);
		if (firstPerson) {
			Player.firstPerson = true;
		}
	}

	public GameObject(String fileName, Loader loader, String textureName, int shine, int reflectivity,
			boolean transparent, boolean fakeLighting, int numberOfRows) {
		this.fileName = fileName;
		this.loader = loader;
		this.shine = shine;
		this.reflectivity = reflectivity;
		this.transparent = transparent;
		this.fakeLighting = fakeLighting;
		this.textureName = textureName;
		this.data = OBJFileLoader.loadOBJ(this.fileName);
		this.rawModel = this.loader.loadToVao(this.data.getVertices(), this.data.getTextureCoords(),
				this.data.getNormals(), this.data.getIndices());
		ModelTexture modelTexture = new ModelTexture(loader.loadGameTexture(this.textureName));
		modelTexture.setNumberOfRows(numberOfRows);
		this.texturedModel = new TexturedModel(this.rawModel, modelTexture);
		this.texture = this.texturedModel.getTexture();
		this.texture.setShineDamper(this.shine);
		this.texture.setReflectivity(this.reflectivity);
		this.texture.setHasTransparency(this.transparent);
		this.texture.setUseFakeLighting(this.fakeLighting);
	}

	public GameObject(String fileName, Loader loader, String textureName, int shine, int reflectivity,
			boolean transparent, boolean fakeLighting) {
		this.fileName = fileName;
		this.loader = loader;
		this.shine = shine;
		this.reflectivity = reflectivity;
		this.transparent = transparent;
		this.fakeLighting = fakeLighting;
		this.textureName = textureName;
		this.data = OBJFileLoader.loadOBJ(this.fileName);
		this.rawModel = this.loader.loadToVao(this.data.getVertices(), this.data.getTextureCoords(),
				this.data.getNormals(), this.data.getIndices());
		this.texturedModel = new TexturedModel(this.rawModel,
				new ModelTexture(loader.loadGameTexture(this.textureName)));
		this.texture = this.texturedModel.getTexture();
		this.texture.setShineDamper(this.shine);
		this.texture.setReflectivity(this.reflectivity);
		this.texture.setHasTransparency(this.transparent);
		this.texture.setUseFakeLighting(this.fakeLighting);
	}

	public GameObject(String fileName, Loader loader, String textureName, String normalMap, int shine,
			int reflectivity) {
		this.fileName = fileName;
		this.shine = (int) shine;
		this.reflectivity = (int) reflectivity;
		this.textureName = textureName;
		this.texturedModel = new TexturedModel(NormalMappedObjLoader.loadOBJ(this.fileName, loader),
				new ModelTexture(loader.loadGameTexture(this.textureName)));
		this.texture = this.texturedModel.getTexture();
		this.texture.setShineDamper(this.shine);
		this.texture.setReflectivity(this.reflectivity);
		this.texture.setNormalMap(loader.loadGameTexture(normalMap));
	}

	public TexturedModel getTexturedModel() {
		return texturedModel;
	}

	public static List<Entity> createAmount(int amount, List<Entity> entities, Terrain terrain, GameObject gameObject,
			float scale) {
		Random random = new Random();
		for (int i = 0; i < amount; i++) {
			float x = random.nextFloat() * -800;
			float z = random.nextFloat() * -600;
			float y = terrain.getHeightOfTerrain(x, z);
			entities.add(new Entity(gameObject, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360,
					0, scale));
		}
		return entities;
	}
}
