package entities;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import terrains.Terrain;

public class Lamp {

	private GameObject gameObject;
	private float positionX;
	private float positionZ;
	private Vector3f rotation;
	private float scale;
	private Vector3f lampPosition;
	private Vector3f lightPostion;
	private Vector3f color;
	private Vector3f attenuation;

	public Lamp(GameObject gameObject, float positionX, float positionZ, Vector3f rotation, float scale,
			List<Light> lights, List<Entity> entities, Terrain terrain, Vector3f color, Vector3f attenuation) {
		this.gameObject = gameObject;
		this.positionX = positionX;
		this.positionZ = positionZ;
		this.rotation = rotation;
		this.scale = scale;
		this.lampPosition = new Vector3f(positionX, terrain.getHeightOfTerrain(positionX, positionZ), positionZ);
		this.lightPostion = new Vector3f(positionX, terrain.getHeightOfTerrain(positionX, positionZ) + 15, positionZ);
		entities.add(new Entity(gameObject, lampPosition, rotation.x, rotation.y, rotation.z, scale));
		lights.add(new Light(lightPostion, color, attenuation));
	}

	public GameObject getGameObject() {
		return gameObject;
	}

	public float getPositionX() {
		return positionX;
	}

	public float getPositionZ() {
		return positionZ;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}

	public Vector3f getLampPosition() {
		return lampPosition;
	}

	public Vector3f getLightPostion() {
		return lightPostion;
	}

	public Vector3f getColor() {
		return color;
	}

	public Vector3f getAttenuation() {
		return attenuation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

}
