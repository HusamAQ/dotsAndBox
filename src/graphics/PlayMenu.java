package graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayMenu implements Menu{
	private static Menu instance=null;
	
	private JPanel playMenuPanel;
	private JButton back,play,human,bot,size1,size2,size3,custom;
	private JTextField player1name, player2name;
	private JFormattedTextField boardW, boardH;
	
	private MenuBasic base;
	
	private boolean botActive=false;
	private int size=1;


	private PlayMenu() {
		playMenuPanel=new Background(Paths.BACKGROUND_PLAY);
		playMenuPanel.setLayout(null);

		back=Menu.backButton();

		playMenuPanel.add(back);
		setUpButtons();
	}

	private void setUpButtons(){
		setUpPlay();
		setUpPlayer();
		setUpBoard();
		this.playMenuPanel.add(play);
		this.playMenuPanel.add(human);
		this.playMenuPanel.add(bot);
		this.playMenuPanel.add(size1);
		this.playMenuPanel.add(size2);
		this.playMenuPanel.add(size3);
		this.playMenuPanel.add(custom);
		this.playMenuPanel.add(player1name);
		this.playMenuPanel.add(player2name);
		this.playMenuPanel.add(boardW);
		this.playMenuPanel.add(boardH);
	}
	@Override
	public JPanel getPanel() {
		return this.playMenuPanel;
	}
	private void setUpPlay(){
		play = Button(Paths.BUTTON_START);
		play.setLocation(475,573);
		play.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){
			GameMenu game= new GameMenu(base,player1name.getText(),player2name.getText());
			base.setVisiblePanel(game.getPanel());
		}});
	}

	private void setUpPlayer(){
		human=Button(Paths.BUTTON_HUMAN_SELECTED);
		bot=Button(Paths.BUTTON_BOT);
		
		bot.setLocation(336,128);
		human.setLocation(164,128);
		bot.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){
			botActive=true;
			setIcon(bot, Paths.BUTTON_BOT_SELECTED);
			setIcon(human, Paths.BUTTON_HUMAN);
			player2name.setEditable(false);
			player2name.setText("RandomBot");
		}});
		human.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){
			botActive=false;
			setIcon(bot, Paths.BUTTON_BOT);
			setIcon(human, Paths.BUTTON_HUMAN_SELECTED);
			player2name.setEditable(true);
			player2name.setText("Player 2");
		}});
		
		player1name=new JTextField("Player 1");
		player2name=new JTextField("Player 2");
		
		player1name.setSize(Paths.BUTTONS_WIDTH_PLAY,30);
		player2name.setSize(Paths.BUTTONS_WIDTH_PLAY,30);
		
		player1name.setLocation(178,202);
		player2name.setLocation(178,255);
	}

	private void setUpBoard(){
		size1=Button(Paths.BUTTON_SIZE1_SELECTED);
		size2=Button(Paths.BUTTON_SIZE2);
		size3=Button(Paths.BUTTON_SIZE3);
		custom=Button(Paths.BUTTON_CUSTOM);
		
		size1.setLocation(164,329);
		size2.setLocation(323,329);
		size3.setLocation(475,329);
		custom.setLocation(164,379);
		
		boardW= new JFormattedTextField("5");
		boardH= new JFormattedTextField("5");
		
		boardW.setLocation(365, 390);
		boardW.setSize(40, 40);
		boardW.setEditable(false);
		
		boardH.setLocation(440, 390);
		boardH.setSize(40, 40);
		boardH.setEditable(false);
		
		
		size1.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){setActiveSize(1);}});
		size2.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){setActiveSize(2);}});
		size3.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){setActiveSize(3);}});
		custom.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){setActiveSize(4);}});
	}
	
	private JButton Button(String path) {
		ImageIcon icon = new ImageIcon(path);
		JButton button=new JButton(icon);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setSize(Paths.BUTTONS_WIDTH_PLAY,Paths.BUTTONS_HEIGHT_PLAY);
		return button;
	}
	
	private void setActiveSize(int s) {
		if(s==size) return;
		switch (size){
		case 1:
			setIcon(size1, Paths.BUTTON_SIZE1);
			break;
		case 2:
			setIcon(size2, Paths.BUTTON_SIZE2);
			break;
		case 3:
			setIcon(size3, Paths.BUTTON_SIZE3);
			break;
		case 4:
			setIcon(custom, Paths.BUTTON_CUSTOM);
			boardW.setEditable(false);
			boardH.setEditable(false);
			break;
		}
		
		switch (s){
		case 1:
			setIcon(size1, Paths.BUTTON_SIZE1_SELECTED);
			break;
		case 2:
			setIcon(size2, Paths.BUTTON_SIZE2_SELECTED);
			break;
		case 3:
			setIcon(size3, Paths.BUTTON_SIZE3_SELECTED);
			break;
		case 4:
			setIcon(custom, Paths.BUTTON_CUSTOM_SELECTED);
			boardW.setEditable(true);
			boardH.setEditable(true);
			break;
		}
		size=s;
	}
	
	private void setIcon(JButton button, String path) {
		ImageIcon icon = new ImageIcon(path);
		button.setIcon(icon);
	}
	
	public static Menu getInstance() {
		if(instance==null) instance=new PlayMenu();
		return instance;
	}

	public void setUpActionListeners(MenuBasic base,Menu Main) {
		this.base=base;
	    Menu.setNavigationTo(base, this.back, Main);
	}
}
