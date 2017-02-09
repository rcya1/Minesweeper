package components;

import main.GamePanel;
import utility.Images;
import utility.Settings;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class TopBar
{
	private MineField mineField;

	private int timerInFrames;
	private int timerInSeconds;

	private int faceExpression; //When the face is pressed, the faceExpression is set to 5

	public TopBar(MineField mineField)
	{
		this.mineField = mineField;
		resetTimer();
		faceExpression = 0;
	}

	public void update()
	{
		int gameStatus = mineField.getGameStatus();
		if(!(gameStatus == -1 || gameStatus == 2 || gameStatus == 3))
		{
			timerInFrames++;
			if(timerInFrames % GamePanel.FPS == 0) timerInSeconds++;
			if(timerInSeconds > 999) timerInSeconds = 999;
		}

		if(faceExpression != 4) faceExpression = gameStatus;
	}

	public void draw(Graphics2D g2d)
	{
		BufferedImage faceImage = null;
		switch(faceExpression)
		{
		case -1: //Purposeful Fallthrough
		case 0: faceImage = Images.TopBar.Faces.NEUTRAL;      break;
		case 1: faceImage = Images.TopBar.Faces.MOUTH_OPENED; break;
		case 2: faceImage = Images.TopBar.Faces.WIN;          break;
		case 3: faceImage = Images.TopBar.Faces.LOSS;         break;
		case 4: faceImage = Images.TopBar.Faces.PRESSED;      break;
		}
		g2d.drawImage(faceImage, GamePanel.WIDTH / 2 - Images.TopBar.Faces.faceWidth / 2, 0, null);

		BufferedImage[] clockImages = getNumberImages(timerInSeconds);
		for(int i = 0; i < clockImages.length; i++)
		{
			g2d.drawImage(clockImages[i], GamePanel.WIDTH - Images.TopBar.Numbers.numberWidth * 3 - 1 +
					i * Images.TopBar.Numbers.numberWidth, 2, null);
		}

		//TODO Add handling for negative numbers
		BufferedImage[] numOfMinesImages = getNumberImages(mineField.getNumOfMines());
		for(int i = 0; i < numOfMinesImages.length; i++)
		{
			g2d.drawImage(numOfMinesImages[i], 1 + i * Images.TopBar.Numbers.numberWidth, 2, null);
		}
	}

	public void mousePressed(MouseEvent e)
	{
		if(e.getX() >= (GamePanel.WIDTH / 2 - Images.TopBar.Faces.faceWidth / 2) * GamePanel.SCALE &&
				e.getX() <= (GamePanel.WIDTH / 2 + Images.TopBar.Faces.faceWidth / 2) * GamePanel.SCALE &&
				e.getY() >= 0 * GamePanel.SCALE &&
				e.getY() <= 26 * GamePanel.SCALE)
		{

			faceExpression = 4;
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		if(e.getX() >= (GamePanel.WIDTH / 2 - Images.TopBar.Faces.faceWidth / 2) * GamePanel.SCALE &&
				e.getX() <= (GamePanel.WIDTH / 2 + Images.TopBar.Faces.faceWidth / 2) * GamePanel.SCALE &&
				e.getY() >= 0 * GamePanel.SCALE &&
				e.getY() <= 26 * GamePanel.SCALE)
		{
			if(faceExpression == 4)
			{
				faceExpression = 0;
				mineField.reset(Settings.COLUMNS, Settings.ROWS, Settings.NUMBER_OF_MINES);
				resetTimer();
			}
		}
	}

	public void keyPressed(int e)
	{
		if(e == KeyEvent.VK_R)
		{
			mineField.reset(Settings.COLUMNS, Settings.ROWS, Settings.NUMBER_OF_MINES);
			resetTimer();
		}
	}

	private BufferedImage[] getNumberImages(int input)
	{
		int numberInput = input;
		Stack<Integer> numbers = new Stack<>();
		for(int i = 0; i < 3; i++)
		{
			numbers.push(numberInput % 10);
			numberInput /= 10;
		}

		BufferedImage[] images = new BufferedImage[3];
		int pass = 0;
		while(!numbers.empty())
		{
			switch(numbers.pop())
			{
			case 0: images[pass] = Images.TopBar.Numbers.NUMBER_0; break;
			case 1: images[pass] = Images.TopBar.Numbers.NUMBER_1; break;
			case 2: images[pass] = Images.TopBar.Numbers.NUMBER_2; break;
			case 3: images[pass] = Images.TopBar.Numbers.NUMBER_3; break;
			case 4: images[pass] = Images.TopBar.Numbers.NUMBER_4; break;
			case 5: images[pass] = Images.TopBar.Numbers.NUMBER_5; break;
			case 6: images[pass] = Images.TopBar.Numbers.NUMBER_6; break;
			case 7: images[pass] = Images.TopBar.Numbers.NUMBER_7; break;
			case 8: images[pass] = Images.TopBar.Numbers.NUMBER_8; break;
			case 9: images[pass] = Images.TopBar.Numbers.NUMBER_9; break;
			}
			pass++;
		}
		return images;
	}

	private void resetTimer()
	{
		timerInSeconds = 0;
		timerInFrames = 0;
	}
}