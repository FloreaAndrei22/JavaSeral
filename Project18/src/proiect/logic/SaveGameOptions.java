package proiect.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveGameOptions {
	private static final String SAVEGAME_FILENAME = "savefile.sv";

	public static void save(GameState game) {
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File(SAVEGAME_FILENAME)));) {
			os.writeObject(game);
			os.flush(); // ajutam la fortarea scrierii
		} catch (IOException e) {
			// nu avem spatiu pe disc
			// nu avem acces pe disc
			System.out.println("NU AM REUSIT SA SALVEZ DIN DIVERSE SI VARII MOTIVE");
			e.printStackTrace();
		}

		System.out.println("AI SALVAT YEY !!!");
	}

	public static GameState load() {
		try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File(SAVEGAME_FILENAME)));) {
			System.out.println("AM REUSIT SA INCARCAM CEVA....");
			// Game game = (Game) is.readObject();
			// return game;
			return (GameState) is.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("ERORI LA INCARCAREA JOCULUI DIN FISIER");
			e.printStackTrace();
		}

		return null;
	}

}
