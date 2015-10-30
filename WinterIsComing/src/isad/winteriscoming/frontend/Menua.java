package isad.winteriscoming.frontend;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import isad.winteriscoming.backend.Konexioa;
import isad.winteriscoming.backend.OperazioakOnline;

public class Menua extends JMenuBar {

	private static final long serialVersionUID = 5223706927915502156L;
	private JMenu konexioa, jaitsi, bilatu, esportatu, segurtasunKopia;
	private JMenuItem konektatu, deskonektatu, txioak, bertxioak, gustokoak, jarraituak, jarraitzaileak, zerrendak,
			mezuak, txioakBilatu, bertxioakBilatu, gustukoakBilatu, zerrendakBilatu, mezuakBilatu, excel, kopiaEgin,
			kopiaKargatu;

	public Menua() {
		this.konexioaMenua();
		this.jaitsiMenua();
		this.bilatuMenua();
		this.esportatuMenua();
		this.segurtasunKopiaMenua();
	}

	private void konexioaMenua() {
		konexioa = new JMenu("Konexioa");
		konektatu = new JMenuItem("Konektatu");
		deskonektatu = new JMenuItem("Deskonektatu");
		konektatu.addActionListener(gureAE -> Konexioa.getKonexioa().logeatu());
		konexioa.add(konektatu);
		konexioa.add(deskonektatu);
		this.add(konexioa);
	}

	private void jaitsiMenua() {
		// TODO ActionListenerrak inplementatuta daudenean gehituko dira.
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
		this.txioak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().tweetakErakutsi());
		this.jaitsi.add(this.bertxioak);
		this.jaitsi.add(this.gustokoak);
		this.jaitsi.add(this.jarraituak);
		this.jaitsi.add(this.jarraitzaileak);
		this.jaitsi.add(this.zerrendak);
		this.zerrendak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().zerrendakErakutsi());
		this.jaitsi.add(this.mezuak);
		this.add(this.jaitsi);
	}

	private void bilatuMenua() {
		// TODO ActionListenerrak inplementatuta daudenean gehituko dira.
		this.bilatu = new JMenu("Bilatu");
		this.txioakBilatu = new JMenuItem("Txioak");
		this.bertxioakBilatu = new JMenuItem("Bertxioak");
		this.gustukoakBilatu = new JMenuItem("Gustukoak");
		this.zerrendakBilatu = new JMenuItem("Zerrendak");
		this.mezuakBilatu = new JMenuItem("Mezuak");
		this.bilatu.add(this.txioakBilatu);
		this.bilatu.add(this.bertxioakBilatu);
		this.bilatu.add(this.gustukoakBilatu);
		this.bilatu.add(this.zerrendakBilatu);
		this.bilatu.add(this.mezuakBilatu);
		this.add(this.bilatu);
	}

	private void esportatuMenua() {
		// TODO ActionListenerrak inplementatuta daudenean gehituko dira.
		this.esportatu = new JMenu("Esportatu");
		this.excel = new JMenuItem("Excel");
		this.esportatu.add(this.excel);
		this.add(this.esportatu);
	}

	private void segurtasunKopiaMenua() {
		// TODO ActionListenerrak inplementatuta daudenean gehituko dira.
		this.segurtasunKopia = new JMenu("Segurtasun Kopia");
		this.kopiaEgin = new JMenuItem("Kopia Egin");
		this.kopiaKargatu = new JMenuItem("Kopia Kargatu");
		this.segurtasunKopia.add(this.kopiaEgin);
		this.segurtasunKopia.add(this.kopiaKargatu);
		this.add(this.segurtasunKopia);
	}

}
