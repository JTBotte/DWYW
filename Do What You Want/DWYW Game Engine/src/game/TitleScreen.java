package game;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import buttons.AbstractButton;
import buttons.IButton;
import entities.Camera;
import entities.Player;
import guis.GuiTexture;
import particles.Particle;
import renderEngine.Loader;
import terrains.Terrain;

public class TitleScreen {

	public void createButtons(List<AbstractButton> buttons, Loader loader, GameStateManager manager,
			List<GuiTexture> guis) {
		AbstractButton start = new AbstractButton(loader, "startButton", new Vector2f(0, -.85f),
				new Vector2f(0.2f, 0.1f)) {
			@Override
			public void whileHovering(IButton button) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopHover(IButton button) {
				// TODO: FIX
			}

			@Override
			public void onStartHover(IButton button) {
				// TODO: FIX
			}

			@Override
			public void onClick(IButton button) {
				manager.setState(State.IN_GAME);
			}

		};

		AbstractButton perspective = new AbstractButton(loader, "perspective3", new Vector2f(-.88f, .65f),
				new Vector2f(0.1f, 0.1f)) {

			@Override
			public void whileHovering(IButton button) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopHover(IButton button) {
				// TODO: FIX

			}

			@Override
			public void onStartHover(IButton button) {
				// TODO: FIX

			}

			int i = 0;

			@Override
			public void onClick(IButton button) {
				i++;
				if (i == 3) {
					Player.firstPerson = true;
					Player.isController = true;
					changeTexture("controller", guis);
					i = 0;
				} else if (Player.firstPerson) {
					Player.firstPerson = false;
					Player.isController = false;
					Camera.setOffset(10);
					changeTexture("perspective3", guis);
				} else if (!Player.firstPerson) {
					Player.firstPerson = true;
					Player.isController = false;
					Camera.setOffset(20);
					changeTexture("perspective1", guis);
				}
			}
		};
		AbstractButton particles = new AbstractButton(loader, "particles", new Vector2f(-.88f, .40f),
				new Vector2f(0.1f, 0.1f)) {

			@Override
			public void whileHovering(IButton button) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopHover(IButton button) {
				// TODO: FIX

			}

			@Override
			public void onStartHover(IButton button) {
				// TODO: FIX

			}

			@Override
			public void onClick(IButton button) {
				if (Particle.particles) {
					Particle.particles = false;
					changeTexture("particlesNone", guis);
				} else if (!Particle.particles) {
					Particle.particles = true;
					changeTexture("particles", guis);
				}
			}
		};
		AbstractButton terrainButton = new AbstractButton(loader, "terrainButton", new Vector2f(-.88f, .15f),
				new Vector2f(0.1f, 0.1f)) {

			@Override
			public void whileHovering(IButton button) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopHover(IButton button) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartHover(IButton button) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onClick(IButton button) {
				if (Terrain.useRandom) {
					Terrain.useRandom = false;
					changeTexture("terrainButton", guis);
				} else if (!Terrain.useRandom) {
					Terrain.useRandom = true;
					changeTexture("randTerrainButton", guis);
				}

			}
		};
		buttons.add(start);
		buttons.add(perspective);
		buttons.add(particles);
		buttons.add(terrainButton);
	}
}
