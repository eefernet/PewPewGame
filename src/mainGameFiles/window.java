package mainGameFiles;

import java.awt.Dimension;
import java.awt.Toolkit;

public class window {
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static boolean fullScreen = false;
	double WINDOW_HEIGHT =screenSize.getHeight();
	double WINDOW_WIDTH = screenSize.getWidth();
	
	
	public int setWindowSizeX(){
		
		if(fullScreen){
			
			WINDOW_WIDTH = screenSize.getWidth();
		}
		
		else{
			
			WINDOW_WIDTH = 1080;
		}
		return (int)WINDOW_WIDTH;
	}
	
	public double getWINDOW_HEIGHT() {
		return WINDOW_HEIGHT;
	}

	public double getWINDOW_WIDTH() {
		return WINDOW_WIDTH;
	}

	public int setWindowSizeY(){
		
		if(fullScreen){
			
			WINDOW_HEIGHT = screenSize.getHeight();
		}
		
		else{
			
			WINDOW_HEIGHT = 720;
		}
		return (int)WINDOW_HEIGHT;
	}
}
