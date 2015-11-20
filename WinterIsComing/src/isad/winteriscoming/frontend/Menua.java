package isad.winteriscoming.frontend;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import isad.winteriscoming.backend.Konexioa;
import isad.winteriscoming.backend.OperazioakOffline;
import isad.winteriscoming.backend.OperazioakOnline;

public class Menua extends JMenuBar {

	private static final long serialVersionUID = 5223706927915502156L;
	private JMenu fitxategia, konexioa, jaitsi, bilatu, esportatu, segurtasunKopia;
	private JMenuItem konektatu, deskonektatu, konektatuTokenekin, txioak, bertxioak, gustokoak, jarraituak, jarraitzaileak, zerrendak,
			jasotakoMezuak, bidalitakoMezuak, txioakBilatu, bertxioakBilatu, gustukoakBilatu, zerrendakBilatu,
			mezuakBilatu, excel, kopiaEgin, kopiaKargatu;

	public Menua() {
		this.fitxategiaMenua();
		this.konexioaMenua();
		this.jaitsiMenua();
		this.bilatuMenua();
		this.esportatuMenua();
		this.segurtasunKopiaMenua();
	}

	private void fitxategiaMenua() {
		this.fitxategia = new JMenu("Fitxategia");
		// TODO MAS COSAS AQUI
		this.add(fitxategia);
	}

	private void konexioaMenua() {
		konexioa = new JMenu("Konexioa");
		konektatu = new JMenuItem("Konektatu");
		deskonektatu = new JMenuItem("Deskonektatu");
		konektatuTokenekin = new JMenuItem("Konektatu Tokenekin");
		konektatuTokenekin.addActionListener(gureAE -> Konexioa.getKonexioa().tokenarekinKonektatu());
		konexioa.add(konektatuTokenekin);
		konektatu.addActionListener(gureAE -> Konexioa.getKonexioa().logeatu());
		deskonektatu.addActionListener(gureAE -> Konexioa.getKonexioa().deskonektatu());
		konexioa.add(konektatu);
		konexioa.add(deskonektatu);
		this.add(konexioa);
		OperazioakOffline off = new OperazioakOffline();
		if (!off.konprobatuTokenakDauden())
			konektatuTokenekin.setEnabled(false);
		deskonektatu.setEnabled(false);
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
		this.jasotakoMezuak = new JMenuItem("Jasotako Mezuak");
		this.bidalitakoMezuak = new JMenuItem("Bidalitako Mezuak");
		this.jaitsi.add(this.jaitsi);
		this.jaitsi.add(this.txioak);
		this.txioak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().txioakDeskargatu());
		this.jaitsi.add(this.bertxioak);
		this.bertxioak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().bertxioakDeskargatu());
		this.jaitsi.add(this.gustokoak);
		this.gustokoak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().gustokoakDeskargatu());
		this.jaitsi.add(this.jarraituak);
		this.jarraituak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().jarraituakDeskargatu());
		this.jaitsi.add(this.jarraitzaileak);
		this.jarraitzaileak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().jarraitzaileakDeskargatu());
		this.jaitsi.add(this.zerrendak);
		this.zerrendak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().zerrendakDeskargatu());
		this.jaitsi.add(this.jasotakoMezuak);
		this.jasotakoMezuak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().jasotakoMezuakDeskargatu());
		this.jaitsi.add(this.bidalitakoMezuak);
		this.bidalitakoMezuak
				.addActionListener(gureAE -> OperazioakOnline.getOperazioak().bidalitakoMezuakDeskargatu());
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
		SegurtasunKopia gureSK = new SegurtasunKopia();
		this.kopiaEgin.addActionListener(gureAE -> gureSK.kopiaEgin());
		this.kopiaKargatu = new JMenuItem("Kopia Kargatu");
		this.segurtasunKopia.add(this.kopiaEgin);
		this.segurtasunKopia.add(this.kopiaKargatu);
		this.add(this.segurtasunKopia);
	}

}
