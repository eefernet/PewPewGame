package mainGameFiles;

public class playerLimit 
{
	window console = new window();
	public boolean windowColliderX(int playerPos)
	{
		boolean flag = false;
		
		if (playerPos <= -10 || playerPos > console.setWindowSizeX())
		{
			 flag = true;
		}
		
		else
		{
			flag = false;
		}
		return flag;
	}
	
	public boolean windowColliderY(int playerPos)
	{
		boolean flag = false;
		
		if (playerPos <= 0 || playerPos > console.setWindowSizeY())
		{
			flag = true;
		}
		
		else
		{
			flag = false;
		}
		
		return flag;
	}
}
