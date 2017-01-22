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

	private Color sliderColor;
	private boolean sliderMouseHover;

	private int optionHoverIndex;

	public OptionState(StateManager stateManager)
	{
		this.stateManager = stateManager;
	}

	public void init()
	{
		values = new int[] {Settings.COLUMNS, Settings.ROWS, Settings.NUMBER_OF_MINES};

		options = new String[]{"Number of Columns", "Number of Rows", "Number of Mines", "Confirm Changes"};
		selection = 0;

		sliderColor = Color.WHITE;
		sliderMouseHover = false;

		optionHoverIndex = -1;
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

		if(sliderColor != Color.DARK_GRAY)
		{
			if(sliderMouseHover)
			{
				sliderColor = new Color(200, 200, 200);
			}
			else sliderColor = Color.WHITE;
		}
	}

	public void draw(Graphics2D g2d)
	{
		g2d.setColor(Color.GRAY);
		g2d.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		for(int i = 0; i < options.length; i++)
		{
			g2d.setColor(Color.WHITE);
			if(i == selection || i == optionHoverIndex) g2d.setColor(Color.DARK_GRAY);
			if(i != options.length - 1) g2d.drawString(options[i], 8, 15 + 15 * i);
			else g2d.drawString(options[i], 8, 15 + 20 * i);
		}

		if(selection != (options.length - 1))
		{
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Default", Font.PLAIN, 12));

			g2d.setStroke(new BasicStroke(2));
			g2d.drawLine(10, 100, 110, 100);

			g2d.setColor(sliderColor);
			int xValue = (int) ((double) (values[selection] - minValue) / (maxValue - minValue) * 100);
			g2d.fillRect(5 + xValue - 1, 90, 5, 20);

			int stringWidth = (int) g2d.getFontMetrics().getStringBounds(Integer.toString(values[selection]), g2d).getWidth();
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
				saveChanges();
			}
		}
	}

	public void keyReleased(int key)
	{

	}

	public void mouseMoved(MouseEvent e)
	{
		Point mouse = new Point((int) e.getPoint().getX() / GamePanel.SCALE,
				(int) e.getPoint().getY() / GamePanel.SCALE);

		if(selection != options.length - 1)
		{
			int xValue = (int) ((double) (values[selection] - minValue) / (maxValue - minValue) * 100);
			Rectangle sliderRectangle = new Rectangle(5 + xValue - 1, 90, 5, 20);

			sliderMouseHover = sliderRectangle.contains(mouse);
		}

		for(int i = 0; i < options.length; i++)
		{
			Rectangle optionRectangle;
			if(i == options.length - 1)
			{
				optionRectangle = new Rectangle(8, 15 + 15 * i, 100, 20);
			}
			else
			{
				optionRectangle = new Rectangle(8, 15 * i, 100, 20);
			}

			if(optionRectangle.contains(mouse))
			{
				optionHoverIndex = i;
				break;
			}
			optionHoverIndex = -1;
		}
	}

	public void mousePressed(MouseEvent e)
	{
		if(sliderMouseHover)
		{
			sliderColor = Color.DARK_GRAY;
		}
		if(optionHoverIndex != -1)
		{
			saveChanges();
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		sliderColor = Color.WHITE;
	}

	public void mouseDragged(MouseEvent e)
	{
		if(sliderMouseHover)
		{
			sliderColor = Color.DARK_GRAY;
			values[selection] = (e.getX() / GamePanel.SCALE) - minValue;
			if(values[selection] < minValue) values[selection] = minValue;
			if(values[selection] > maxValue) values[selection] = maxValue;
		}
	}

	private void saveChanges()
	{
		selection = optionHoverIndex;

		if(selection == options.length - 1)
		{
			stateManager.setState(StateManager.MENU_STATE);
			Settings.COLUMNS = values[0];
			Settings.ROWS = values[1];
			Settings.NUMBER_OF_MINES = values[2];

			int gameWidth;
			int gameHeight;

			if(values[0] < 9) gameWidth = 9 * 16;
			else if(values[0] * 16 <= Settings.WIDTH_OF_SCREEN / GamePanel.SCALE)
				gameWidth = values[0] * 16;
			else
				gameWidth = Math.round(Settings.WIDTH_OF_SCREEN / GamePanel.SCALE);

			if(values[1] < 9) gameHeight = 9 * 16;
			else if(values[1] * 16 + 26 <= Settings.HEIGHT_OF_SCREEN / GamePanel.SCALE)
				gameHeight = values[1] * 16 + 26;
			else
				gameHeight = Math.round(Settings.HEIGHT_OF_SCREEN / GamePanel.SCALE);

			Game.createNewWindow(gameWidth, gameHeight);
		}
	}
}
