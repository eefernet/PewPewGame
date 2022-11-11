package mainGameFiles;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class playerObtain 
{
	int playerX;
	int playerY;
	
	public static int boxX;
	public static int boxY;
	
	public static int hX;
	public static int hY;

	Rectangle ammo,healthbox;
	
	public static boolean destroy = false;
	
	window window = new window();
	Random rand = new Random();
	
	public void generateAmmo(BufferedImage ammoImage)
	{
		//destroy = false;
		boxX = rand.nextInt(window.setWindowSizeX()+2);
		boxY = rand.nextInt(window.setWindowSizeY()+2);
		
		
		ammo = new Rectangle(boxX,boxY,ammoImage.getWidth(),ammoImage.getHeight());
	}
	
	public void generateHealth(BufferedImage health)
	{
		hX = rand.nextInt(window.setWindowSizeX()+2);
		hY = rand.nextInt(window.setWindowSizeY()+2);
		
		healthbox = new Rectangle(hX,hY,64,64);	
	}
	
	public void checkForHealth(int playerX, int playerY)
	{
		Rectangle player = new Rectangle(playerX,playerY,64,64);
		 
		 if(player.intersects(healthbox))
		 {
			 mainGame.player1Health += 250;
			 destroy = true;
		 }
	}
	
	public void checkForHealth1(int playerX, int playerY)
	{
		Rectangle player = new Rectangle(playerX,playerY,64,64);
		 
		 if(player.intersects(healthbox))
		 {
			 mainGame.player2Health += 250;
			 destroy = true;
		 }
	}
	
	public void check(int playerX,int playerY)
	{
		 this.playerX = playerX;
		 this.playerY = playerY;

		 Rectangle player = new Rectangle(playerX,playerY,64,64);
		 
		 if(player.intersects(ammo))
		 {
			 playerFire.ammo += 50;
			 playerFire.magEmpty = false;
			 destroy = true;
		 }
	}
	
	public void check1(int playerX,int playerY)
	{
		 this.playerX = playerX;
		 this.playerY = playerY;

		 Rectangle player = new Rectangle(playerX,playerY,64,64);
		 
		 if(player.intersects(ammo))
		 {
			 secondPlayerFire.ammo += 50; 
			 secondPlayerFire.magEmpty = false;
			 destroy = true;
		 }
	}
	
	public boolean didGet()
	{
		return destroy;
	}
	
	
}
