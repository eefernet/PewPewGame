package mainGameFiles;

import javax.sound.sampled.*;

public class bgMusic 
{
	public static boolean musicRun;
	public static boolean musicrunning;
	private Clip clip;
	
	
	public static bgMusic rocketThrusters = new bgMusic("/Sounds/ambient.wav");
	public static bgMusic laser = new bgMusic("/Sounds/shotFired.wav");
	public static bgMusic noAmmo = new bgMusic("/Sounds/noAmmo.wav");
	public static bgMusic menu = new bgMusic("/Sounds/tick.wav");
	public static bgMusic newStuff = new bgMusic("/Sounds/ambient.wav");
	public static bgMusic endGame = new bgMusic("/Sounds/gameEnd.wav");
	public static bgMusic menuBG1 = new bgMusic("/Sounds/menuPlaylist/menuBG1.wav");
	public static bgMusic menuBG2 = new bgMusic("/Sounds/menuPlaylist/menuBG2.wav");
	public static bgMusic menuBG3 = new bgMusic("/Sounds/menuPlaylist/nice.wav");
	
	
	public bgMusic (String fileName) 
	{
		try 
		{
			AudioInputStream ais = AudioSystem.getAudioInputStream(bgMusic.class.getResource(fileName));
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) 
		
		{
			e.printStackTrace();
		}
	}


	public void play() 
	{
		try 
		{
			if (clip != null) 
			{
				new Thread() 
				{
					public void run() 
					{
						synchronized (clip) 
						{
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void stop()
	{
		if(clip == null) return;
		clip.stop();
	}
	
	public void loop() 
	{
		try {
			if (clip != null) 
			{
				new Thread() 
				{
					public void run()
					{
						synchronized (clip) 
						{
							clip.stop();
							clip.setFramePosition(0);
							clip.loop(Clip.LOOP_CONTINUOUSLY);
						}
					}
				}.start();
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void isActive()
	{
		musicrunning = clip.isActive();
	}
}
