package components;

import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MineField
{
	private int rows;
	private int columns;

	private int tileWidth;
	private int tileHeight;

	private int numOfMines;

	private int[][] locations; //Locations of all mines (0-CLEAR, -1-MINE, NUMBERS-NUMBERS)
	private int[][] board; //All pieces that have been selected (0-NOT, 1-SELECTED, 2-FLAG, 3-?, 4-HELD)

	private int faceExpression; //0-Neutral, 1-:O, 2-WIN, 3-LOSS

	public MineField(int columns, int rows, int numOfMines)
	{
		createNewBoard(columns, rows, numOfMines);

		tileWidth = 16;
		tileHeight = 16;
	}

	public void createNewBoard(int columns, int rows, int numOfMines)
	{
		this.columns = columns;
		this.rows = rows;

		locations = new int[columns][rows];
		board = new int[columns][rows];

		this.numOfMines = numOfMines;

		init();
	}

	private void init()
	{
		for(int i = 0; i < numOfMines; i++) addRandomMine();

		for(int i = 0; i < columns; i++)
		{
			for(int j = 0; j < rows; j++)
			{
				System.out.print(locations[i][j] + " ");
			}
			System.out.print("\n");
		}
	}

	private void addRandomMine()
	{
		//Normally, we add one to the column and row, but because they are indices, we don't
		int column = (int) (Math.random() * columns);
		int row = (int) (Math.random() * rows);

		if(locations[column][row] != -1)
		{
			locations[column][row] = -1;

			boolean leftAvailable = column - 1 >= 0;
			boolean rightAvailable = column + 1 < columns;
			boolean topAvailable = row - 1 >= 0;
			boolean bottomAvailable = row + 1 < rows;

			if(leftAvailable)
			{
				if(locations[column - 1][row] != -1) locations[column - 1][row]++;
				if(topAvailable)
				{
					if(locations[column - 1][row - 1] != -1) locations[column - 1][row - 1]++;
				}
				if(bottomAvailable)
				{
					if(locations[column - 1][row + 1] != -1) locations[column - 1][row + 1]++;
				}
			}
			if(rightAvailable)
			{
				if(locations[column + 1][row] != -1) locations[column + 1][row]++;
				if(topAvailable)
				{
					if(locations[column + 1][row - 1] != -1) locations[column + 1][row - 1]++;
				}
				if(bottomAvailable)
				{
					if(locations[column + 1][row + 1] != -1) locations[column + 1][row + 1]++;
				}
			}
			if(topAvailable)
			{
				if(locations[column][row - 1] != -1) locations[column][row - 1]++;
			}
			if(bottomAvailable)
			{
				if(locations[column][row + 1] != -1) locations[column][row + 1]++;
			}
		}
		else addRandomMine();
	}

	public void update()
	{
		//Update timer
	}

	public void draw(Graphics2D g2d)
	{
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		g2d.setColor(Color.GRAY);
		for(int column = 0; column < columns; column++)
		{
			for(int row = 0; row < rows; row++)
			{
				switch(board[column][row])
				{
				case 0:
					g2d.setColor(Color.GRAY);
					g2d.fillRect(column * tileWidth, row * tileHeight, tileWidth, tileHeight);
					break;
				case 1:
					switch(locations[column][row])
					{
					case 0:
						g2d.setColor(Color.DARK_GRAY);
						break;
					case -1:
						g2d.setColor(Color.ORANGE);
						break;
					case 1:
						g2d.setColor(Color.BLUE);
						break;
					case 2:
						g2d.setColor(Color.GREEN);
						break;
					case 3:
						g2d.setColor(Color.CYAN);
						break;
					case 4:
						g2d.setColor(Color.WHITE);
						break;
					case 5:
						break;
					case 6:
						break;
					case 7:
						break;
					case 8:
						break;
					}

					g2d.fillRect(column * tileWidth, row * tileHeight, tileWidth, tileHeight);
					break;
				case 2:
					g2d.setColor(Color.RED);
					g2d.fillRect(column * tileWidth, row * tileHeight, tileWidth, tileHeight);
					break;
				case 3:
					break;
				case 4:
					g2d.setColor(Color.BLACK);
					g2d.fillRect(column * tileWidth, row * tileHeight, tileWidth, tileHeight);
					break;
				}
			}
		}
	}

	public void keyPressed(int e)
	{

	}

	public void keyReleased(int e)
	{

	}

	public void mousePressed(MouseEvent e)
	{
		int column = e.getX() / (tileWidth * GamePanel.SCALE);
		int row = e.getY() / (tileHeight * GamePanel.SCALE);

		switch(board[column][row])
		{
		case 0:
			if(SwingUtilities.isLeftMouseButton(e)) board[column][row] = 4;
			else if(SwingUtilities.isRightMouseButton(e))
			{
				board[column][row] = 2;
			}
			break;
		case 2:
			if(SwingUtilities.isRightMouseButton(e))
			{
				board[column][row] = 0;
			}
			break;
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		for(int column = 0; column < columns; column++)
		{
			for(int row = 0; row < rows; row++)
			{
				if(board[column][row] == 4 && SwingUtilities.isLeftMouseButton(e))
				{
					board[column][row] = 1;
				}
			}
		}
	}
}
