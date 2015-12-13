package isad.winteriscoming.backend;

//    WinterTwitter Lizentzia
//    Copyright (C) 2015  Sergio Pascual, Sergio Santana, Unai Arrizabalaga, Jonathan Nieto
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, version 3 of the License.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see http://www.gnu.org/licenses/.

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import isad.winteriscoming.frontend.AukerakUI;
import isad.winteriscoming.frontend.WinterTwitter;

public class Nagusia {
	public static final float BERTSIOA = 0.9F;
	public static final String IZENBURUA = "WinterTwitter " + Nagusia.BERTSIOA;
	private static String path;
	private static WinterTwitter wtFrame;

	public static void main(String[] args) {
		if (!System.getProperty("os.name").toLowerCase().contains("mac os x")) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
				}
			}
		}
		wtFrame = new WinterTwitter();
		wtFrame.dekoratuGabeHasieratu();
		AukerakUI nireAUI = new AukerakUI();
		path = nireAUI.hasi();
		DBKS.getDBKS().konektatu(path);
		wtFrame.dekoratu();
	}

	public static String getPath() {
		return path;
	}
}
