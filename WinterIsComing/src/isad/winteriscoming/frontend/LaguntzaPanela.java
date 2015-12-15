package isad.winteriscoming.frontend;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LaguntzaPanela extends JPanel {

	private static final long serialVersionUID = -5185394871055030571L;
	private JButton itzuli;
	private JTextArea textuaArea;

	public LaguntzaPanela() {
		String jaitsiLaguntza = "Jaitsi menua: Menu honekin zure twittereko datu guztiak jaitsi ditzakezu. Twitterren API limiteak direla eta, datu asko ezin izango dira jaitsi. Hau gertatzen bada programak mezu bat pantailaratuko du, eta zenbat denbora itxaron beharko duzun ezango dizu. Menu hau ezin izango da erabili Twitteren kautotu arte.";
		String konektatuLaguntza = " Konektatu menua: Menu honekin Twitterera konektatu zaitezke. Konektatu tokenekin aukera gaituta egongo da bezte baten konektatu bazara eta PIN zenbakia sartzeko lehioan PIN gorde aukera desgaitu ez baduzu. Aukera honekin azkar konektatu zaitezke berriro Twitterrrera. Konektatu aukera erabiliz Twitterrera konektatuko zara, eta bertan agertzen den zenbakia aplikazio honetara sartu beharko duzu.";
		String bistaratuLaguntza = " Bistaratu menua: Bistaratu menuaren aukerekin zure ordenagailuan gordetako txioak, bertxioak... ikusiko dituzu. Hauek aplikazioan bertan bistaratuko dira. Hemen bistaratzen diren datuak zure ordenagailuan jaitsitakoak dira.";
		String esportatuLaguntza = " Esportatu menua: Menu honen aukerekin jaitsitako datu guztiak Excel fitxategi batean kopiatuko dira. Programa honek xls fitxategiak (Excel bertsio zaharrentzako) eta xlsx fitxategiak (Excel bertsio berrientzako) sortzen ditu.";
		String segurtasunKopiaLaguntza = " Segurtasun Kopia menua: Menu honekin datu basearen, hau da, zuk jaitsitako informazio guztiaren segurtasun kopia bat egingo da.";
		this.setLayout(new BorderLayout());
		this.itzuli = new JButton("Itzuli");
		this.itzuli.addActionListener(gureAE -> WinterTwitter.getOraingoWT().getPanela().nagusiaIpini());
		this.add(itzuli, BorderLayout.PAGE_END);
		this.textuaArea = new JTextArea(jaitsiLaguntza + konektatuLaguntza + bistaratuLaguntza + esportatuLaguntza + segurtasunKopiaLaguntza);
		textuaArea.setLineWrap(true);
		textuaArea.setWrapStyleWord(true);
		this.textuaArea.setEditable(false);
		JScrollPane sp = new JScrollPane(textuaArea);
		this.add(sp, BorderLayout.CENTER);
		this.setOpaque(true);
	}
}