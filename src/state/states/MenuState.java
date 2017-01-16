package state.states;

import main.GamePanel;
import state.State;
import state.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuState extends State
{
	//Idea - Have an icon on the side that slides when changing the options

	private String[] options;
	private int selection;

	public MenuState(StateManager stateManager)
	{
		this.stateManager = stateManager;
		init();
	}

	public void init()
	{
		options = new String[] {"Play", "Setup Game", "Tutorial", "Exit"};
		selection = 0;
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
			if(i == selection) g2d.setColor(Color.RED);
			g2d.drawString(options[i], 8, 15 + 13 * i);
		}
	}

	public void keyPressed(int key)
	{
		switch(key)
		{
		case KeyEvent.VK_UP:
			if(selection > 0) selection--;
			break;
		case KeyEvent.VK_DOWN:
			if(selection < options.length - 1) selection++;
			break;
		case KeyEvent.VK_ENTER:
			switch(selection)
			{
			case 0: //Play
				stateManager.setState(StateManager.PLAY_STATE);
				//Check if the values are changed, and if they are, create a new window.
				break;
			case 1: //Options
				stateManager.setState(StateManager.OPTION_STATE);
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
	public void mouseDragged(MouseEvent e)
	{

	}
}
