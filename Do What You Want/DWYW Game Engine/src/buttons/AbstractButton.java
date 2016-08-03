package buttons;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import guis.GuiTexture;
import renderEngine.DisplayManager;
import renderEngine.Loader;

public abstract class AbstractButton implements IButton {

	private GuiTexture guiTexture;
	private int texture;
	private Vector2f position;
	private Vector2f scale;
	private Vector2f originalScale;
	private boolean isHidden = true, isHovering = false;
	private Loader loader;

	public AbstractButton(Loader loader, String texture, Vector2f position, Vector2f scale) {
		this.loader = loader;
		this.texture = loader.loadGameTexture(texture);
		this.position = position;
		this.scale = scale;
		this.guiTexture = new GuiTexture(this.texture, this.position, this.scale);
		originalScale = scale;
	}

	public void update() {
		if (!isHidden) {
			Vector2f location = guiTexture.getPosition();
			Vector2f scale = guiTexture.getScale();
			Vector2f mouseCoords = DisplayManager.getNormalizedMouseCoordinates();

			if (location.y + scale.y > -mouseCoords.y && location.y - scale.y < -mouseCoords.y
					&& location.x + scale.x > mouseCoords.x && location.x - scale.x < mouseCoords.x) {
				whileHovering(this);
				if (!isHovering) {
					isHovering = true;
					onStartHover(this);
				}
				while (Mouse.next())
					if (Mouse.isButtonDown(0))
						onClick(this);
			}

			else if (isHovering) {
				isHovering = false;
				onStopHover(this);
			}
		}
	}

	public void show(List<GuiTexture> guitextureList) {
		if (isHidden) {
			guitextureList.add(guiTexture);
			isHidden = false;
		}
	}

	public void hide(List<GuiTexture> guitextureList) {
		if (!isHidden) {
			guitextureList.remove(guiTexture);
			isHidden = true;
		}
	}

	public void resetScale() {
		guiTexture.setScale(originalScale);
	}

	public void playHoverAnimation(float scaleFactor) {
		guiTexture.setScale(new Vector2f(originalScale.x + scaleFactor, originalScale.y + scaleFactor));
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void changeTexture(String texture, List<GuiTexture> guis) {
		this.hide(guis);
		this.texture = loader.loadGameTexture(texture);
		this.guiTexture = new GuiTexture(this.texture, position, originalScale);
		this.show(guis);
	}
}
