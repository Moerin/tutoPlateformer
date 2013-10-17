package GameState;

public class GameStateManager {

	private GameState[] gameStates;
	private int currentState;

	// Propri�t� etat du jeu (Nombre, menu, niveau)
	public static final int NUMGAMESTATES = 2;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;

	// Constructeur
	public GameStateManager() {

		gameStates = new GameState[NUMGAMESTATES];

		currentState = MENUSTATE;
		loadState(currentState);
	}

	// Chargement des diff�rentes �tapes du jeu
	private void loadState(int state) {
		if (state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if (state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
	}

	// Remise � z�ro du jeu
	private void unloadState(int state) {
		gameStates[state] = null;
	}

	// Setter pour changer l'�tat du jeu
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);

	}

	// Mise a jour de l'�tat
	public void update() {
		if (gameStates[currentState] != null)
			gameStates[currentState].update();
	}

	// Affichage �tat
	public void draw(java.awt.Graphics2D g) {
		if (gameStates[currentState] != null)
			gameStates[currentState].draw(g);
	}

	// Gestion des evenements clavier
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}

	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
}
