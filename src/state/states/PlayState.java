package state.states;

import components.MineField;
import state.State;
import state.StateManager;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PlayState extends State
{
	MineField mineField;

	public PlayState(StateManager stateManager)
	{
		this.stateManager = stateManager;
		init();
	}

	public void init()
	{
		mineField = new MineField(9, 9, 10);
	}

	public void update()
	{
		mineField.update();
	}

	public void draw(Graphics2D g2d)
	{
		mineField.draw(g2d);
	}

	public void keyPressed(int key)
	{
		mineField.keyPressed(key);
	}

	public void keyReleased(int key)
	{
		mineField.keyReleased(key);
	}

	public void mousePressed(MouseEvent e)
	{
		mineField.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e)
	{
		mineField.mouseReleased(e);
	}
}
