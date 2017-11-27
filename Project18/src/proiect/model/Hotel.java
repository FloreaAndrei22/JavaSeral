package proiect.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class Hotel implements Serializable {

	private static final long serialVersionUID = 6584521582029407332L;
	private final Set<Camera> camere;

	public Hotel() {
		// TODO: vedem daca ne ajuta sa retinem si ordinea in care le-am bagat
		camere = new LinkedHashSet<>();
	}

	public int getNrCamere() {
		return camere.size();
	}

	public void adaugaCamera(Camera c) {
		synchronized (this) {
			camere.add(c);
		}
	}

	public synchronized Camera getCameraDisponibila(int nrLocuri) {
		for (Camera c : camere) {
			if (c.isEmpty() && nrLocuri == c.getNrLocuri()) {
				return c;
			}
		}
		return null;
	}

	public synchronized int getIntretinere() {
		int suma = 0;
		for (Camera c : camere) {
			suma += c.getIntretinerePerZi();
		}
		return suma;
	}

	public synchronized int getIncasari() {
		int suma = 0;
		for (Camera c : camere) {
			if (!c.isEmpty()) {
				suma += c.getIncasarePerZi();
			}
		}
		return suma;
	}

	public synchronized void verificaClienti() {
		for (Camera c : camere) {
			c.verificaClient();
		}
	}

	public synchronized int getNrCamereGoale() {
		int total = 0;
		for (Camera c : camere) {
			if (c.isEmpty()) {
				total++;
			}
		}
		return total;
	}

	@Override
	public String toString() {
		return "Hotel [camere=" + camere + "]";
	}

}
