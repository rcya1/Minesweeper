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

		cursorX = 2;
		cursorY = 4;

		text = new String[][] {
				{
					"Welcome to the\nMinesweeper Tutorial!",
						"Here you will learn\nthe basics of Minesweeper.",
						"To exit this tutorial,\npress escape at any moment.",
						"Let's Begin!"
				},
				{
					"The point of Minesweeper is\nto clear all of the squares.",
						"As you can see, when\nyou click on a square,",
						"the square displays\nsome information.",
						"To win, clear all\nof the squares!"
				},
				{
					"However, winning is\nnot that easy.",
						"There are mines randomly\nplaced on the field.",
						"A mine was clicked,\nand the game ended."
				},
				{
					"To find mines, use\nthe numbers as clues!",
						"The number 2 means there\nare 2 mines around this tile.",
						"Therefore, these\nhave to be mines.",
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
			break;
		case 1:
			if(mineField == null) mineField = new MineField(9, 9, 0);
			if(stringIndex <= 2) moveCursorTo(22, 64, 8);
			else moveCursorTo(38, 64, 8);

			mineField.setValue(1, 4, -1);
			mineField.setValue(2, 4, -1);
			mineField.refreshNumbers();

			if(stringIndex == 1) mineField.click(1, 3);
			if(stringIndex == 3) mineField.click(2, 3);

			break;
		case 2:
			if(mineField == null) mineField = new MineField(9, 9, 10);
			mineField.setValue(1, 3, 1);
			mineField.setValue(4, 5, -1);

			mineField.click(1, 3);

			moveCursorTo(72, 96, 8);
			if(stringIndex == 1) mineField.click(4, 5);
			break;
		case 3:
			if(mineField == null) mineField = new MineField(9, 9, 10);
			mineField.setValue(1, 1, 2);
			mineField.setValue(0, 0, -1);
			mineField.setValue(1, 0, -1);
			mineField.setValue(2, 0, 1);
			mineField.setValue(0, 1, 2);
			mineField.setValue(2, 1, 1);
			mineField.setValue(0, 2, 1);
			mineField.setValue(1, 2, 1);
			mineField.setValue(2, 2, 1);

			mineField.click(1, 1);
			mineField.click(0, 1);
			mineField.click(2, 1);
			mineField.click(0, 2);
			mineField.click(1, 2);
			mineField.click(2, 2);
			mineField.click(2, 0);

			if(stringIndex == 1) moveCursorTo(12, 16, 4);
			if(stringIndex == 2)
			{
				moveCursorTo(0, 4, 4);
				mineField.setFlag(0, 0);
			}
			if(stringIndex == 3)
			{
				moveCursorTo(20, 4, 4);
				mineField.setFlag(1, 0);
			}
			break;
		case 4:
			if(mineField == null) mineField = new MineField(9, 9, 10);
			break;
		case 5:
			if(mineField == null) mineField = new MineField(9, 9, 10);
			break;
		}

//		CIRCLE SWAG
//		cursorX = (int) (50 + -20 * Math.cos((double) tick / 8));
//		cursorY = (int) (50 + 20 * Math.sin((double) tick / 8));

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
		case 5:
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

				mineField = null;
				if(slide >= text.length)
				{
					stateManager.setState(StateManager.MENU_STATE);
				}
			}
			subStringIndex = 0;
			break;
		}
	}

	private void moveCursorTo(int x, int y, int ticks)
	{
//		if(Math.abs(x - cursorX) < 5 || Math.abs(y - cursorY) < 5)
//		{
//			cursorX = x;
//			cursorY = y;
//		}

		if(cursorX != x && cursorY != y)
		{
			cursorX += (x - cursorX) / ticks;
			cursorY += (y - cursorY) / ticks;
		}
	}
}
