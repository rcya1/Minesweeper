package state.states;

import components.Tutorial;
import state.State;
import state.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TutorialState extends State
{
	private StateManager stateManager;

	private Tutorial tutorial;

	public TutorialState(StateManager stateManager)
	{
		this.stateManager = stateManager;
		init();
	}

	public void init()
	{
		tutorial = new Tutorial();
	}

	public void update()
	{
		tutorial.update();
	}

	public void draw(Graphics2D g2d)
	{
		tutorial.draw(g2d);
	}

	public void keyPressed(int key)
	{
		tutorial.keyPressed(key);

		if(key == KeyEvent.VK_ESCAPE) stateManager.setState(StateManager.MENU_STATE);
	}

	public void keyReleased(int key)
	{

	}

	public void mouseMoved(MouseEvent e)
	{

	}

	public void mousePressed(MouseEvent e)
	{

	}

	public void mouseReleased(MouseEvent e)
	{

	}

	public void mouseDragged(MouseEvent e)
	{

	}
}
