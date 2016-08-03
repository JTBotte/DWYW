package input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

public class ControllerInput {

	static Controller controller;

	public static Controller getController() {
		try {
			Controllers.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Controllers.poll();
		for (int i = 0; i < Controllers.getControllerCount(); i++) {
			controller = Controllers.getController(i);
		}
		return Controllers.getController(0);
	}
}