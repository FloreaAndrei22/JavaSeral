package proiect.model;

import java.io.Serializable;

import proiect.Util;

public class Client implements Serializable {

	private static final long serialVersionUID = 6854193627076987524L;
	// TODO: lista de animale
	// TODO: ceva sa diferentieze clientii
	private final String nume;
	private final int nrAnimale;
	private int nrDays;

	public Client(String nume, int nrAnimale) {
		this.nume = nume;
		this.nrAnimale = nrAnimale;
		nrDays = Util.getRandomIntBetween(2, 10);
	}

	public String getNume() {
		return nume;
	}

	public int getNrAnimale() {
		return nrAnimale;
	}

	@Override
	public String toString() {
		return "Client [nume=" + nume + ", nrAnimale=" + nrAnimale + ", nrDays=" + nrDays + "]";
	}

	public int getNrDays() {
		return nrDays;
	}

	public void decrementNrDays() {
		nrDays--;
	}

}
