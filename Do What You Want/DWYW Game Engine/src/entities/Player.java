package entities;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity {

	public static final float WALK_SPEED = 20;
	public static final float RUN_SPEED = 40;
	private static final float TURN_SPEED = 160;
	public static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;

	public static boolean isController = false;
	public static boolean firstPerson = false;

	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;

	private boolean isInAir = false;

	public Player(GameObject obj, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(obj, position, rotX, rotY, rotZ, scale);
	}

	public void move(Terrain terrain, Controller controller) {
		checkInputs(controller);
		processMouse();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		// float sdx = 0;
		// float sdz = 0;
		float dx = 0;
		float dz = 0;
		float x = 0;
		float z = 0;
		// sdx = (float) (distance * Math.sin(Math.toRadians(super.getRotY() -
		// 90)));
		// sdz = (float) (distance * Math.cos(Math.toRadians(super.getRotY() -
		// 90)));
		dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		// if(sdx != 0)
		// x = (sdx + dx) / 2;
		// else if (sdx == 0)
		x = dx;
		// if(dx != 0)
		// z = (sdz + dz) / 2;
		// else if(dx == 0)
		z = dz;
		super.increasePosition(x, 0, z);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if (super.getPosition().y < terrainHeight) {
			upwardsSpeed = 0;
			super.getPosition().y = terrainHeight;
			isInAir = false;
		}
	}

	private void jump() {
		if (!isInAir) {
			this.upwardsSpeed = JUMP_POWER;
			isInAir = true;
		}
	}

	private void processMouse() {
		if (firstPerson && !isController) {
			float mouseDX = Mouse.getDX() * -0.16f;
			// int buffer = 500;
			if (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && Mouse.isInsideWindow()) {
				// System.out.println(Mouse.getX() + "|" + Mouse.getY());
				if (getRotY() + mouseDX >= 360) {
					increaseRotation(0, mouseDX - 360, 0);
				} else if (getRotY() + mouseDX < 0) {
					increaseRotation(0, 360 - mouseDX, 0);
				} else {
					increaseRotation(0, mouseDX, 0);

				}
				/*
				 * if (Mouse.getX() <= buffer || !Mouse.isInsideWindow()) {
				 * Mouse.setCursorPosition(Display.getWidth() - buffer - 5,
				 * Mouse.getY() + Mouse.getDY()); while (Mouse.next()) {
				 * Mouse.setGrabbed(true); if (Mouse.isInsideWindow()) {
				 * Mouse.setGrabbed(false); continue; } } } else if
				 * (Mouse.getX() >= Display.getWidth() - buffer ||
				 * !Mouse.isInsideWindow()) { Mouse.setCursorPosition(buffer +
				 * 5, Mouse.getY() + Mouse.getDY()); while (Mouse.next()) {
				 * Mouse.setGrabbed(true); if (Mouse.isInsideWindow()) {
				 * Mouse.setGrabbed(false); continue; } } }
				 */
			}
		}
	}

	private void checkInputs(Controller controller) {
		if (isController) {
			try {
				if (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					float moveX = controller.getXAxisValue();
					float moveY = controller.getYAxisValue();
					if (moveX != 0 && moveY != 0) {
						this.currentSpeed = -RUN_SPEED * moveY;
					} else {
						this.currentSpeed = 0;
					}
					controller.setRXAxisDeadZone(0.3f);
					float rotX = controller.getRXAxisValue();
					if (rotX != 0)
						this.currentTurnSpeed = (-TURN_SPEED * rotX);
					else
						this.currentTurnSpeed = 0;
				}
				if (controller.isButtonPressed(0)) {
					jump();
				}
			} catch (Exception ex) {
				isController = false;
				firstPerson = false;
				System.err.println("No Controller Connected!\nSwitching To 3rd Person Mode!");
			}
		} else {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					this.currentSpeed = RUN_SPEED;
				} else {
					this.currentSpeed = WALK_SPEED;
				}
			} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					this.currentSpeed = -RUN_SPEED;
				} else {
					this.currentSpeed = -WALK_SPEED;
				}
			} else {
				this.currentSpeed = 0;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				if (firstPerson && !isController) {
					this.currentSpeed = WALK_SPEED;
				} else {
					this.currentTurnSpeed = -TURN_SPEED;
				}
			} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				if (firstPerson) {
					this.currentSpeed = -WALK_SPEED;
				} else {
					this.currentTurnSpeed = TURN_SPEED;
				}
			} else {
				this.currentTurnSpeed = 0;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				jump();
			}
		}
	}

}
