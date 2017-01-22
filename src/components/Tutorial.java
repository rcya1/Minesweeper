package components;

import main.GamePanel;
import state.StateManager;
import utility.Images;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tutorial
{
	private StateManager stateManager;

	private int slide;
	private int tick;
	private int stringIndex;
	private int subStringIndex;

	private int cursorX;
	private int cursorY;

	private String[][] text;

	private MineField mineField;

	public Tutorial(StateManager stateManager)
	{
		this.stateManager = stateManager;

		slide = 0;
		tick = 0;
		subStringIndex = 0;

		cursorX = 0;
		cursorY = 0;

		text = new String[][] {
				{
					"Welcome to the\nMinesweeper Tutorial!",
						"Here you will learn\nthe basics of Minesweeper.",
						"To exit this tutorial,\npress escape at any moment.",
						"Let's Begin!"
				},
				{
					"The point of Minesweeper is\nto click all of the squares.",
						"As you can see, all of the\nsquares were cleared."
				},
				{
					"However, winning is\nnot that easy.",
						"There are mines randomly\nplaced on the field.",
						"A mine was clicked,\nand the game ended."
				},
				{
					"To find mines, use\nthe numbers as clues!",
						"The number 3 means there\nare 3 mines around this tile.",
						"You can mark mines,\nso you don't click on them."
				},
				{
					"Using logic, you can\nbeat Minesweeper!",
				}};
	}

	public void update()
	{
		switch(slide)
		{
		case 0:
			if(mineField == null) mineField = new MineField(9, 9, 10);
			mineField.update();
			break;
		}

//		CIRCLE SWAG
		cursorX = (int) (50 + -20 * Math.cos((double) tick / 8));
		cursorY = (int) (50 + 20 * Math.sin((double) tick / 8));

		tick++;
	}

	public void draw(Graphics2D g2d)
	{
		String drawString;

		switch(slide)
		{
		case 0:
			if(mineField != null) mineField.draw(g2d);
			break;
		case 1:
			if(mineField != null) mineField.draw(g2d);
			break;
		case 2:
			if(mineField != null) mineField.draw(g2d);
			break;
		case 3:
			if(mineField != null) mineField.draw(g2d);
			break;
		case 4:
			if(mineField != null) mineField.draw(g2d);
			break;
		}

		drawString = text[slide][stringIndex].substring(0, subStringIndex);

		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Default", Font.PLAIN, 10));
		drawCenteredSplitString(g2d, drawString, GamePanel.HEIGHT - 30);

		if(tick % 2 == 0) subStringIndex++;
		if(subStringIndex >= text[slide][stringIndex].length())
			subStringIndex = text[slide][stringIndex].length();

		g2d.drawImage(Images.CURSOR, cursorX, cursorY, null);
	}

	//Draws a string centered
	private void drawCenteredSplitString(Graphics2D g2d, String string, int y)
	{
		for(String line : string.split("\n"))
		{
			int stringWidth = (int) g2d.getFontMetrics().getStringBounds(line, g2d).getWidth();
			g2d.drawString(line, (GamePanel.WIDTH - stringWidth) / 2,
					5 + (y += (g2d.getFontMetrics().getHeight() - 4)));
		}
	}

	public void keyPressed(int key)
	{
		switch(key)
		{
		case KeyEvent.VK_SPACE:
			stringIndex++;
			if(stringIndex >= text[slide].length)
			{
				stringIndex = 0;
				slide++;
				if(slide >= text.length)
				{
					stateManager.setState(StateManager.MENU_STATE);
				}
			}
			subStringIndex = 0;

			break;
		}
	}
}
