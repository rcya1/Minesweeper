package state.states;

import main.Game;
import main.GamePanel;
import state.State;
import state.StateManager;
import utility.Settings;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class OptionState extends State
{
	private int[] values;

	private int minValue;
	private int maxValue;

	private String[] options;
	private int selection;

	public OptionState(StateManager stateManager)
	{
		this.stateManager = stateManager;
	}

	public void init()
	{
		values = new int[] {Settings.COLUMNS, Settings.ROWS, Settings.NUMBER_OF_MINES};

		options = new String[]{"Number of Columns", "Number of Rows", "Number of Mines", "Confirm Changes"};
		selection = 0;

		//Calculate min and max values based on the selection.
		//Columns/Rows are always between 5 and 99;
		//Mines - Default - 1/8th of the area of field
	}

	public void update()
	{
		//Calculate mine default value if the mine value was not changed from the start
		//Set min and max values
		if(selection == 0 || selection == 1)
		{
			minValue = 1;
			maxValue = 100;
		}
		else if(selection == 2)
		{
			minValue = 1;
			maxValue = (values[0] * values[1]) - 1;

			if(values[2] < minValue) values[2] = minValue;
			if(values[2] > maxValue) values[2] = maxValue;
		}
	}

	public void draw(Graphics2D g2d)
	{
		g2d.setColor(Color.GRAY);
		g2d.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		for(int i = 0; i < options.length; i++)
		{
			g2d.setColor(Color.WHITE);
			if(i == selection) g2d.setColor(Color.RED);
			if(i != options.length - 1) g2d.drawString(options[i], 8, 15 + 15 * i);
			else g2d.drawString(options[i], 8, 15 + 20 * i);
		}

		if(selection != (options.length - 1))
		{
			g2d.setStroke(new BasicStroke(2));
			g2d.drawLine(10, 100, 110, 100);
			int xValue = (int) ((double) (values[selection] - minValue) / (maxValue - minValue) * 100);
			g2d.fillRect(5 + xValue - 1, 90, 5, 20);

			int stringWidth = g2d.getFontMetrics().stringWidth(Integer.toString(values[selection]));
			g2d.drawString(Integer.toString(values[selection]), (GamePanel.WIDTH + stringWidth) / 2, 150);
		}
	}

	public void keyPressed(int key)
	{
		switch(key)
		{
		case KeyEvent.VK_UP:
			if(selection > 0) selection--;

			if(values[2] == Settings.NUMBER_OF_MINES)
			{
				values[2] = (values[0] * values[1]) / 8;
			}
			break;
		case KeyEvent.VK_DOWN:
			if(selection < options.length - 1) selection++;

			if(values[2] == Settings.NUMBER_OF_MINES)
			{
				values[2] = (values[0] * values[1]) / 8;
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(selection != (options.length - 1)) values[selection]++;
			if(values[selection] > maxValue) values[selection] = maxValue;
			break;
		case KeyEvent.VK_LEFT:
			if(selection != (options.length - 1)) values[selection]--;
			if(values[selection] < minValue) values[selection] = minValue;
			break;
		case KeyEvent.VK_ENTER:
			if(selection == options.length - 1)
			{
				stateManager.setState(StateManager.MENU_STATE);
				Settings.COLUMNS = values[0];
				Settings.ROWS = values[1];
				Settings.NUMBER_OF_MINES = values[2];
				Game.createNewWindow(values[0] * 16, values[1]* 16 + 26);
			}
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
