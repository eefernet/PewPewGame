package mainGameFiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class playerFire extends JFrame
{
	

	window console = new window();
	playerObject image = new playerObject();
	
	public static boolean isShooting;
	public static boolean magEmpty = false;
	
	public static int ammo = 150;
	
	public boolean collide;
	private int laserPosX,laserPosY,speedY;
	public boolean isVisible;

	
	
	public boolean readyToFire = true;
	
	public boolean isReadyToFire() 
	{
		return readyToFire;
	}


	public void setReadyToFire(boolean readyToFire) 
	{
		this.readyToFire = readyToFire;
	}
	
	public ArrayList<playerFire> lasers = new ArrayList<playerFire>();
	
	
	
	public playerFire(int x, int y)
	{
		speedY = 80;
		laserPosX = x;
		laserPosY = y;
		isVisible = true;
	}

	
	public void shooting(boolean shoot)
	{
		if(readyToFire)
		{
			if(ammo>0)
			{
				isShooting = shoot;
				//System.out.println(ammo);
				
				if(isShooting)
				{
					ammo-=1;
				}
			}
			
			else
			{
				magEmpty = true;
			}
		}
		
		
	}
	
	public int update()
	{
		
		for(int i = 0; i < lasers.size();++i)
		{
			playerFire laser = lasers.get(i);
			
			if(laser.getLaserPosY() <= 0)
			{
				laser.remove(i);
				System.out.println("laser Removed");
			}
		}
		
		if(laserPosY<1)
		{
			
			isVisible = false;
		}
		
		laserPosY -= speedY;
		
		return laserPosY;
	}
	
	public void shoot()
	{
		if(readyToFire)
		{
			playerFire b = new playerFire(laserPosX+2,laserPosY-10);
			lasers.add(b);
			System.out.println(lasers.size());
		}
		
	}
	
	public int getLaserPosX()
	{
		return laserPosX;
	}
	
	public int getLaserPosY()
	{
		return laserPosY;
	}
	
	public int getSpeed()
	{
		return speedY;
	}
	
	public boolean isVisible()
	{
		return isVisible;
	}
	
	public void setX(int x)
	{
		this.laserPosX=x;
	}
	
	public void setY(int y)
	{
		this.laserPosY=y;
	}
	
	public void setSpeed(int s)
	{
		this.speedY=s;
	}
	
	public void setVisible(boolean v)
	{
		this.isVisible = v;
	}
	
	public ArrayList getProjectiles()
	{
		return lasers;
	}
}
