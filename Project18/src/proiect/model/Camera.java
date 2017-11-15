package proiect.model;

import java.io.Serializable;

// TODO: camerele intra intr-un hash set!!!
// TODO: nr de camera!
public class Camera implements Serializable {

	private static final long serialVersionUID = 6271429926039889199L;
	private TipCamera tip;
	private Client client;

	public Camera(TipCamera tip) {
		super();
		this.tip = tip;
	}

	public boolean isEmpty() {
		return client == null;
		// if (client == null) return true; else return false;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public int getNrLocuri() {
		return tip.getNrLocuri();
	}

	public Client getClient() {
		return client;
	}

	public int getIncasarePerZi() {
		return tip.getIncasarePerZi();
	}

	public int getIntretinerePerZi() {
		return tip.getIntretinerePerZi();
	}

	public void verificaClient() {
		if (client != null) {
			if (client.getNrDays() == 0) {
				client = null;
			} else {
				client.decrementNrDays();
			}
		}
	}

	@Override
	public String toString() {
		return "Camera [tip=" + tip + ", client=" + client + "]\n";
	}

}
