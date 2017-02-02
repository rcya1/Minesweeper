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
		createNewWindow(145, 185);
	}

	public static void createNewWindow(int width, int height)
	{
		if(frame != null) frame.setVisible(false);
		frame = new JFrame("Minesweeper");
		frame.setContentPane(new GamePanel(width, height));
		frame.setPreferredSize(new Dimension(width * GamePanel.SCALE, height * GamePanel.SCALE));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		System.out.println(frame.getSize());

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		Settings.WIDTH_OF_SCREEN = ((screen.width - 32) / 16) * 16;
		Settings.HEIGHT_OF_SCREEN = ((screen.height - 96) / 16) * 16;
	}
}
