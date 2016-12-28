package state.states;

import components.MineField;
import components.TopBar;
import javafx.scene.transform.Affine;
import state.State;
import state.StateManager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class PlayState extends State
{
	private MineField mineField;
	private TopBar topBar;

	private int gameStatus;

	public PlayState(StateManager stateManager)
	{
		this.stateManager = stateManager;
		init();

		gameStatus = 0;
	}

	public void init()
	{
		mineField = new MineField(9, 9, 10);
		mineField.setCoordinates(0, 26);

		topBar = new TopBar();
	}

	public void update()
	{
		mineField.update();
		topBar.update();

		gameStatus = mineField.getGameStatus();
	}

	public void draw(Graphics2D g2d)
	{
		mineField.draw(g2d);
		topBar.draw(g2d);
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
