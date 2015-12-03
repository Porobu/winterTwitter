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
	private static JMenuItem konektatu;
	private static JMenuItem deskonektatu;
	private static JMenuItem konektatuTokenekin;
	private JMenuItem txioak;
	private JMenuItem bertxioak;
	private JMenuItem gustokoak;
	private JMenuItem aipamenak;
	private JMenuItem jarraituak;
	private JMenuItem jarraitzaileak;
	private JMenuItem zerrendak;
	private JMenuItem mezuak;
	private JMenuItem txioakBilatu;
	private JMenuItem bertxioakBilatu;
	private JMenuItem gustukoakBilatu;
	private JMenuItem aipamenakBilatu;
	private JMenuItem zerrendakBilatu;
	private JMenuItem mezuakBilatu;
	private JMenuItem excel;
	private JMenuItem kopiaEgin;
	private JMenuItem kopiaKargatu;

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
		botoiakHasieranEtaDeskonektatzean();
	}

	public static void botoiakHasieranEtaDeskonektatzean() {
		konektatu.setEnabled(true);
		OperazioakOffline off = new OperazioakOffline();
		konektatuTokenekin.setEnabled(off.konprobatuTokenakDauden());
		deskonektatu.setEnabled(false);
	}
	
	public static void botoiakKonektatzean() {
		konektatu.setEnabled(false);
		konektatuTokenekin.setEnabled(false);
		deskonektatu.setEnabled(true);
	}

	private void jaitsiMenua() {
		// TODO ActionListenerrak inplementatuta daudenean gehituko dira.
		this.jaitsi = new JMenu("Jaitsi");
		this.txioak = new JMenuItem("Txioak");
		this.bertxioak = new JMenuItem("Bertxioak");
		this.gustokoak = new JMenuItem("Gustokoak");
		this.aipamenak = new JMenuItem("Aipamenak");
		this.jarraituak = new JMenuItem("Jarraituak");
		this.jarraitzaileak = new JMenuItem("Jarraitzaileak");
		this.zerrendak = new JMenuItem("Zerrendak");
		this.mezuak = new JMenuItem("Mezuak");
		this.jaitsi.add(this.jaitsi);
		this.jaitsi.add(this.txioak);
		this.txioak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().txioakJaitsi());
		this.jaitsi.add(this.bertxioak);
		this.bertxioak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().bertxioakJaitsi());
		this.jaitsi.add(this.gustokoak);
		this.gustokoak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().gustokoakJaitsi());
		this.jaitsi.add(this.jarraituak);
		this.aipamenak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().aipamenakJaitsi());
		this.jaitsi.add(this.aipamenak);
		this.jarraituak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().jarraituakDeskargatu());
		this.jaitsi.add(this.jarraitzaileak);
		this.jarraitzaileak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().jarraitzaileakDeskargatu());
		this.jaitsi.add(this.zerrendak);
		//this.zerrendak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().zerrendakDeskargatu());
		this.jaitsi.add(this.mezuak);
		this.mezuak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().mezuakDeskargatu());
		this.add(this.jaitsi);
	}

	private void bilatuMenua() {
		// TODO ActionListenerrak inplementatuta daudenean gehituko dira.
		this.bilatu = new JMenu("Bilatu");
		this.txioakBilatu = new JMenuItem("Txioak");
		this.bertxioakBilatu = new JMenuItem("Bertxioak");
		this.gustukoakBilatu = new JMenuItem("Gustukoak");
		this.aipamenakBilatu = new JMenuItem("Aipamenak");
		this.zerrendakBilatu = new JMenuItem("Zerrendak");
		this.mezuakBilatu = new JMenuItem("Mezuak");
		this.bilatu.add(this.txioakBilatu);
		this.bilatu.add(this.bertxioakBilatu);
		this.bilatu.add(this.gustukoakBilatu);
		this.bilatu.add(this.aipamenakBilatu);
		this.bilatu.add(this.zerrendakBilatu);
		this.bilatu.add(this.mezuakBilatu);
		this.add(this.bilatu);
	}

	private void esportatuMenua() {
		// TODO ActionListenerrak inplementatuta daudenean gehituko dira.
		this.esportatu = new JMenu("Esportatu");
		this.excel = new JMenuItem("Excel");
		EsportatuExcelUI nireExcel = new EsportatuExcelUI();
		this.excel.addActionListener(gureAE -> nireExcel.gorde());
		this.esportatu.add(this.excel);
		this.add(this.esportatu);
	}

	private void segurtasunKopiaMenua() {
		// TODO ActionListenerrak inplementatuta daudenean gehituko dira.
		this.segurtasunKopia = new JMenu("Segurtasun Kopia");
		this.kopiaEgin = new JMenuItem("Kopia Egin");
		FitxategiOperazioakUI gureFOUI = new FitxategiOperazioakUI();
		this.kopiaEgin.addActionListener(gureAE -> gureFOUI.kopiaEgin());
		this.kopiaKargatu = new JMenuItem("Kopia Kargatu");
		this.segurtasunKopia.add(this.kopiaEgin);
		this.segurtasunKopia.add(this.kopiaKargatu);
		this.add(this.segurtasunKopia);
	}

}
