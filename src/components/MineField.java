package components;

import main.GamePanel;
import utility.Images;
import utility.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class MineField //TODO Force first pick to be a blank space
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

	private int gameStatus; //-1-Has not clicked a square, 0-Neutral, 1-:O, 2-WIN, 3-LOSS

	private boolean multiClickFlag; //Activated when right+left is used
	private int[] multiClickCoordinates; //Row and Column of tile that is multiclicked

	private int clicks;

	private boolean cameraModeX;
	private boolean cameraModeY;

	private int cameraX;
	private int cameraY;

	private int tempCameraX;
	private int tempCameraY;

	private int mouseDragOriginX;
	private int mouseDragOriginY;

	public MineField()
	{
		tileWidth = 16;
		tileHeight = 16;

		x = 0;
		y = 0;

		reset();
	}

	void reset()
	{
		tileWidth = 16;
		tileHeight = 16;

		this.columns = Settings.COLUMNS;
		this.rows = Settings.ROWS;

		locations = new int[columns][rows];
		board = new int[columns][rows];

		this.numOfMines = Settings.NUMBER_OF_MINES;

		clicks = 0;

		gameStatus = -1;

		cameraModeX = columns * tileWidth > GamePanel.WIDTH;
		cameraModeY = rows * tileHeight > GamePanel.HEIGHT;

		cameraX = 0;
		cameraY = 0;

		tempCameraX = 0;
		tempCameraY = 0;
	}

	void createNewBoard(int zeroColumn, int zeroRow)
	{
		reset();
		for(int i = 0; i < numOfMines; i++) addRandomMine(zeroColumn, zeroRow);
	}

	public void update()
	{
		boolean winFlag = true;

		//Check if win conditions are met
		for(int column = 0; column < columns; column++)
		{
			for(int row = 0; row < rows; row++)
			{
				//Check if the tile is open
				if(board[column][row] != 1)
				{
					//If it is, and it is not a mine, the game has not been won yet
					if(locations[column][row] != -1) winFlag = false;
				}
			}
		}

		//If the game has been won
		if(winFlag)
		{
			gameStatus = 2;
			numOfMines = 0;

			//Loop through all tiles, if a tile is amine and not flagged, set it to a flag
			for(int columnI = 0; columnI < columns; columnI++)
			{
				for(int rowI = 0; rowI < rows; rowI++)
				{
					if(locations[columnI][rowI] == -1 && board[columnI][rowI] != 2)
					{
						board[columnI][rowI] = 2;
					}
				}
			}
		}
	}

	public void mousePressed(MouseEvent e)
	{
		//If the game has not been won or lost yet
		if(gameStatus != 2 && gameStatus != 3)
		{
			//Ensure the mouse coordinates are within the bounds of the game
			if(e.getX() >= this.x * GamePanel.SCALE &&
					e.getX() <= (this.x + this.tileWidth * columns) * GamePanel.SCALE &&
					e.getY() >= this.y * GamePanel.SCALE &&
					e.getY() <= (this.y + this.tileHeight * rows) * GamePanel.SCALE)
			{
				//Start the game/timer
				if(gameStatus == -1)
				{
					if(SwingUtilities.isRightMouseButton(e) || SwingUtilities.isLeftMouseButton(e))
						gameStatus = 0;
				}

				//Get the mouseX, subtract the offset, and divide by the tile dimensions to get
				//the row/column of where an object is
				//tileWidth is multiplied by scale, since getX is in unscaled pixels, while the entire thing is
				//scaled up
				int column = (e.getX() - ((x - cameraX) * GamePanel.SCALE)) / (tileWidth * GamePanel.SCALE);
				int row = (e.getY() - ((y - cameraY) * GamePanel.SCALE)) / (tileHeight * GamePanel.SCALE);

				//If this is the first reveal, generate the board
				if(clicks == 0)
				{
					createNewBoard(column, row);
				}

				//Action depends on what type of tile the user clicks on
				switch(board[column][row])
				{
				//If the tile has not been pressed
				case 0:
					//If left clicked, set the tile to be pressed
					if(SwingUtilities.isLeftMouseButton(e))
					{
						//Set the gameStatus to the :O
						gameStatus = 1;
						board[column][row] = 4;
					}
					//If right clicked, set the tile to be flagged
					else if(SwingUtilities.isRightMouseButton(e))
					{
						board[column][row] = 2;
						numOfMines--;
					}
					break;
				//If the tile is flagged
				case 2:
					//If right clicked, remove the flag
					if(SwingUtilities.isRightMouseButton(e))
					{
						board[column][row] = 0;
						numOfMines++;
					}
					break;
				}

				//Check for multi reveal, reveal down the tiles
				if(locations[column][row] >= 1 && board[column][row] == 1)
				{
					//If a multiclick is initiated
					if((Settings.DOUBLE_PRESS_MULTI_CLICK && SwingUtilities.isLeftMouseButton(e) &&
							SwingUtilities.isRightMouseButton(e)) ||
							!Settings.DOUBLE_PRESS_MULTI_CLICK && SwingUtilities.isLeftMouseButton(e))
					{
						//Check all of the surrounding tiles to the tile being pressed
						//Set the tiles to being pressed
						boolean leftAvailable = column - 1 >= 0;
						boolean rightAvailable = column + 1 < columns;
						boolean topAvailable = row - 1 >= 0;
						boolean bottomAvailable = row + 1 < rows;

						if(leftAvailable)
						{
							if(board[column - 1][row] == 0) board[column - 1][row] = 4;
							if(topAvailable)
							{
								if(board[column - 1][row - 1] == 0)
									board[column - 1][row - 1] = 4;
							}
							if(bottomAvailable)
							{
								if(board[column - 1][row + 1] == 0)
									board[column - 1][row + 1] = 4;
							}
						}
						if(rightAvailable)
						{
							if(board[column + 1][row] == 0) board[column + 1][row] = 4;
							if(topAvailable)
							{
								if(board[column + 1][row - 1] == 0)
									board[column + 1][row - 1] = 4;
							}
							if(bottomAvailable)
							{
								if(board[column + 1][row + 1] == 0)
									board[column + 1][row + 1] = 4;
							}
						}
						if(topAvailable)
						{
							if(board[column][row - 1] == 0) board[column][row - 1] = 4;
						}
						if(bottomAvailable)
						{
							if(board[column][row + 1] == 0) board[column][row + 1] = 4;
						}
					}
				}
			}
		}

		//Activate camera movement
		if(SwingUtilities.isLeftMouseButton(e))
		{
			mouseDragOriginX = e.getX();
			mouseDragOriginY = e.getY();

			tempCameraX = cameraX;
			tempCameraY = cameraY;
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		//Check for multiclick, reveal the tiles
		//If the game has not been won or lost yet
		if(gameStatus != 2 && gameStatus != 3)
		{
			//Ensure the mouse coordinates are within the bounds of the game
			if(e.getX() >= this.x * GamePanel.SCALE && e.getX() <= (this.x + this.tileWidth * columns) * GamePanel.SCALE && e.getY() >= this.y * GamePanel.SCALE && e.getY() <= (this.y + this.tileHeight * rows) * GamePanel.SCALE)
			{
				//Calculate column and row of the tile pressed on
				int column = (e.getX() - ((x - cameraX) * GamePanel.SCALE)) / (tileWidth * GamePanel.SCALE);
				int row = (e.getY() - ((y - cameraY) * GamePanel.SCALE)) / (tileHeight * GamePanel.SCALE);

				if(locations[column][row] >= 1 && board[column][row] == 1)
				{
					//If a multiclick is initiated
					if((Settings.DOUBLE_PRESS_MULTI_CLICK && SwingUtilities.isLeftMouseButton(e) &&
							SwingUtilities.isRightMouseButton(e)) ||
							!Settings.DOUBLE_PRESS_MULTI_CLICK && SwingUtilities.isLeftMouseButton(e))
					{
						//Set the flag so that the multiple clicks are handled properly
						multiClickFlag = true;
						//Set the multiClick coordinates to the tile pushed
						multiClickCoordinates = new int[] {column, row};

						clicks++;
					}
				}
			}
		}

		//Count for how many mines are flagged around the multiClicked tile
		int multiClickMineCounter = 0;

		//Loop through all tiles
		for(int column = 0; column < columns; column++)
		{
			for(int row = 0; row < rows; row++)
			{
				//If there is not a multiClick to be handled
				if(!multiClickFlag)
				{
					if(board[column][row] == 4 && SwingUtilities.isLeftMouseButton(e))
					{
						//Set the gameStatus back to normal and reveal the tile
						if(gameStatus == 1) gameStatus = 0;
						reveal(column, row, true);
					}
				}
				else
				{
					int multiClickColumn = multiClickCoordinates[0];
					int multiClickRow = multiClickCoordinates[1];

					//If the tile being looped over is within the 8 tiles surrounding the multiClicked tile
					if(column == multiClickColumn || column == multiClickColumn - 1
							|| column == multiClickColumn + 1)
					{
						if(row == multiClickRow || row == multiClickRow - 1 || row == multiClickRow + 1)
						{
							//If the tile is flagged, increment the mineCounter
							if(board[column][row] == 2) multiClickMineCounter++;
						}
					}

					//If the mineCounter is equal to the number of mines surrounding the multiClickTile
					if(multiClickMineCounter == locations[multiClickCoordinates[0]]
							[multiClickCoordinates[1]])
					{
						clickAround(multiClickCoordinates[0], multiClickCoordinates[1]);
					}

					//Set all pressed in tiles back to their normal state, if they were not clicked
					if(board[column][row] == 4) board[column][row] = 0;
				}
			}
		}

		//Set the flag back to false to prepare for next multiClick
		multiClickFlag = false;
	}

	public void mouseDragged(MouseEvent e)
	{
		//Move around the camera
		if(SwingUtilities.isLeftMouseButton(e))
		{
			//Change the camera depending on the origin of the reveal, and then divide by scale to scale down movement
			if(cameraModeX) cameraX = tempCameraX - (e.getX() - mouseDragOriginX) / GamePanel.SCALE;
			if(cameraModeY) cameraY = tempCameraY - (e.getY() - mouseDragOriginY) / GamePanel.SCALE;

			//Limit the camera movement
			if(cameraX < 0) cameraX = 0;
			if(cameraY < 0) cameraY = 0;
			if(cameraModeX)
			{
				if(cameraX + GamePanel.WIDTH > columns * tileWidth)
					cameraX = (columns * tileWidth) - GamePanel.WIDTH;
			}
			if(cameraModeY)
			{
				if(cameraY + GamePanel.HEIGHT > rows * tileHeight)
					cameraY = (rows * tileWidth) - GamePanel.HEIGHT;
			}
		}
	}

	//Reveal a tile
	void reveal(int column, int row, boolean countClick)
	{
		//If this reveal was not part of a multiclick, then the reveal will be counted
		if(countClick) clicks++;

		//If the tile is not already pressed and it is not flagged
		if(!(board[column][row] == 1 || board[column][row] == 2 || board[column][row] == 5))
		{
			//Set the tile to pressed
			board[column][row] = 1;

			//If tile is empty, reveal all adjacent tiles
			if(locations[column][row] == 0)
			{
				clickAround(column, row);
			}

			//If a mine is clicked
			else if(locations[column][row] == -1)
			{
				//Set gameStatus to lost state
				gameStatus = 3;

				for(int columnI = 0; columnI < columns; columnI++)
				{
					for(int rowI = 0; rowI < rows; rowI++)
					{
						//If a tile is flagged and not a mine, set it to a false flag
						if(board[columnI][rowI] == 2 && locations[columnI][rowI] != -1)
						{
							board[columnI][rowI] = 5;
						}

						//Find mines, and reveal them, but not activate them
						else if(locations[columnI][rowI] == -1 && board[columnI][rowI] != 2 &&
								//If at least one of the coordinates does not match,
								//then the mine is not the one initially clicked on
								(column != columnI || row != rowI))
						{
							//Set the other mines to the untriggered state and the
							//clicked mine that remains -1 will be highlighted red
							locations[columnI][rowI] = -2;
							board[columnI][rowI] = 1;
						}
					}
				}
			}
		}
	}

	//Click all tiles around a certain tile, does not reveal tile given
	private void clickAround(int column, int row)
	{
		//Check to make sure the tiles do not go out of bounds
		if(column - 1 >= 0)
		{
			reveal(column - 1, row, false);
			if(row - 1 >= 0) reveal(column - 1, row - 1, false);
			if(row + 1 < rows) reveal(column - 1, row + 1, false);
		}
		if(column + 1 < columns)
		{
			reveal(column + 1, row, false);
			if(row - 1 >= 0) reveal(column + 1, row - 1, false);
			if(row + 1 < rows) reveal(column + 1, row + 1, false);
		}
		if(row - 1 >= 0) reveal(column, row - 1, false);
		if(row + 1 < rows) reveal(column, row + 1, false);
	}

	public void draw(Graphics2D g2d)
	{
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		//Offset the drawing by a certain amount
		AffineTransform transform = g2d.getTransform();
		g2d.translate(x, y);

		//Go through every tile and find out which sprite it corresponds to, and draw it
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
					//If the tile has been pressed, draw a different tile depending on what is underneath
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

				g2d.drawImage(drawImage, column * tileWidth - cameraX, row * tileHeight - cameraY,
						tileWidth, tileHeight, null);
			}
		}

		//Reset the translation
		g2d.setTransform(transform);
	}

	//Zero Coordinates are for the initial click, and mines will not spawn around the coordinates
	private void addRandomMine(int zeroColumn, int zeroRow)
	{
		//Generate random value using Math.random(), which goes 0-1
		//Cast to int truncates the value, so 0 has an equal chance
		//Normally, we add one to the column and row, but because they are indices, we don't
		int column = (int) (Math.random() * columns);
		int row = (int) (Math.random() * rows);

		//Flag for whether the coordinates generated are or are not part of the
		boolean keepGoing = true;

		//If the generated mine is near the first click, then generate a new mine
		if(column + 1 == zeroColumn || column == zeroColumn || column - 1 == zeroColumn)
		{
			if(row + 1 == zeroRow || row == zeroRow || row - 1 == zeroRow)
			{
				addRandomMine(zeroColumn, zeroRow);
				keepGoing = false;
			}
		}

		//If this iteration of the random mine is not near the click, then continue with placing the mine
		if(keepGoing)
		{
			//Ensure location chosen is not a mine
			if(locations[column][row] != -1)
			{
				locations[column][row] = -1;

				//Determine which sides are free
				//Add one to all surrounding blocks to generate the number clues
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
			//If the random coordinates go to a mine, then generate a new mine
			else addRandomMine(zeroColumn, zeroRow);
		}
	}

	//Refresh all of the mine numbers
	void refreshNumbers()
	{
		//Set all nonmines back to 0
		for(int column = 0; column < columns; column++)
		{
			for(int row = 0; row < rows; row++)
			{
				if(locations[column][row] != -1) locations[column][row] = 0;
			}
		}

		//Iterate through all tiles
		for(int column = 0; column < columns; column++)
		{
			for(int row = 0; row < rows; row++)
			{
				if(locations[column][row] == -1)
				{
					//Determine which sides are free
					//Add one to all surrounding blocks to generate the number clues
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
			}
		}
	}

	void setValue(int column, int row, int value)
	{
		locations[column][row] = value;
	}

	void setFlag(int column, int row)
	{
		board[column][row] = 2;
	}

	//Set the coordinates of the mineField for drawing
	public void setCoordinates(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	int getGameStatus()
	{
		return gameStatus;
	}
	int getNumOfMines()
	{
		return numOfMines;
	}
}
