package utility;

public class Settings
{
	private static final int[] DEFAULT_VALUES = {9, 9, 10};

	public static int COLUMNS;
	public static int ROWS;
	public static int NUMBER_OF_MINES;

	public static int WIDTH_OF_SCREEN;
	public static int HEIGHT_OF_SCREEN;

	private static boolean initialized;

	public static void init()
	{
		if(!initialized)
		{
			COLUMNS = DEFAULT_VALUES[0];
			ROWS = DEFAULT_VALUES[1];
			NUMBER_OF_MINES = DEFAULT_VALUES[2];
		}

		initialized = true;
	}
}
