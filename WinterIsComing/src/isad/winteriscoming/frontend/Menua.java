package isad.winteriscoming.frontend;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import isad.winteriscoming.backend.Konexioa;
import isad.winteriscoming.backend.OperazioakOffline;
import isad.winteriscoming.backend.OperazioakOnline;

public class Menua extends JMenuBar {

	private static final long serialVersionUID = 5223706927915502156L;
	private static JMenuItem konektatu;
	private static JMenuItem deskonektatu;
	private static JMenuItem konektatuTokenekin;
	private static JMenuItem txioak;
	private static JMenuItem gustokoak;
	private static JMenuItem aipamenak;
	private static JMenuItem jarraituak;
	private static JMenuItem jarraitzaileak;
	private static JMenuItem zerrendak;
	private static JMenuItem mezuak;
	public static void botoiakHasieranEtaDeskonektatzean() {
		konektatu.setEnabled(true);
		OperazioakOffline off = new OperazioakOffline();
		konektatuTokenekin.setEnabled(off.konprobatuTokenakDauden());
		deskonektatu.setEnabled(false);
		txioak.setEnabled(false);
		gustokoak.setEnabled(false);
		aipamenak.setEnabled(false);
		jarraituak.setEnabled(false);
		jarraitzaileak.setEnabled(false);
		zerrendak.setEnabled(false);
		mezuak.setEnabled(false);
	}
	public static void botoiakKonektatzean() {
		konektatu.setEnabled(false);
		konektatuTokenekin.setEnabled(false);
		deskonektatu.setEnabled(true);
		txioak.setEnabled(true);
		gustokoak.setEnabled(true);
		aipamenak.setEnabled(true);
		jarraituak.setEnabled(true);
		jarraitzaileak.setEnabled(true);
		zerrendak.setEnabled(true);
		mezuak.setEnabled(true);
	}
	private JMenu konexioa, jaitsi, bistaratu, esportatu, segurtasunKopia, laguntza;
	private JMenuItem txioakBilatu;
	private JMenuItem bertxioakBilatu;
	private JMenuItem gustukoakBilatu;
	private JMenuItem aipamenakBilatu;
	private JMenuItem jarraituakBistaratu;
	private JMenuItem jarraitzaileakBistaratu;
	private JMenuItem zerrendakBilatu;
	private JMenuItem mezuakBilatu;
	private JMenuItem excel2007;
	private JMenuItem excel2003;
	private JMenuItem kopiaEgin;
	private JMenuItem laguntzaAukera;

	private JMenuItem webOrria;

	private JMenuItem wtBuruz;

	public Menua() {
		this.jaitsiMenua();
		this.konexioaMenua();
		this.bistaratuMenua();
		this.esportatuMenua();
		this.segurtasunKopiaMenua();
		try {
			this.laguntzaMenua();
		} catch (IOException | URISyntaxException e) {
		}
	}

	private void bistaratu(String mota) {
		TaulaPanela panela = new TaulaPanela(mota);
		WinterTwitter.getOraingoWT().getPanela().panelaAldatu(panela);
	}

	private void bistaratuMenua() {
		this.bistaratu = new JMenu("Bistaratu");
		this.txioakBilatu = new JMenuItem("Txioak");
		this.bertxioakBilatu = new JMenuItem("Bertxioak");
		this.gustukoakBilatu = new JMenuItem("Gustukoak");
		this.aipamenakBilatu = new JMenuItem("Aipamenak");
		this.jarraituakBistaratu = new JMenuItem("Jarraituak");
		this.jarraitzaileakBistaratu = new JMenuItem("Jarraitzaileak");
		this.zerrendakBilatu = new JMenuItem("Zerrendak");
		this.mezuakBilatu = new JMenuItem("Mezuak");
		this.txioakBilatu.addActionListener(gureAE -> this.bistaratu("txioa"));
		this.bertxioakBilatu.addActionListener(gureAE -> this.bistaratu("bertxioa"));
		this.gustukoakBilatu.addActionListener(gureAE -> this.bistaratu("gustokoa"));
		this.aipamenakBilatu.addActionListener(gureAE -> this.bistaratu("aipamena"));
		this.jarraituakBistaratu.addActionListener(gureAE -> this.bistaratu("jarraitua"));
		this.jarraitzaileakBistaratu.addActionListener(gureAE -> this.bistaratu("jarraitzailea"));
		this.zerrendakBilatu.addActionListener(gureAE -> this.bistaratu("zerrenda"));
		this.mezuakBilatu.addActionListener(gureAE -> this.bistaratu("mezua"));
		this.bistaratu.add(this.txioakBilatu);
		this.bistaratu.add(this.bertxioakBilatu);
		this.bistaratu.add(this.gustukoakBilatu);
		this.bistaratu.add(this.aipamenakBilatu);
		this.bistaratu.add(jarraituakBistaratu);
		this.bistaratu.add(jarraitzaileakBistaratu);
		this.bistaratu.add(this.zerrendakBilatu);
		this.bistaratu.add(this.mezuakBilatu);
		this.add(this.bistaratu);
	}

	private void esportatuMenua() {
		this.esportatu = new JMenu("Esportatu");
		this.excel2007 = new JMenuItem("Excel 2007");
		this.excel2003 = new JMenuItem("Excel 2003");
		EsportatuExcelUI gureExcel = new EsportatuExcelUI();
		this.excel2007.addActionListener(gureAE -> gureExcel.gorde(2007));
		this.excel2003.addActionListener(gureAE -> gureExcel.gorde(2003));
		this.esportatu.add(this.excel2003);
		this.esportatu.add(this.excel2007);
		this.add(this.esportatu);
	}

	private void jaitsiMenua() {
		this.jaitsi = new JMenu("Jaitsi");
		txioak = new JMenuItem("Txioak");
		gustokoak = new JMenuItem("Gustokoak");
		aipamenak = new JMenuItem("Aipamenak");
		jarraituak = new JMenuItem("Jarraituak");
		jarraitzaileak = new JMenuItem("Jarraitzaileak");
		zerrendak = new JMenuItem("Zerrendak");
		mezuak = new JMenuItem("Mezuak");
		this.jaitsi.add(this.jaitsi);
		jaitsi.add(txioak);
		txioak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().txioakJaitsi());
		jaitsi.add(gustokoak);
		gustokoak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().gustokoakJaitsi());
		jaitsi.add(jarraituak);
		aipamenak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().aipamenakJaitsi());
		jaitsi.add(aipamenak);
		jarraituak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().jarraituakJaitsi());
		jaitsi.add(jarraitzaileak);
		jarraitzaileak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().jarraitzaileakJaitsi());
		jaitsi.add(zerrendak);
		zerrendak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().zerrendakJaitsi());
		jaitsi.add(mezuak);
		mezuak.addActionListener(gureAE -> OperazioakOnline.getOperazioak().mezuakJaitsi());
		this.add(this.jaitsi);
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
		Menua.botoiakHasieranEtaDeskonektatzean();
	}

	private void laguntzaMenua() throws IOException, URISyntaxException {
		this.laguntza = new JMenu("Laguntza");
		this.laguntzaAukera = new JMenuItem("Laguntza");
		this.webOrria = new JMenuItem("WinterTwitter-en webgunea");
		this.webOrria.addActionListener(gureAE -> this.nabigatzaileaIreki());
		this.wtBuruz = new JMenuItem("WinterTwitter-eri buruz");
		this.wtBuruz.addActionListener(gureAE -> WinterTwitter.getOraingoWT().getPanela().panelaAldatu(new WTBuruz()));
		this.laguntza.add(laguntzaAukera);
		this.laguntza.add(webOrria);
		this.laguntza.add(wtBuruz);
		this.add(laguntza);
	}

	private void nabigatzaileaIreki() {
		try {
			Desktop.getDesktop().browse(new URI("https://github.com/UPV-EHU-Bilbao/winterTwitter"));
		} catch (IOException | URISyntaxException e) {
		}
	}

	private void segurtasunKopiaMenua() {
		this.segurtasunKopia = new JMenu("Segurtasun Kopia");
		this.kopiaEgin = new JMenuItem("Kopia Egin");
		FitxategiOperazioakUI gureFOUI = new FitxategiOperazioakUI();
		this.kopiaEgin.addActionListener(gureAE -> gureFOUI.kopiaEgin());
		this.segurtasunKopia.add(this.kopiaEgin);
		this.add(this.segurtasunKopia);
	}
}