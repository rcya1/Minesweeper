package components;

import main.GamePanel;
import utility.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MineField
{
	private int x;
	private int y;

	private int rows;
	private int columns;

	private int tileWidth;
	private int tileHeight;

	private int numOfMines;

	private int[][] locations; //Locations of all mines (0-CLEAR, -1-MINE_TRIGGERED, -2-MINE_UNTRIGGERED, NUMBERS-NUMBERS)
	private int[][] board; //All pieces that have been selected (0-NOT, 1-SELECTED, 2-FLAG, 3-?, 4-HELD, 5-FALSE)

	private int faceExpression; //0-Neutral, 1-:O, 2-WIN, 3-LOSS

	private boolean multiClickFlag; //When the right+left click is used, this is used to ensure the spaces are fulfilled
	private int[] multiClickCoordinates;

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

		faceExpression = 0;

		for(int i = 0; i < numOfMines; i++) addRandomMine();
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
		boolean winFlag = true;

		//Check if win
		for(int column = 0; column < columns; column++)
		{
			for(int row = 0; row < rows; row++)
			{
				if(board[column][row] != 1)
				{
					if(locations[column][row] != -1) winFlag = false;
				}
			}
		}

		if(winFlag)
		{
			faceExpression = 2;
			//WIN CODE
		}

		if(!winFlag)
		{
			//Update timer
		}
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
				BufferedImage drawImage = null;

				switch(board[column][row])
				{
				case 0:
					drawImage = Images.Tiles.EMPTY;
					break;
				case 1:
					switch(locations[column][row])
					{
					case 0:
						drawImage = Images.Tiles.PRESSED;
						break;
					case -1:
						drawImage = Images.Tiles.MINE_TRIGGERED;
						break;
					case -2:
						drawImage = Images.Tiles.MINE_UNTRIGGERED;
						break;
					case 1:
						drawImage = Images.Tiles.Numbers.ONE;
						break;
					case 2:
						drawImage = Images.Tiles.Numbers.TWO;
						break;
					case 3:
						drawImage = Images.Tiles.Numbers.THREE;
						break;
					case 4:
						drawImage = Images.Tiles.Numbers.FOUR;
						break;
					case 5:
						drawImage = Images.Tiles.Numbers.FIVE;
						break;
					case 6:
						drawImage = Images.Tiles.Numbers.SIX;
						break;
					case 7:
						drawImage = Images.Tiles.Numbers.SEVEN;
						break;
					case 8:
						drawImage = Images.Tiles.Numbers.EIGHT;
						break;
					}
					break;
				case 2:
					drawImage = Images.Tiles.FLAG;
					break;
				case 3:
					drawImage = Images.Tiles.QUESTION;
					break;
				case 4:
					drawImage = Images.Tiles.PRESSED;
					break;
				case 5:
					drawImage = Images.Tiles.MINE_FALSE;
					break;
				}

				g2d.drawImage(drawImage, column * tileWidth, row * tileHeight, tileWidth, tileHeight, null);
			}
		}
	}

	public void keyPressed(int e)
	{
		if(e == KeyEvent.VK_R)
		{
			createNewBoard(9, 9, 10);
		}
	}

	public void keyReleased(int e)
	{

	}

	public void mousePressed(MouseEvent e)
	{
		if(faceExpression != 2 && faceExpression != 3)
		{
			int column = e.getX() / (tileWidth * GamePanel.SCALE);
			int row = e.getY() / (tileHeight * GamePanel.SCALE);

			switch(board[column][row])
			{
			case 0:
				if(SwingUtilities.isLeftMouseButton(e))
				{
					faceExpression = 1;
					board[column][row] = 4;
				}
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

			//When a number tile has all mines around it marked, left+right clicking clears all tiles around it
			if(board[column][row] >= 1)
			{
				if(SwingUtilities.isLeftMouseButton(e) && SwingUtilities.isRightMouseButton(e))
				{
					boolean leftAvailable = column - 1 >= 0;
					boolean rightAvailable = column + 1 < columns;
					boolean topAvailable = row - 1 >= 0;
					boolean bottomAvailable = row + 1 < rows;

					if(leftAvailable)
					{
						if(board[column - 1][row]== 0) board[column - 1][row] = 4;
						if(topAvailable)
						{
							if(board[column - 1][row - 1]== 0) board[column - 1][row - 1] = 4;
						}
						if(bottomAvailable)
						{
							if(board[column - 1][row + 1]== 0) board[column - 1][row + 1] = 4;
						}
					}
					if(rightAvailable)
					{
						if(board[column + 1][row]== 0) board[column + 1][row] = 4;
						if(topAvailable)
						{
							if(board[column + 1][row - 1]== 0) board[column + 1][row - 1] = 4;
						}
						if(bottomAvailable)
						{
							if(board[column + 1][row + 1]== 0) board[column + 1][row + 1] = 4;
						}
					}
					if(topAvailable)
					{
						if(board[column][row - 1]== 0) board[column][row - 1] = 4;
					}
					if(bottomAvailable)
					{
						if(board[column][row + 1]== 0) board[column][row + 1] = 4;
					}

					multiClickFlag = true;
					multiClickCoordinates = new int[] {column, row};
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		int multiClickMineCounter = 0;

		for(int column = 0; column < columns; column++)
		{
			for(int row = 0; row < rows; row++)
			{
				if(!multiClickFlag)
				{
					if(board[column][row] == 4 && SwingUtilities.isLeftMouseButton(e))
					{
						if(faceExpression == 1) faceExpression = 0;
						click(column, row);
					}
				}
				else
				{
					int multiClickColumn = multiClickCoordinates[0];
					int multiClickRow = multiClickCoordinates[1];

					if(column == multiClickColumn || column == multiClickColumn - 1 || column == multiClickColumn + 1)
					{
						if(row == multiClickRow || row == multiClickRow - 1 || row == multiClickRow + 1)
						{
							if(board[column][row] == 2) multiClickMineCounter++;
						}
					}
					if(multiClickMineCounter == locations[multiClickCoordinates[0]][multiClickCoordinates[1]])
					{
						clickAround(multiClickCoordinates[0], multiClickCoordinates[1]);
					}

					if(board[column][row] == 4) board[column][row] = 0;
				}
			}
		}

		multiClickFlag = false;
	}

	private void click(int column, int row)
	{
		if(board[column][row] != 1 && board[column][row] != 2)
		{
			board[column][row] = 1;

			//If tile is empty, click all adjacent tiles
			if(locations[column][row] == 0)
			{
				clickAround(column, row);
			}
			//If mine is clicked, click all other mines
			else if(locations[column][row] == -1)
			{
				faceExpression = 3;

				for(int columnI = 0; columnI < columns; columnI++)
				{
					for(int rowI = 0; rowI < rows; rowI++)
					{
						if(locations[columnI][rowI] == -1 && board[columnI][rowI] != 2 &&
								//If at least one of the coordinates does not match,
								//then the mine is not the one clicked on
								(column != columnI || row != rowI))
						{
							//Set the other mines to the nontriggered state and selected
							//The mine that remains -1 will be highlighted red
							locations[columnI][rowI] = -2;
							board[columnI][rowI] = 1;
						}
					}
				}

				for(int columnI = 0; columnI < columns; columnI++)
				{
					for(int rowI = 0; rowI < rows; rowI++)
					{
						if(board[columnI][rowI] == 2 && locations[columnI][rowI] != -1)
						{
							board[columnI][rowI] = 5;
						}
					}
				}

				//LOSE CODE
			}
		}
	}

	private void clickAround(int column, int row)
	{
		if(column - 1 >= 0)
		{
			click(column - 1, row);
			if(row - 1 >= 0) click(column - 1, row - 1);
			if(row + 1 < rows) click(column - 1, row + 1);
		}
		if(column + 1 < columns)
		{
			click(column + 1, row);
			if(row - 1 >= 0) click(column + 1, row - 1);
			if(row + 1 < rows) click(column + 1, row + 1);
		}
		if(row - 1 >= 0) click(column, row - 1);
		if(row + 1 < rows) click(column, row + 1);
	}
}
