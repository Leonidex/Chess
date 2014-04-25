package game;

import gui.FullScreen;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.UIManager;


public class Main1 {

	public static void main(String[] args) {
				
		try
		{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			
	 		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = env.getDefaultScreenDevice();

	        Thread screenThread = new Thread(new FullScreen(device));
	        screenThread.run();
		}
		catch (Exception e)
		{
			System.err.println("ERROR!");
			System.err.println(e);
		}
	}
}
