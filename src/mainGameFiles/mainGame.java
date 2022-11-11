package mainGameFiles;
import java.awt.*; 
import java.awt.event.KeyEvent; 
import java.awt.image.BufferedImage; 
import java.util.ArrayList;

import javax.swing.JFrame;



@SuppressWarnings("serial")
public class mainGame extends JFrame
{   
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	
	
	static BufferedImage Icon = playerObject.loadImage("/Images/windowIcon.png");	//loads images to ram
	static BufferedImage bgImage =  playerObject.loadImage("/Images/spaceBackgroundLvl1.jpg");
	static BufferedImage playerObj = playerObject.loadImage("/Images/spaceship.png");
	static BufferedImage playerMoving = playerObject.loadImage("/Images/spaceshipMoving.png");
	static BufferedImage player2obj = playerObject.loadImage("/Images/spaceshipplayer2.png");
	static BufferedImage player2moving = playerObject.loadImage("/Images/spaceshipMoving2.png");
	static BufferedImage explosion = playerObject.loadImage("/Images/explosion2.png");
	static BufferedImage ammoImage = playerObject.loadImage("/Images/Ammo.png");
	static BufferedImage health = playerObject.loadImage("/Images/health.png");
	static BufferedImage laserBullet = playerObject.loadImage("/Images/laser.png");
	
	
	public final static double  damage = 8.5;
	
	BufferedImage backBuffer;	//local removable images
	//BufferedImage laserBullet;
	//BufferedImage laser2;
	
	public String systemMessage;
	
	playerFire fire = new playerFire(0,0);	//new object for shooting method
	secondPlayerFire fire2 = new secondPlayerFire(0,0); //same as last
	

	public static final int SHIP_SPEED = 30;
	
	bottomPlayer player12 = new bottomPlayer();			//new objects that are used in this class
	playerLimit player = new playerLimit();
	playerNames names = new playerNames();
	playerObtain generate = new playerObtain();
	static window console = new window();
    boolean isRunning = true,died=false; //booleans that make the game run
    int fps = 60; 						//frames per second desired
    
    Font font = new Font("Courier new",Font.BOLD,20);	//simple font to use in the class
    Font f1 = new Font("Slant",Font.PLAIN,50); //new font
	Font f2 = new Font("Slant",Font.PLAIN,20); //new font
    
    public static boolean player1CheckPulse = false;	//player death check
    public boolean player2CheckPulse = false;
    public boolean player2Explosion = false;
    public boolean player1Explosion = false;
    public boolean drawGame = false;
    
    Insets insets; 
    InputHandler input; 
    
    int player2X = console.setWindowSizeX()/2, player2Y = console.setWindowSizeY()/2+600;
    public static int playerPosX = console.setWindowSizeX()/2;
    public static int playerPosY = console.setWindowSizeY()/2+200;
    
    public static double player1Health = 1000;	//player health
    public static double player2Health = 1000;
    public static boolean paused;
    private static boolean gameInit = false;
    
    public double player1Kills = 0;
    public double player2Kills = 0;
    
    public double currentGame = 0;
    Rectangle player1 = new Rectangle();
    Rectangle player2 = new Rectangle();
    
    
        
    public static void main(String[] args) //main method
    {
    	System.out.println("Main loaded");
    	mainGame game = new mainGame();
    	
    	
    	game.run();
    	System.exit(0);  //keeps window open
    }
    
    public void run() 
    {
    	bgMusic.newStuff.loop();
    	
    	if(gameInit !=true)
    	{
    		initialize();
    	}
    						//runs initialize method that makes jframe and sets up arrays for lasers
    	while(isRunning)
    	{
    		@SuppressWarnings("rawtypes")				//gets rid of freaking annoying rawtype errors on my methods
			ArrayList lasers = fire.getProjectiles();
    		ArrayList lasers2 = fire2.getProjectiles();
    		
        	
    		
    		long time = System.currentTimeMillis(); //starts timer for a later method
    		for(int i = 0; i < lasers.size(); i++)		//checks every laser x and y position to check to see if its in the screen
        	{
        		playerFire p = (playerFire) lasers.get(i);	
        		if(p.isVisible())
        		{
        			p.update();
        		}
        		
        		else
        		{
        			lasers.remove(i);					//removes it when it leaves the screen so it doesn't bog down the memory.
        		}
        	}
        	
        	for(int i = 0; i < lasers2.size(); i++)
        	{
        		secondPlayerFire p2 = (secondPlayerFire) lasers2.get(i);
        		if(p2.isVisible())
        		{
        			p2.update();
        		}
        		
        		else
        		{
        			lasers2.remove(i);
        		}
        	}
            
            	update(); //runs update method
                draw();		//runs draw method
            
            
             
            time = (1000 / fps) - (System.currentTimeMillis() - time); //makes it refresh at the given frame rate
                        
            if (time > 0) 							//checks to see if the system is behind on the update then if its not it sleeps
            {
            	try 
                {
            		Thread.sleep(time); 
                } 
                catch(Exception e){} 
                    } 
    		} 
    	  
            setVisible(false); 
        } 
        
        void initialize() 					//makes the JFrame that the game is printed to.
        {
        	
            setTitle("Interstellar"); 
            setIconImage(Icon);
            setSize(console.setWindowSizeX(), console.setWindowSizeY()); 
            setResizable(false); 
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            if(console.fullScreen)
            {
            	setUndecorated(true);
            }
            
            setVisible(true);
            
            generate.generateAmmo(ammoImage);
            generate.generateHealth(health);
                
           insets = getInsets(); //sets background image that refreshes to make it buffer better
           setSize(insets.left + console.setWindowSizeX() + insets.right, insets.top + console.setWindowSizeY() + insets.bottom); 
                
           backBuffer = new BufferedImage(console.setWindowSizeX(), console.setWindowSizeY(), BufferedImage.TYPE_INT_RGB); //makes the image a bufferedImage type that refreshes better without flickering
           input = new InputHandler(this); //new object to my keyboard class that takes input from the keyboard
           gameInit = true;
           
        } 
        
        
        void update() 	//update method that checks for changes or changes something that needs to be changed in the draw method
        {	
        	if(player1CheckPulse!=true && player2CheckPulse !=true && paused!=true) //if both players are alive do this
        	{
        		if(input.isKeyDown(KeyEvent.VK_ESCAPE))
                {
        			pausedGame();
                }
        	}
        	
        	if (input.isKeyDown(KeyEvent.VK_D)) 					//checks to see if a key was pressed from the input class
            {
            	playerObj = playerMoving;						//reassigns image to the player
            	if(player.windowColliderX(playerPosX) != true)	//checks to see if the player hits the edge of the window 
            	{
            		
            		playerPosX += SHIP_SPEED;							//if not just add to the x value
            		draw();										//run the draw method
            	}
            	
            	else
            	{
            		
            		playerPosX = 0;								//if it does go to this x coordinate
            		draw();										//run the draw method
            	}   
            } 
            
            if (input.isKeyDown(KeyEvent.VK_A)) 
            { 
            	playerObj = playerMoving;
            	if(player.windowColliderX(playerPosX) != true)
            	{
            		
            		playerPosX -= SHIP_SPEED;
            		draw();
            	}
            	
            	else
            	{
            		
            		playerPosX = console.setWindowSizeX();
            		draw();
            	}
            } 
            
            if (input.isKeyDown(KeyEvent.VK_W))
            {
            	playerObj = playerMoving;
            	
            	if (player.windowColliderY(playerPosY) != true)
            	{
            		
            		playerPosY -= SHIP_SPEED;
            		draw();
            	}
            	else
            	{
            		
            		playerPosY = console.setWindowSizeY();
            		draw();
            	}
            	
            }
            
            if (input.isKeyDown(KeyEvent.VK_S))
            {
            	playerObj = playerMoving;
            	if(player.windowColliderY(playerPosY) != true)
            	{
            		playerPosY += SHIP_SPEED;
            		draw();
            	}
            	
            	else
            	{
            		playerPosY = 5;
            		draw();
            	}
            	
            }
            
            if(input.isKeyDown(KeyEvent.VK_SPACE))
            {
            	fire.shooting(true);
            	
            	if(fire.magEmpty!=true)
            	{
            		
                	fire.setX(playerPosX);
                	fire.setY(playerPosY);
                	fire.shoot();
                	fire.setReadyToFire(false);
                	bgMusic.laser.play();
                	draw();
            	}
            	
            	else if(fire.magEmpty)
            	{
            		bgMusic.noAmmo.play();
            	}
            }
            
            if(input.isKeyDown(KeyEvent.VK_SPACE)!=true)
            {
            	fire.shooting(false);
            	fire.setReadyToFire(true);
            	draw();
            }
            
            if(input.isKeyDown(KeyEvent.VK_SHIFT))
            {
            	fire2.shooting(true);
            	
            	if(fire2.magEmpty!=true)
            	{
            		
                	fire2.setX(player2X);
                	fire2.setY(player2Y);
                	fire2.shoot();
                	fire2.setReadyToFire(false);
                	bgMusic.laser.play();
                	draw();
            	}
            	
            	else
            	{
            		bgMusic.noAmmo.play();
            	}
            }
            
            if(input.isKeyDown(KeyEvent.VK_SHIFT)!=true)
            {
            	fire2.shooting(false);
            	fire2.setReadyToFire(true);
            	draw();
            }
            
            if(input.isKeyDown(KeyEvent.VK_UP))
            {
            	player2obj = player2moving;
            	
            	if (player.windowColliderY(player2Y) != true)
            	{
            		player2Y -= SHIP_SPEED;
            		draw();
            	}
            	else
            	{
            		player2Y = console.setWindowSizeY();
            		draw();
            	}
            }
            
            if (input.isKeyDown(KeyEvent.VK_RIGHT)) 
            {
            	player2obj = player2moving;
            	if(player.windowColliderX(player2X) != true)
            	{
            		player2X += SHIP_SPEED;
            		draw();
            	}
            	
            	else
            	{
            		player2X = 0;
            		draw();
            	}   
            } 
            
            if (input.isKeyDown(KeyEvent.VK_LEFT)) 
            { 
            	player2obj = player2moving;
            	if(player.windowColliderX(player2X) != true)
            	{
            		player2X -= SHIP_SPEED;
            		draw();
            	}
            	
            	else
            	{
            		player2X = console.setWindowSizeX();
            		draw();
            	}
            } 
            
            if (input.isKeyDown(KeyEvent.VK_DOWN))
            {
            	player2obj = player2moving;
            	if(player.windowColliderY(player2Y) != true)
            	{
            		player2Y += SHIP_SPEED;
            		draw();
            	}
            	
            	else
            	{
            		player2Y = 1;
            	}
            }                
    	
            if(player1CheckPulse || player2CheckPulse)
            {
            	if(input.isKeyDown(KeyEvent.VK_ESCAPE))
                {
                	reset();
                }	
            }
            
            if(input.isKeyDown(KeyEvent.VK_F12))
            {
            	System.exit(0);
            }
            else
            {
            	player2obj = playerObject.loadImage("/Images/spaceshipplayer2.png");
            	playerObj = playerObject.loadImage("/Images/spaceship.png");
            }
                
        }
        
        void pausedGame() 
        {        	
        	while(paused)
        	{
        		if(input.isKeyDown(KeyEvent.VK_ESCAPE))
        		{
        			paused = false;
        			update();
        			break;
        		}
        	}
		}

		public void keyReleased(KeyEvent e)
        {
        	switch(e.getKeyCode())
        	{
        	case KeyEvent.VK_SPACE:
        		fire.readyToFire= true;
        		break;
        	
        	case KeyEvent.VK_SHIFT:
        		fire2.readyToFire = true;
        		break;
        	}
        }
        
        @SuppressWarnings({ "static-access", "rawtypes" })
		void draw() 
        {
        	Graphics g = getGraphics();
        	Graphics bbg = backBuffer.getGraphics();
        	g.drawImage(backBuffer, insets.left, insets.top, this);
        	
        	
        	bbg.clearRect(0, 0, console.setWindowSizeX(), console.setWindowSizeY());
        	
        	
        	bbg.drawImage(bgImage,0,0,getWidth(),getHeight(), null);
        	
        	
        	
        	
        	if(progress.maxGames>progress.currentGame)
        	{
        			

            	if(player1CheckPulse)
            	{
            		bgImage = playerObject.loadImage("/Images/spaceBackgroundgameend.jpg");
            		Font f1 = new Font("Slant",Font.PLAIN,50);
                    bbg.setFont(f1);
            		bbg.drawString(names.player1Name+" has lost!", 100,100);
            		bbg.drawString("Get rekt m8!", console.setWindowSizeX()/2-150, console.setWindowSizeY()/2-150);
            		bbg.setFont(f2);
            		progress.bottomPlayerKill+=1;
            		bbg.drawString(names.getPlayer1name()+ "............." + Double.toString(progress.bottomPlayerXp), console.setWindowSizeX()/2-150, console.setWindowSizeY()/2-120);
            		bbg.drawString(names.getPlayer2name()+ "............." + Double.toString(progress.topPlayerXp), console.setWindowSizeX()/2-150, console.setWindowSizeY()/2-100);
            		
            		if(progress.topPlayerXp-progress.bottomPlayerXp > 5000)
            		{
            			bbg.drawString(names.getPlayer1name()+ " is getting his ass kicked by " + names.getPlayer2name(), console.setWindowSizeX()/2-150, console.setWindowSizeY()/2+200);
            		}
            		
            	}
            	
            	if(player2CheckPulse)
            	{
            		
            		bgImage = playerObject.loadImage("/Images/spaceBackgroundgameend.jpg");
            		progress.bottomPlayerKill+=1;
                    bbg.setFont(f1);
                    bbg.drawString(names.getPlayer2name()+" has lost!", 100,100);
            		bbg.drawString("Get rekt m8!", console.setWindowSizeX()/2-150, console.setWindowSizeY()/2-150);
            		bbg.setFont(f2);
            		
            		bbg.drawString(names.getPlayer1name()+ "............." + Double.toString(progress.bottomPlayerXp), console.setWindowSizeX()/2-150, console.setWindowSizeY()/2-120);
            		bbg.drawString(names.getPlayer2name()+ "............." + Double.toString(progress.topPlayerXp), console.setWindowSizeX()/2-150, console.setWindowSizeY()/2-100);
            		
            		if(progress.bottomPlayerXp-progress.topPlayerXp > 5000)
            		{
            			bbg.drawString(names.getPlayer2name()+ " is getting his ass kicked by " + names.getPlayer1name(), console.setWindowSizeX()/2-150, console.setWindowSizeY()/2+200);
            		}
            		
            	}
            	
            	if(player1CheckPulse!=true && player2CheckPulse !=true)
            	{
            		
            		bbg.drawImage(playerObj, playerPosX, playerPosY, this);
            		bbg.drawImage(player2obj, player2X, player2Y, this);
            		bbg.drawString(names.player2Name, player2X, player2Y-20);
            		bbg.drawString(names.player1Name, playerPosX, playerPosY-20);
            		bbg.setFont(f2);
            		bbg.drawString("Health of " + names.player2Name+ " " +(int)player2Health/10+"%", 20,40);
            		bbg.drawString("Health of " + names.player1Name+ " " +(int)player1Health/10+"%", 20,console.setWindowSizeY()-40);
            		
            		if(player1Health<200)
            		{
            			bbg.drawImage(health, generate.hX, generate.hY,64,64, this);
            			generate.checkForHealth(playerPosX, playerPosY);
            		}
            		
            		if(player2Health<200)
            		{
            			bbg.drawImage(health, generate.hX, generate.hY,64,64, this);
            			generate.checkForHealth1(player2X,player2Y);
            		}
            		
            		if(fire.ammo<50)
            		{
            			bbg.drawImage(ammoImage, generate.boxX, generate.boxY, this);
            			generate.check(playerPosX, playerPosY);
            			
            			bbg.setColor(Color.RED);
            			bbg.drawString("Ammo : " + fire.ammo, playerPosX, playerPosY);
            		}
            		
            		else
            		{
            			bbg.drawString("Ammo : " + fire.ammo, playerPosX, playerPosY);
            		}
            		
            		if(fire2.ammo<50)
            		{
            			bbg.drawImage(ammoImage, generate.boxX, generate.boxY, this);
            			generate.check1(player2X, player2Y);
            			
            			bbg.setColor(Color.RED);
            			bbg.drawString("Ammo : " + fire2.ammo, player2X, player2Y);
            		}
            		
            		else
            		{
            			bbg.drawString("Ammo : " + fire2.ammo, player2X, player2Y);
            		}

                	
                
                		ArrayList lasers1 = fire.getProjectiles();
                		for(int i = 0; i < lasers1.size();i++)
                		{
                			playerFire p = (playerFire) lasers1.get(i);
                		
            				bbg.drawImage(laserBullet,p.getLaserPosX(),p.getLaserPosY(),null);
            				player2.setBounds(player2X-25,player2Y-25,64,64);
            		    	Rectangle laser = new Rectangle(p.getLaserPosX(),p.getLaserPosY(),1,1);
            		    	
            		    	if(player2.intersects(laser))
            		    	{
            		    		if(player2Health>0)
                				{
            		    			
            		    			p.setVisible(false);
            		    			
                					progress.bottomPlayerXp+=1;
                					player2Explosion = true;
                					player2Health-=damage;
                					System.out.println(player2Health);
                				}
                				
                				else
                				{
                					System.out.println("enemy die");
                    				player2CheckPulse = true;
                				}
            		    	}
                		}
                		
                		ArrayList lasers2 = fire2.getProjectiles();
                		for(int i = 0; i < lasers2.size();i++)
                		{
                			secondPlayerFire p2 = (secondPlayerFire) lasers2.get(i);
                			
            				bbg.drawImage(laserBullet,p2.getLaserPosX(),p2.getLaserPosY(),null);
            				player1.setBounds(playerPosX-25,playerPosY-25,64,64);
            		    	Rectangle laser = new Rectangle(p2.getLaserPosX(),p2.getLaserPosY(),1,1);
            		    	
            		    	if(player1.intersects(laser))
            		    	{
            		    		if(player1Health>0)
                				{	
            		    			
            		    			p2.setVisible(false);
            		    			
                					progress.topPlayerXp+=1;
                					player1Explosion = true;
                					player1Health-=damage;
                					System.out.println(player1Health);
                				}
                				
                				else
                				{
                					
                					System.out.println("enemy die");
                    				player1CheckPulse = true;
                				}
            		    	
            		    	}

                		if(fire.magEmpty)
                		{
                			bbg.setColor(Color.RED);
                			bbg.setFont(font);
                			bbg.drawString("Mag empty", playerPosX+20, playerPosY-10);
                			
                		}
                		
                		if(fire2.magEmpty)
                		{
                			bbg.setColor(Color.RED);
                			bbg.setFont(font);
                			bbg.drawString("Mag empty", player2X+20, player2Y-10);
                		}
                		
                		
                		
                		bbg.drawImage(playerObj, playerPosX, playerPosY, this);
                		bbg.drawImage(player2obj, player2X, player2Y, this);
                		if(player1Explosion)
                		{
                			bbg.drawImage(explosion, playerPosX-60, playerPosY-80, this);
                			
                		}
                		
                		if(player2Explosion)
                		{
                			bbg.drawImage(explosion, player2X-60,player2Y-80, this);
                			
                		}
                		player1Explosion = false;
                		player2Explosion = false;
            	}
            	
            	
        	}
        	
        	}
            	else
            	{
            		if(bottomPlayer.playerXP > topPlayer.playerXP)
            		{
            			
            			bbg.setFont(f2);
            			bgImage = playerObject.loadImage("/Images/spaceBackgroundgameend.jpg");
            			bbg.drawString(names.getPlayer1name()+ "............." + Double.toString(progress.bottomPlayerXp)+ " Kills : " + progress.bottomPlayerKill, console.setWindowSizeX()/2-150, console.setWindowSizeY()/2-120);
                		bbg.drawString(names.getPlayer2name()+ "............." + Double.toString(progress.topPlayerXp) + " Kills : " + progress.topPlayerKill, console.setWindowSizeX()/2-150, console.setWindowSizeY()/2-100);
                		
                		bbg.drawString(bottomPlayer.playerUsername+" wins", 100, 100);
                		bbg.drawString("Press <enter> to continue...", 100, 150);
                		
                		if(input.isKeyDown(KeyEvent.VK_ENTER))
                        {
                			resetToMenu();
                    		this.setVisible(false);
                    		this.dispose();
                        	String[] args = {};
                    		mainMenu.main(args);
                        }  
            		}
            		
            		else
            		{
            			
            			bbg.setFont(f2);
            			bgImage = playerObject.loadImage("/Images/spaceBackgroundgameend.jpg");
            			bbg.drawString(names.getPlayer2name()+ "............." + Double.toString(progress.topPlayerXp) + " Kills : " + progress.topPlayerKill, console.setWindowSizeX()/2-150, console.setWindowSizeY()/2-100);
            			bbg.drawString(names.getPlayer1name()+ "............." + Double.toString(progress.bottomPlayerXp)+ " Kills : " + progress.bottomPlayerKill, console.setWindowSizeX()/2-150, console.setWindowSizeY()/2-120);
                		
            			
                		bbg.drawString(names.getPlayer2name()+" wins", 100, 100);
                		bbg.drawString("Press <enter> to continue...", 100, 150);
                		
                		if(input.isKeyDown(KeyEvent.VK_ENTER))
                        {
                			resetToMenu();
                    		this.setVisible(false);
                    		this.dispose();
                        	String[] args = {};
                    		mainMenu.main(args);
                        }
            		}
        		
        		
        	}	
        }
        	
        
        void reset()
        {
        	bgMusic.endGame.play();
        	progress.currentGame +=1;
        	
        	player1CheckPulse = false;
        	player2CheckPulse = false;
            player2Explosion = false;
            player1Explosion = false;
            
            player1Health = 1000;
            player2Health = 1000;
            if(player1CheckPulse)
            {
            	progress.topPlayerKill +=1;
            }
            
            else
            {
            	progress.bottomPlayerKill +=1;
            }
            
            
            
            player2X = console.setWindowSizeX()/2;
            player2Y = console.setWindowSizeY()/2+600;
            playerPosX = console.setWindowSizeX()/2;
            playerPosY = console.setWindowSizeY()/2+200;
            
            fire.ammo = 150;
            secondPlayerFire.ammo = 150;
            fire.magEmpty = false;
            secondPlayerFire.magEmpty = false;
            bgImage = playerObject.loadImage("/Images/spaceBackgroundLvl1.jpg");
            this.setVisible(false);
        	this.dispose();
        	String[] args = {};
    		mainGame.main(args);  
        }
        
        void resetToMenu()
        {
        	bgMusic.endGame.play();
        	progress.currentGame = 0;
        	progress.maxGames = 0;
        	player1CheckPulse = false;
        	player2CheckPulse = false;
            player2Explosion = false;
            player1Explosion = false;
            
            progress.bottomPlayerXp = 0;
            progress.topPlayerXp = 0;
            progress.topPlayerKill = 0;
            progress.bottomPlayerKill =0;
            
            player1Health = 1000;
            player2Health = 1000;
            
            player2X = console.setWindowSizeX()/2;
            player2Y = console.setWindowSizeY()/2+600;
            playerPosX = console.setWindowSizeX()/2;
            playerPosY = console.setWindowSizeY()/2+200;
            
            fire.ammo = 150;
            secondPlayerFire.ammo = 150;
            fire.magEmpty = false;
            secondPlayerFire.magEmpty = false;
            bgImage = playerObject.loadImage("/Images/spaceBackgroundLvl1.jpg");
            this.setVisible(false);
        	this.dispose();
        	
        	
        }
        
        
}