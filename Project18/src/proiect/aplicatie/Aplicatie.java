package proiect.aplicatie;

import javax.swing.SwingUtilities;

import proiect.gui.GameGrafic;
import proiect.logic.GameState;

public class Aplicatie {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				// Scanner sc = new Scanner(System.in);
				// System.out.println("Vrei un joc nou sau sa incarci joc din
				// fisier? (1 sau 2)");
				// int opt = sc.nextInt();
				GameGrafic game = null;
				GameState state = new GameState();
				// if (opt == 2) {
				// game = SaveGameOptions.load();
				// } else {
				game = new GameGrafic(state);
				// }

				if (game != null) {
					game.start();
				}

			}
		});
	}

}
