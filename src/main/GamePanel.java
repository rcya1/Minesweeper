package main;

import state.StateManager;
import utility.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener
{
	public static int WIDTH;
	public static int HEIGHT;
	public static final int SCALE = 2;

	private Thread updateThread;
	private boolean running;

	private BufferedImage image;
	private Graphics2D g2d;

	private StateManager stateManager;

	public static final int FPS = 60;

	GamePanel(int width, int height)
	{
		super();

		WIDTH = width;
		HEIGHT = height;

		this.setFocusable(true);
		this.requestFocus();
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
	}

	public void addNotify()
	{
		super.addNotify();

		if(updateThread == null)
		{
			updateThread = new Thread(this);
			this.addKeyListener(this);
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			updateThread.start();
		}
	}

	private void draw()
	{
		stateManager.draw(g2d);
	}

	private void drawToScreen()
	{
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.dispose();
	}

	private void init()
	{
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D) image.getGraphics();
		running = true;
		stateManager = new StateManager();

		Settings.init();
	}

	private void update()
	{
		stateManager.update();
	}

	@Override
	public void run()
	{
		init();
		long start;
		long elapsed;
		long wait;
		while(running)
		{
			start = System.nanoTime(); //Get Current Time of this Tick
			update();
			draw();
			drawToScreen();

			long targetTime = 1000 / FPS;
			elapsed = System.nanoTime() - start; //Find how long the tick has been running
			wait = targetTime - elapsed / 1000000; //Find how long to wait from target time and how long has passed
			if(wait < 0) wait = 5;
			try
			{
				Thread.sleep(wait);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e != null) stateManager.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e != null) stateManager.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if(e != null) stateManager.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(e != null) stateManager.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}
	@Override public void mouseDragged(MouseEvent e)
	{
		if(e != null) stateManager.mouseDragged(e);
	}
	@Override public void mouseMoved(MouseEvent e)
	{
		if(e != null) stateManager.mouseMoved(e);
	}
}
