package mainGameFiles;


import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class playerObject 
{
	public static BufferedImage playerObj;
	
	
	public static BufferedImage loadImage(String file)
	{
		playerObj = null;
		
		try 
		{
			playerObj = ImageIO.read(mainGame.class.getResource(file));
		} 
		
		catch (IOException e) 
		
		{
			e.printStackTrace();
		}
		
		return playerObj;
	}
}
