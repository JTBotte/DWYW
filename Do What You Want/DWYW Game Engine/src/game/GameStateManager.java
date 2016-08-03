package game;

public class GameStateManager {

	State game_state = State.MAIN_MENU;

	public State getState() {
		return game_state;
	}
	
	public void setState(State state){
		game_state = state;
	}
}
