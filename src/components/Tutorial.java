package components;

import main.GamePanel;
import utility.Images;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tutorial
{
	private int slide;
	private int tick;
	private int stringIndex;

	private int cursorX;
	private int cursorY;

	private String[][] text;

	private MineField mineField;

	public Tutorial()
	{
		slide = 0;
		tick = 0;

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

				},
				{

				},
				{

				},
				{

				},
				{

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

		//CIRCLE SWAG
//		cursorX = (int) (50 + 20 * Math.cos(tick));
//		cursorY = (int) (50 + 20 * Math.sin(tick));

		tick++;
	}

	public void draw(Graphics2D g2d)
	{
		String drawString;

		switch(slide)
		{
		case 0:
			if(mineField != null) mineField.draw(g2d);
			drawString = text[slide][stringIndex];

			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			drawCenteredSplitString(g2d, drawString, GamePanel.HEIGHT - 30);
			break;
		}

		g2d.drawImage(Images.TopBar.Faces.WIN, cursorX, cursorY, null);
	}

	//Draws a string centered
	private void drawCenteredSplitString(Graphics2D g2d, String string, int y)
	{
		for(String line : string.split("\n"))
		{
			int stringWidth = (int) g2d.getFontMetrics().getStringBounds(line, g2d).getWidth();
			g2d.drawString(line, (GamePanel.WIDTH - stringWidth) / 2,
					y += g2d.getFontMetrics().getHeight());
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
				if(slide >= text.length) slide = text.length - 1;
			}

			break;
		}
	}
}
