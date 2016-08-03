package entities;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private float distanceFromPlayer = 0;
	private float angleAroundPlayer = 0;
	private static float offset = 10;

	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch;
	private float yaw;
	private float roll;

	private Player player;

	public Camera(Player player) {
		this.player = player;
	}

	public void move(Controller controller) {
		// TODO SET MAX YAW
		// TODO SET MIN ZOOM DISTANCE
		// TODO SET Y OFFSET TO CENTER CAM ON BODY
		calculateZoom();
		processMouse();
		calculatePitch(controller);
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticDistance + offset;
	}

	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}

	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}

	private void calculateZoom() {
		if (!Player.firstPerson) {
			float zoomLevel = Mouse.getDWheel() * 0.1f;
			distanceFromPlayer -= zoomLevel;
		}
	}

	private void calculatePitch(Controller controller) {
		if (Player.isController) {
			controller.setRYAxisDeadZone(0.3f);
			final float MAX_UP = 90;
			final float MAX_DOWN = -90;
			if (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				float controllerDY = -controller.getRYAxisValue();
				if (pitch - controllerDY >= MAX_DOWN && pitch - controllerDY <= MAX_UP) {
					pitch += -controllerDY;
				} else if (pitch - controllerDY < MAX_DOWN) {
					pitch = MAX_DOWN;
				} else if (pitch - controllerDY > MAX_UP) {
					pitch = MAX_UP;
				}
			}
		} else {
			if (Mouse.isButtonDown(1)) {
				float pitchChange = Mouse.getDY() * 0.1f;
				pitch -= pitchChange;

			}
		}
	}

	private void calculateAngleAroundPlayer() {
		if (Mouse.isButtonDown(0) && !Player.isController && !Player.firstPerson) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}

	private void processMouse() {
		if (Player.firstPerson && !Player.isController) {
			final float MAX_UP = 90;
			final float MAX_DOWN = -90;
			if (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				float mouseDY = Mouse.getDY() * 0.16f;
				if (pitch - mouseDY >= MAX_DOWN && pitch - mouseDY <= MAX_UP) {
					pitch += -mouseDY;
				} else if (pitch - mouseDY < MAX_DOWN) {
					pitch = MAX_DOWN;
				} else if (pitch - mouseDY > MAX_UP) {
					pitch = MAX_UP;
				}
			}
		}
	}

	public void invertPitch() {
		this.pitch = -pitch;
	}

	public void invertYaw() {
		this.yaw = -yaw;
	}

	public static void setOffset(float offset) {
		Camera.offset = offset;
	}

}
