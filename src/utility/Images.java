package utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Images
{
	private static final int WIDTH = 16;
	private static final int HEIGHT = 16;

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

	public static class Tiles
	{
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
