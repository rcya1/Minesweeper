package state.states;

import main.GamePanel;
import state.State;
import state.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuState extends State
{
	private String[] options;
	private int selected;

	public MenuState(StateManager stateManager)
	{
		this.stateManager = stateManager;
		init();
	}

	public void init()
	{
		options = new String[] {"Play", "Setup Game", "Tutorial", "Exit"};
		selected = 0;
	}

	public void update()
	{

	}

	public void draw(Graphics2D g2d)
	{
		g2d.setColor(Color.GRAY);
		g2d.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		for(int i = 0; i < options.length; i++)
		{
			g2d.setColor(Color.WHITE);
			if(i == selected) g2d.setColor(Color.RED);
			g2d.drawString(options[i], 8, 15 + 13 * i);
		}
	}

	public void keyPressed(int key)
	{
		switch(key)
		{
		case KeyEvent.VK_UP:
			if(selected > 0) selected--;
			break;
		case KeyEvent.VK_DOWN:
			if(selected < options.length - 1) selected++;
			break;
		case KeyEvent.VK_ENTER:
			switch(selected)
			{
			case 0: //Play
				stateManager.setState(StateManager.PLAY_STATE);
				break;
			case 1: //Options
				//Game.createNewWindow(300, 300);
				break;
			case 2: //Help
				break;
			case 3: //Exit
				System.exit(0);
				break;
			}
			break;
		}
	}

	public void keyReleased(int key)
	{

	}

	public void mousePressed(MouseEvent e)
	{

	}

	public void mouseReleased(MouseEvent e)
	{

	}
}
