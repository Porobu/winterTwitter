package isad.winteriscoming.backend;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Nagusia {

	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}
		Konexioa.getKonexioa().logeatu();
	}

}
