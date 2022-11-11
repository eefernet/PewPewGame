package mainGameFiles;

import java.util.Random;

import javax.swing.JOptionPane;

public class playerNames 
{
	int index1,index2;
	window console = new window();
	Random rand = new Random();
	public static int numOfGames;
	public static String player1Name,player2Name;
	public static String amountOfGames;
	
	private String[] gamerTags = {"penis", "Mai Mai", "Black Maj","penis licker","noScopeRz4life","4llDamnDay","your mom"};
	
	public void playerNames()
	{
		int dialogResult = JOptionPane.showConfirmDialog (null, "FullScreen?");
		
		
		if(dialogResult == JOptionPane.YES_OPTION)
		{
			window.fullScreen = true;
		}
		
		
		player1Name = JOptionPane.showInputDialog("whats player 1 name?");
		
		if(player1Name  != null && player1Name.isEmpty())
		{
			index1 = rand.nextInt(gamerTags.length-1);
			player1Name = gamerTags[index1];
		}
		
		player2Name = JOptionPane.showInputDialog("whats player 2 name?");
		
		if(player2Name  != null && player2Name.isEmpty())
		{
			index2 = rand.nextInt(gamerTags.length);
			if(index2==index1)
			{
				index2 = rand.nextInt(gamerTags.length);
			}
			player2Name = gamerTags[index2];
		}
	
	}
	
	public void amountOfGames()
	{
		amountOfGames = JOptionPane.showInputDialog("How many games would you like to play?");
		
		/**if(amountOfGames !=null && amountOfGames.isEmpty());
		{
			amountOfGames = "2" ;
		}**/
		
		progress.maxGames = Integer.parseInt(amountOfGames);
	}
	
	public String getPlayer1name()
	{
		return player1Name;
	}
	
	public String getPlayer2name()
	{
		return player2Name;
	}
	
	public int getNumGames()
	{
		return Integer.parseInt(amountOfGames);
	}
}
