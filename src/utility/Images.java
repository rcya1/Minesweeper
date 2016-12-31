package utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Images
{
	private static BufferedImage loadSingleImage(String string)
	{
		try
		{
			Image image = ImageIO.read(Images.class.getResourceAsStream(string));
			return (BufferedImage) image;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static class TopBar
	{
		public static class Faces
		{
			public static final int faceWidth = 26;
			public static final int faceHeight = 26;
			private static final BufferedImage FACES = loadSingleImage("/topBar/Faces.png");

			public static final BufferedImage NEUTRAL = FACES != null ?
					FACES.getSubimage(0, 0, faceWidth, faceHeight) :
					null;
			public static final BufferedImage PRESSED = FACES != null ?
					FACES.getSubimage(faceWidth, 0, faceWidth, faceHeight) :
					null;
			public static final BufferedImage MOUTH_OPENED = FACES != null ?
					FACES.getSubimage(faceWidth * 2, 0, faceWidth, faceHeight) :
					null;
			public static final BufferedImage WIN = FACES != null ?
					FACES.getSubimage(faceWidth * 3, 0, faceWidth, faceHeight) :
					null;
			public static final BufferedImage LOSS = FACES != null ?
					FACES.getSubimage(faceWidth * 4, 0, faceWidth, faceHeight) :
					null;
		}

		public static class Numbers
		{
			public static final int numberWidth = 13;
			public static final int numberHeight = 23;

			private static final BufferedImage NUMBERS = loadSingleImage("/topBar/Numbers.png");

			public static final BufferedImage NUMBER_1 = NUMBERS != null ?
					NUMBERS.getSubimage(0, 0, numberWidth, numberHeight) :
					null;
			public static final BufferedImage NUMBER_2 = NUMBERS != null ?
					NUMBERS.getSubimage(numberWidth, 0, numberWidth, numberHeight) :
					null;
			public static final BufferedImage NUMBER_3 = NUMBERS != null ?
					NUMBERS.getSubimage(numberWidth * 2, 0, numberWidth, numberHeight) :
					null;
			public static final BufferedImage NUMBER_4 = NUMBERS != null ?
					NUMBERS.getSubimage(numberWidth * 3, 0, numberWidth, numberHeight) :
					null;
			public static final BufferedImage NUMBER_5 = NUMBERS != null ?
					NUMBERS.getSubimage(numberWidth * 4, 0, numberWidth, numberHeight) :
					null;
			public static final BufferedImage NUMBER_6 = NUMBERS != null ?
					NUMBERS.getSubimage(0, numberHeight, numberWidth, numberHeight) :
					null;
			public static final BufferedImage NUMBER_7 = NUMBERS != null ?
					NUMBERS.getSubimage(numberWidth, numberHeight, numberWidth, numberHeight) :
					null;
			public static final BufferedImage NUMBER_8 = NUMBERS != null ?
					NUMBERS.getSubimage(numberWidth * 2, numberHeight, numberWidth, numberHeight) :
					null;
			public static final BufferedImage NUMBER_9 = NUMBERS != null ?
					NUMBERS.getSubimage(numberWidth * 3, numberHeight, numberWidth, numberHeight) :
					null;
			public static final BufferedImage NUMBER_0 = NUMBERS != null ?
					NUMBERS.getSubimage(numberWidth * 4, numberHeight, numberWidth, numberHeight) :
					null;
		}
	}

	public static class Tiles
	{
		private static final int WIDTH = 16;
		private static final int HEIGHT = 16;

		private static final BufferedImage TILES = loadSingleImage("/tiles/Tiles.png");

		public static final BufferedImage EMPTY = TILES != null ?
				TILES.getSubimage(0, 0, WIDTH, HEIGHT) :
				null;
		public static final BufferedImage PRESSED = TILES != null ?
				TILES.getSubimage(WIDTH, 0, WIDTH, HEIGHT) :
				null;
		public static final BufferedImage FLAG = TILES != null ?
				TILES.getSubimage(WIDTH * 2, 0, WIDTH, HEIGHT) :
				null;
		public static final BufferedImage QUESTION = TILES != null ?
				TILES.getSubimage(WIDTH * 3, 0, WIDTH, HEIGHT) :
				null;
		public static final BufferedImage MINE_UNTRIGGERED = TILES != null ?
				TILES.getSubimage(0, HEIGHT, WIDTH, HEIGHT) :
				null;
		public static final BufferedImage MINE_TRIGGERED = TILES != null ?
				TILES.getSubimage(WIDTH, HEIGHT, WIDTH, HEIGHT) :
				null;
		public static final BufferedImage MINE_FALSE = TILES != null ?
				TILES.getSubimage(WIDTH * 2, HEIGHT, WIDTH, HEIGHT) :
				null;

		public static class Numbers
		{
			private static final BufferedImage NUMBERS = loadSingleImage("/tiles/Numbers.png");

			public static final BufferedImage ONE = NUMBERS != null ?
					NUMBERS.getSubimage(0, 0, WIDTH, HEIGHT) :
					null;
			public static final BufferedImage TWO = NUMBERS != null ?
					NUMBERS.getSubimage(WIDTH, 0, WIDTH, HEIGHT) :
					null;
			public static final BufferedImage THREE = NUMBERS != null ?
					NUMBERS.getSubimage(WIDTH * 2, 0, WIDTH, HEIGHT) :
					null;
			public static final BufferedImage FOUR = NUMBERS != null ?
					NUMBERS.getSubimage(WIDTH * 3, 0, WIDTH, HEIGHT) :
					null;
			public static final BufferedImage FIVE = NUMBERS != null ?
					NUMBERS.getSubimage(0, HEIGHT, WIDTH, HEIGHT) :
					null;
			public static final BufferedImage SIX = NUMBERS != null ?
					NUMBERS.getSubimage(WIDTH, HEIGHT, WIDTH, HEIGHT) :
					null;
			public static final BufferedImage SEVEN = NUMBERS != null ?
					NUMBERS.getSubimage(WIDTH * 2, HEIGHT, WIDTH, HEIGHT) :
					null;
			public static final BufferedImage EIGHT = NUMBERS != null ?
					NUMBERS.getSubimage(WIDTH * 3, HEIGHT, WIDTH, HEIGHT) :
					null;
		}
	}
}
