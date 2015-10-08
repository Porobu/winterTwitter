package isad.winteriscoming.frontend;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menua extends JMenuBar {

	private static final long serialVersionUID = 5223706927915502156L;
	private JMenu jaitsi, bilatu, esportatu, segurtasunKopia;
	private JMenuItem txioak, bertxioak, gustokoak, jarraituak, jarraitzaileak, zerrendak, mezuak, txioakBilatu,
			bertxioakBilatu, gustukoakBilatu, zerrendakBilatu, mezuakBilatu, excel, kopiaEgin, kopiaKargatu;

	public Menua() {
		this.jaitsiMenua();
		this.bilatuMenua();
		this.esportatuMenua();
		this.segurtasunKopiaMenua();
	}

	private void jaitsiMenua() {
		//TODO ActionListenerrak inplementatuta daudenean gehituko dira.
		this.jaitsi = new JMenu("Jaitsi");
		this.txioak = new JMenuItem("Txioak");
		this.bertxioak = new JMenuItem("Bertxioak");
		this.gustokoak = new JMenuItem("Gustokoak");
		this.jarraituak = new JMenuItem("Jarraituak");
		this.jarraitzaileak = new JMenuItem("Jarraitzaileak");
		this.zerrendak = new JMenuItem("Zerrendak");
		this.mezuak = new JMenuItem("Mezuak");
		this.jaitsi.add(this.jaitsi);
		this.jaitsi.add(this.txioak);
		this.jaitsi.add(this.bertxioak);
		this.jaitsi.add(this.gustokoak);
		this.jaitsi.add(this.jarraituak);
		this.jaitsi.add(this.jarraitzaileak);
		this.jaitsi.add(this.zerrendak);
		this.jaitsi.add(this.mezuak);
	}

	private void bilatuMenua() {

	}

	private void esportatuMenua() {

	}

	private void segurtasunKopiaMenua() {

	}

}
