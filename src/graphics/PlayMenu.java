package graphics;

import game.GameBoard;
import game.Graph;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PlayMenu implements Menu{
	private static Menu instance=null;
	
	private JPanel playMenuPanel;
	private JButton back,play,human,bot,size1,size2,size3,custom;
	private JTextField player1name, player2name;
	private JFormattedTextField boardW, boardH;
	private JCheckBox initials;
	
	private MenuBasic base;
	
	private boolean botActive=false;
	private boolean showInitials=false;
	private boolean customSize=false;
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
		this.playMenuPanel.add(initials);
	}
	@Override
	public JPanel getPanel() {
		return this.playMenuPanel;
	}
	private void setUpPlay(){
		play = Button(Paths.BUTTON_START);
		play.setLocation(475,573);
		play.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){
			if(customSize){
				Graph.setWidth(Integer.parseInt(boardW.getText()));
				Graph.setHeight(Integer.parseInt(boardH.getText()));
			}
			new GameBoard();
			Graph.setPlayer1Name(player1name.getText());
			Graph.setPlayer2Name(player2name.getText());
			Graph.setActivateRandom(botActive);
			System.out.println(customSize);
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
		
		initials= new JCheckBox();
		initials.setSize(30, 30);
		initials.setLocation(178,302);
		initials.setOpaque(false);
		initials.addItemListener(new ItemListener() {public void itemStateChanged(ItemEvent e) {showInitials=!showInitials;
		Graph.setInitials(showInitials);}});
	}

	private void setUpBoard(){
		size1=Button(Paths.BUTTON_SIZE1_SELECTED);
		size2=Button(Paths.BUTTON_SIZE2);
		size3=Button(Paths.BUTTON_SIZE3);
		custom=Button(Paths.BUTTON_CUSTOM);
		
		size1.setLocation(164,339);
		size2.setLocation(323,339);
		size3.setLocation(475,339);
		custom.setLocation(164,389);
		
		boardW= new JFormattedTextField(new NumberFormatter(new DecimalFormat("##;")));
		boardH= new JFormattedTextField(new NumberFormatter(new DecimalFormat("##;")));
		boardW.setValue(5);
		boardH.setValue(5);
		
		boardW.setLocation(365, 400);
		boardW.setSize(40, 40);
		boardW.setEditable(false);
		
		boardH.setLocation(440, 400);
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
			customSize=false;
			Graph.setHeight(3);
			Graph.setWidth(3);
			break;
		case 2:
			setIcon(size2, Paths.BUTTON_SIZE2);
			customSize=false;
			Graph.setHeight(4);
			Graph.setWidth(4);
			break;
		case 3:
			setIcon(size3, Paths.BUTTON_SIZE3);
			customSize=false;
			Graph.setHeight(5);
			Graph.setWidth(5);
			break;
		case 4:
			setIcon(custom, Paths.BUTTON_CUSTOM);
			customSize=true;
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
			customSize=true;
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
