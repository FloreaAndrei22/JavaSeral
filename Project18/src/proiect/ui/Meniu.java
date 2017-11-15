package proiect.ui;

import java.util.Scanner;

import proiect.model.TipCamera;

// separam UI de logica
public class Meniu {

	private Scanner sc = new Scanner(System.in);

	// callback // listenere
	public void afisareMeniu(MenuOptions options) {
		options.afisareStatistici();
		System.err.println("Introdu 1 pentru cumparare camere" + " \n introdu 2 pentru procesare client"
				+ " \n introdu 3 pentru salvare");
		int optiune = sc.nextInt();
		switch (optiune) {
		case 1:
			options.cumparaCamere();
			break;
		case 2:
			options.proceseazaClient();
			break;
		case 3:
			options.salveazaJoc();
			break;
		default:
			System.out.println("Ai ajuns intr-un caz nefericit");
			break;
		}
	}

	public void afisareOptiuniClient(MenuOptiuneClient callback) {
		System.err.println("Alege 1 pentru a trimite clientul acasa");
		System.err.println("Alege 2 pentru a da skip si a cumpara o camera");
		int optiune = sc.nextInt();
		switch (optiune) {
		case 1:
			callback.trimiteAcasa();
			break;
		case 2:
			callback.skipLaCumparaCamera();
			break;
		default:
			break;
		}
	}

	// static implicit
	public /* static */ interface MenuOptiuneClient {
		void trimiteAcasa();

		void skipLaCumparaCamera();
	}

	public TipCamera alegeTipCamera() {
		TipCamera[] tipuri = TipCamera.values();
		System.out.println("**********************");
		System.out.println("*****INTRODU:");
		for (int i = 0; i < tipuri.length; i++) {
			System.out.println(i + ". " + tipuri[i]);
		}
		System.out.println("**********************");
		int optiune;
		try {
			optiune = sc.nextInt();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (optiune < 0 || optiune >= tipuri.length) {
			return null;
		}
		return tipuri[optiune];
	}

	// operatii de clean-up
	public void dispose() {
		// TODO: cum sa oprim scannerul
		sc.close();
	}
}
