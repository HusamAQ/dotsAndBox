package graphics;

public class Test {
	public static void main(String[] args) {
		MenuBasic x= new MenuBasic();
		MainMenu y = new MainMenu(x.getFrame());
		x.setVisiblePanel(y.getPanel());
		//
	}
}
