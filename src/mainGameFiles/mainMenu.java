package mainGameFiles;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.thehowtotutorial.splashscreen.JSplash;



public class mainMenu extends JFrame 
{   
	int songNum;
	window console = new window();
	JTextArea ta = new JTextArea(20,40);;
	public static boolean musicActivated = true;
	Font font = new Font("Courier new",Font.BOLD,15);
	Font f3 = new Font("Press Start Regular",Font.PLAIN,19);
	public boolean selection;
	public boolean drawIsTrue = true;
    boolean isRunning = true; 
    int fps = 10; 
    int windowWidth = 900; 
    int windowHeight = 500;
    
    public static boolean windowIsOpen = false;
    String line;
    public String musicStat = "Music on/off";
    private final static String GAME_VERSION = "v2.4.5 BETA";
    private String title = "Interstellar";
    private String play ="Play Game";
    
    
    
    BufferedImage bg = playerObject.loadImage("/Images/MenuImages/bg.jpg");
    BufferedImage bg1 = playerObject.loadImage("/Images/GIFS/new.gif");
    BufferedImage flare = playerObject.loadImage("/Images/MenuImages/dirtylense.png");
    int x;
    JLabel label;
    
    BufferedImage backBuffer; 
    Insets insets; 
    InputHandler input; 
    JFrame frame = new JFrame();
    
    playerNames names = new playerNames();
    static JSplash splash = new JSplash(mainMenu.class.getResource("/Images/spaceship.png"),true,true,false,GAME_VERSION,null,Color.BLACK,Color.RED);   
        
        
        public static void main(String[] args) 
        { 
        	
            splash.splashOn();
            splash.setAlwaysOnTop(true);
            
        	
            
        	mainMenu game = new mainMenu(); 
            game.run(); 
            System.exit(0); 
        } 
        
    
        public void run() 
        { 
        	
        	
                initialize(); 
                
                while(isRunning) 
                { 
                        long time = System.currentTimeMillis(); 
                         
                        update(); 
                        draw(); 
                        
                        //  delay for each frame  -   time it took for one frame 
                        time = (1000 / fps) - (System.currentTimeMillis() - time); 
                        
                        if (time > 0) 
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
        
      
        void initialize() 
        { 
        	x =1;
        	playRandomMusic();
            setTitle(title); 
            setSize(windowWidth, windowHeight);
            setIconImage(playerObject.loadImage("/Images/spaceship.png"));
            setResizable(false); 
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setAlwaysOnTop(true);
            setFocusable(true);
            setLocationRelativeTo(null);
            setUndecorated(true);
            setVisible(true);
            insets = getInsets(); 
            setSize(insets.left + windowWidth + insets.right, insets.top + windowHeight + insets.bottom); 
            
            requestFocus();
            
            backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB); 
            input = new InputHandler(this); 
            splash.setProgress(45,"loading window...");
            
        } 
        
       void playRandomMusic()
       {
    	  Random rand = new Random();
          songNum = rand.nextInt(3)+1;
          	
          	if(songNum == 1)
          	{
          		bgMusic.menuBG1.play();
          	}
          	
          	if(songNum == 2)
          	{
          		bgMusic.menuBG2.play();
          	}
          	
          	if(songNum == 3)
          	{
          		bgMusic.menuBG3.play();
          	}
       }
        
        void getText()
        {
        		URL file = mainMenu.class.getResource("");
        		
        		
        		
        		String filer = (file+"");
        		String mute = filer.replace("file:/","");
        		mute += "test.txt";
        		System.out.println(mute);
        		
        		try
        		{
        			FileReader reader = new FileReader(mute);
        			BufferedReader br = new BufferedReader(reader);
        			ta.read(br, null);
        			br.close();
        			ta.requestFocus();
        			JScrollPane sp = new JScrollPane(ta);
        			frame.setSize(550,350);
        			frame.setAlwaysOnTop(true);
            		frame.setLocationRelativeTo(this);
            		frame.setUndecorated(true);
            		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            		frame.add(sp);
            		frame.setVisible(true);
            		selection = false;
            	
        		}
				
        		catch(Exception e)
        		{
        			System.out.println("Error loading file");
        		}
				
        		while(windowIsOpen)
        		{
        			update();
        		}
        		
        }
        
        void update() 
        {
        	if (input.isKeyDown(KeyEvent.VK_DOWN)) 
            { 
        		if(x+1==6)
            	{
        			bgMusic.menu.play();
            		x=1;
            		draw();
            	}
        		
        		else
        		{
        			bgMusic.menu.play();
                	x += 1;
           		 	System.out.println(x);
           		 	draw();
        		}
            	
                    
            } 
            if (input.isKeyDown(KeyEvent.VK_UP)) 
            {
            	
            	if(x-1==0)
            	{
            		bgMusic.menu.play();
            		x = 5;
            		draw();
            	}
            	
            	else
            	{
            		bgMusic.menu.play();
                	x -= 1;
            		System.out.println(x);
            		draw(); 
            	}
            	       
            }
            
            if(input.isKeyDown(KeyEvent.VK_ENTER))
            {
            	System.out.println(x);
            	selection = true;
            	draw();
            }
            
            if(input.isKeyDown(KeyEvent.VK_ESCAPE))
            {
            	frame.dispose();
            }
            splash.setProgress(95,"Update Method loading...");
    }


		void draw() 
        {               
			
			Graphics g = getGraphics();
        	Graphics bbg = backBuffer.getGraphics();
        	
        	g.drawImage(backBuffer, insets.left, insets.top, this);
        	bbg.clearRect(0, 0, console.setWindowSizeX(), console.setWindowSizeY());
        	
        	bbg.drawImage(bg1,0,0,getWidth(),getHeight(), null); 
            
            bbg.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
            
        	
            
            bbg.setColor(Color.WHITE); 
            Font f = new Font("Slant",Font.PLAIN,80);
            bbg.setFont(f);
            bbg.drawString(title,300,80);
            Font f1 = new Font("Slant",Font.PLAIN,50);
            bbg.setFont(f1);
            
         
            
            
            if(x==1)
            {
            	bbg.setColor(Color.RED);
            	bbg.drawImage(flare, 60, 180,200,100, this);
            	bbg.drawString(play + "- who likes to be on top?", 60, 250);
            	if(selection)
            	{
            		this.setVisible(false);
            		this.dispose();
            		names.playerNames();
            		names.amountOfGames();
            		
            		
            		String[] args = {};
            		mainGame.main(args);
            		isRunning = false;
            		System.exit(0);
            	}
            }
            
            else
            {
            	
            	bbg.setColor(Color.GRAY);
            	bbg.drawString(play, 20, 250);
            }
            
            if(x==2)
            {
            	bbg.setColor(Color.RED);
            	bbg.drawImage(flare, 60, 240,200,100, this);
            	bbg.drawString("Multiplayer- play via interwebs(soon...)", 60, 300);
            	
            	
            }
            
            else
            {
            	
            	bbg.setColor(Color.GRAY);
            	bbg.drawString("Multiplayer", 20, 300);
            	
            }
            
            
            
            
            if(x==3)
            {
            	bbg.setColor(Color.RED);
            	bbg.drawImage(flare, 60, 280,200,100, this);
            	bbg.drawString(musicStat, 60, 350);
            	//bbg.drawImage(arg0, arg1, arg2, arg3, arg4, arg5)
            	
            	if(selection)
        		{
            		if(musicActivated !=true)
                	{
            			musicActivated = true;
            			playRandomMusic();
            			
            			musicStat = "Music On";
                	}
            		
            		else if(musicActivated)
                	{
            			musicActivated = false;
            			bgMusic.menuBG1.stop();
            			bgMusic.menuBG2.stop();
            			bgMusic.menuBG3.stop();
            			
            			musicStat = "Music Off";
                	}
        		}	
            }
            
            else
            {
            	
            	bbg.setColor(Color.GRAY);
            	bbg.drawString("Settings", 20, 350);
            	
            }
            
            if(x==4)
            {
            	bbg.setColor(Color.RED);
            	bbg.drawImage(flare,30,340,200,100,this);
            	bbg.drawString("About",60,400);

            	if(selection)
            	{
            		selection = false;
            		if(windowIsOpen!=true)
            		{
            			windowIsOpen = true;
            			selection = false;
            			getText();
                	}
						
            			
            		}
            	}	
            
            
            else
            {
            	windowIsOpen = false;
            	bbg.setColor(Color.GRAY);
            	bbg.drawString("About", 20, 400);
            	
            }
            
            if(x==5)
            {
            	bbg.setColor(Color.RED);
            	bbg.drawImage(flare, 10, 380,200,100, this);
            	bbg.drawString("Exit", 60, 450);
            	
            	if(selection)
            	{
            		this.setVisible(false);
            		this.dispose();
            		System.exit(0);
            	}
            }
            
            else
            {
            	
            	bbg.setColor(Color.GRAY);
            	bbg.drawString("Exit", 20, 450);
            	
            }
            bbg.setFont(font);
            bbg.drawString("Game version : "+ GAME_VERSION, 655, 475);
            bbg.drawString("This game fully supports mayonase, v3.0", 549, 490);
            selection = false;
            splash.setProgress(100,"Drawing items...");
            splash.splashOff();
            
        } 
} 