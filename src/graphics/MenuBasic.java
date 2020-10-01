package graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuBasic {
	private JFrame frame;
	private JPanel panel;
	
	private MainMenu main;
	private PlayMenu play;
	private Rules rules;
	private Credits credits;
	
	public MenuBasic() {
		setUpMenus();
		
		this.frame=new JFrame();
		frame.setTitle("Dots & Boxes");
		frame.setSize(Paths.FRAME_WIDTH, Paths.FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		panel=main.getPanel();
		frame.add(panel);
		
		ImageIcon img = new ImageIcon(Paths.TOP_ICON);
		frame.setIconImage(img.getImage());
		
		frame.setVisible(true);
	}
	
	public void setVisiblePanel(JPanel newPanel) {
		frame.remove(panel);
		this.panel=newPanel;
		frame.add(panel);
		refreshFrame();
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public void refreshFrame() {
		frame.setVisible(false);
		frame.setVisible(true);
	}
	
	private void setUpMenus() {
		main=(MainMenu) MainMenu.getInstance();
		play=(PlayMenu) PlayMenu.getInstance();
		rules=(Rules) Rules.getInstance();
		credits=(Credits) Credits.getInstance();
		
		
		
		main.setUpActionListeners(this, play, rules, credits);
		play.setUpActionListeners(this, main);
		rules.setUpActionListeners(this, main);
		credits.setUpActionListeners(this, main);
	}
}
