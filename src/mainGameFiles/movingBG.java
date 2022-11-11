package mainGameFiles;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class movingBG 
{
	public JPanel menuBackground()
	{
		JPanel panel = new JPanel();
		
		BoxLayout layoutMgr = new BoxLayout(panel,BoxLayout.PAGE_AXIS);
		
		panel.setLayout(layoutMgr);
		
		ClassLoader cldr = this.getClass().getClassLoader();
		
		java.net.URL imageURL = cldr.getResource("Images/GIFS/new.gif");
		ImageIcon imageIcon = new ImageIcon(imageURL);
		
		JLabel iconLabel = new JLabel();
		
		iconLabel.setIcon(imageIcon);
		imageIcon.setImageObserver(iconLabel);
		
		JLabel label = new JLabel("menu");
		panel.add(iconLabel);
		panel.add(label);
		return panel;
	}
}
