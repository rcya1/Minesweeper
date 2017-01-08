package utility;

public class Settings
{
	public static final int[] DEFAULT_VALUES = {9, 9, 10};

	public static int COLUMNS;
	public static int ROWS;
	public static int NUMBER_OF_MINES;

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
