package mainGameFiles;

public class bottomPlayer 
{
	public static double damage;
	public static boolean shield;
	public static int speed;
	public static double health;
	
	public static double playerXP;
	public static String playerUsername;
	public static int playerkills;
	
	playerNames names = new playerNames();
	
	public bottomPlayer()
	{
		damage = 0.5;
		shield = false;
		playerXP = 0;
		playerUsername = names.getPlayer1name();
	}
	
	public static void addXP()
	{
		playerXP +=1.5;
	}
	
	public boolean hasShield()
	{
		return shield;
	}
	
	public void maxHealthUp()
	{
		
	}
}
