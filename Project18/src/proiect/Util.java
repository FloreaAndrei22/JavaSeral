package proiect;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

	private static Random rand = new Random();

	public static int getRandomIntBetween(int lower, int upper) {
		// return rand.nextInt(upper - lower) + lower;
		return ThreadLocalRandom.current().nextInt(lower, upper + 1);
	}
	
	public static boolean isNextClientAvailable() {
		return rand.nextInt(10) == 0;
	}

}
