package main;

import utility.Settings;

import javax.swing.*;
import java.awt.*;

public class Game
{
	//Sprites ripped by Black Squirrel

	private static JFrame frame;

	public static void main(String[] args)
	{
		createNewWindow(144, 170);
	}

	public static void createNewWindow(int width, int height)
	{
		if(frame != null) frame.setVisible(false);
		frame = new JFrame("Minesweeper");
		frame.setContentPane(new GamePanel(width, height));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		Settings.WIDTH_OF_SCREEN = screen.width;
		Settings.HEIGHT_OF_SCREEN = screen.height - 96;
	}
}
