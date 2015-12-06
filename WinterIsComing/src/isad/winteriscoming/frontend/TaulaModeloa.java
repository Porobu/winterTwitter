package isad.winteriscoming.frontend;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import isad.winteriscoming.backend.OperazioakOffline;

public class TaulaModeloa extends AbstractTableModel {
	private String[] izenak;
	private ArrayList<String[]> datuak;
	private static final long serialVersionUID = 5416722887047816684L;

	public TaulaModeloa(String mota) {
		mota = mota.toLowerCase();
		if (mota.equals("txioa") || mota.equals("bertxioa") || mota.equals("gustokoa")) {
			izenak = new String[] { "Edukia", "Data" };
		} else if (mota.equals("jarraitua") || mota.equals("jarraitzailea")) {
			izenak = new String[] { "Izena", "Nick" };
		} else if (mota.equals("zerrendak")) {
			izenak = new String[] { "Izena", "Deskribapena", "Erabiltzaile Izena", "Zerrenda Izena", "Nick" };
		} else {
			izenak = new String[] { "Data", "Edukia", "Bidaltzaile Izena", "Hartzaile Izena" };
		}
		OperazioakOffline gureOO = new OperazioakOffline();
		datuak = gureOO.datuakJaso(mota);
	}

	@Override
	public int getColumnCount() {
		return izenak.length;
	}

	@Override
	public int getRowCount() {
		return datuak.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		return datuak.get(row)[col];
	}

	public Class<? extends Object> getColumnClass(int c) {
		return String.class;
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}
}