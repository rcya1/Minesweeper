package state;

import state.states.MenuState;
import state.states.PlayState;

import java.awt.*;
import java.awt.event.MouseEvent;

public class StateManager
{
	private static final int NUMBER_OF_STATES = 3;

	private static final int MENU_STATE = 0;
	private static final int PLAY_STATE = 1;

	private State[] states;
	private int currentState;

	public StateManager()
	{
		states = new State[NUMBER_OF_STATES];
		currentState = PLAY_STATE;
		loadState(currentState);
	}

	public void draw(Graphics2D g2d)
	{
		if(states[currentState] != null) states[currentState].draw(g2d);
	}

	public void update()
	{
		if(states[currentState] != null) states[currentState].update();
	}

	private void loadState(int state)
	{
		if(state == MENU_STATE) states[state] = new MenuState(this);
		if(state == PLAY_STATE) states[state] = new PlayState(this);
	}

	public void setState(int state)
	{
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		states[currentState].init();
	}

	private void unloadState(int state)
	{
		states[state] = null;
	}

	public void keyPressed(int key)
	{
		states[currentState].keyPressed(key);
	}

	public void keyReleased(int key)
	{
		states[currentState].keyReleased(key);
	}

	public void mousePressed(MouseEvent e)
	{
		states[currentState].mousePressed(e);
	}

	public void mouseReleased(MouseEvent e)
	{
		states[currentState].mouseReleased(e);
	}
}
