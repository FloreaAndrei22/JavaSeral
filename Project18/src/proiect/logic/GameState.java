package proiect.logic;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import proiect.Util;
import proiect.model.Camera;
import proiect.model.Client;
import proiect.model.Hotel;
import proiect.model.TipCamera;

// retine starea jocului si logica lui
public class GameState implements Serializable {

	public static interface GameStateCallbacks {
		void afisareInformatii();

		void proceseazaMeniuAdaugaClientFaraCamera();

		TipCamera getTipCamera();
	}

	private static final long serialVersionUID = 1759976492786431734L;

	// FPS
	public static final int TIME_UNIT = 1000;

	private int buget;
	private Hotel hotel;
	private final Queue<Client> coada = new LinkedList<>();
	private transient boolean running;
	private transient GameStateCallbacks callbacks;

	public GameState() {
		// intreaba daca se vrea joc nou sau se incarca din fisier
		// daca se vrea joc nou, initializam cu valori noi
		buget = 10000000;
		hotel = new Hotel();
	}

	public void setCallbacks(GameStateCallbacks callbacks) {
		this.callbacks = callbacks;
	}

	public void start() {
		running = true;

		new Thread(() -> {
			while (running) {
				try {
					Thread.sleep(TIME_UNIT);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				calculeazaZiua();
			}
		}).start();

		// thread care se ocupa cu clienti
		new Thread(() -> {
			while (running) {
				try {
					Thread.sleep(TIME_UNIT / 3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				genereazaClient();
			}
		}).start();

	}

	public void cumparaCamere0(TipCamera tip) {
		if (tip == null) {
			System.err.println("nu e bine, mah...");
			return;
		}
		int costCamera = tip.getPret();
		if (buget >= costCamera) {
			buget -= costCamera;
			hotel.adaugaCamera(new Camera(tip));
		} else {
			// TODO: trimite afisarea catre UI
			System.err.println("Fonduri insuficiente...");
		}
	}

	public void proceseazaClient() {
		System.err.println("urmeaza sa procesam un client si avem la coada " + coada.size());
		// luam clientul de la coada si il bagam in camera
		if (coada.peek() == null) {
			return;
		}
		System.err.println("clientul vrea o camera de " + coada.peek().getNrAnimale());
		int nrLocuriCerute = coada.peek().getNrAnimale();
		Camera camera = hotel.getCameraDisponibila(nrLocuriCerute);
		if (camera == null) {
			callbacks.proceseazaMeniuAdaugaClientFaraCamera();
			return;
		}
		camera.setClient(coada.poll());
	}

	public void trimiteClientAcasa() {
		coada.poll();
		System.out.println("Mai ai la coada " + coada.size());
	}

	public void cumparaCameraSiCazeazaClient() {
		// alternativ dau return la camera cumaparata
		cumparaCamere0(callbacks.getTipCamera());
		int nrLocuriCerute = coada.peek().getNrAnimale();
		Camera camera = hotel.getCameraDisponibila(nrLocuriCerute);
		if (camera == null) {
			coada.poll();
			System.out.println("A ESUAT CUMPARAREA CAMEREI POSIBIL DIN FONDURI INSU...");
			System.out.println("am trimis clientul acasa :(");
		} else {
			camera.setClient(coada.poll());
		}
	}

	private void calculeazaZiua() {
		int deIncasat = hotel.getIncasari();
		int dePlatit = hotel.getIntretinere();
		hotel.verificaClienti();
		buget = buget - dePlatit + deIncasat;
		if (callbacks != null) {
			callbacks.afisareInformatii();
		}
	}

	public void genereazaClient() {
		// am 10% sanse sa imi dea un client
		if (Util.isNextClientAvailable()) {
			Client c = new Client("Ghita", 2);
			coada.offer(c);
			System.out.println("Avem inca un client, si o coada de " + coada.size());
		}
	}

	public int getNrCamere() {
		return hotel.getNrCamere();
	}

	public int getNrCamereGoale() {
		return hotel.getNrCamereGoale();
	}

	public int getBuget() {
		return buget;
	}

	public int getNrClientiCoada() {
		return coada.size();
	}

	public Client getUrmatorulClient() {
		return coada.peek();
	}

	public String getstatus() {
		return hotel.toString();
	}

	public void setState(GameState load) {
		this.buget = load.buget;
		this.hotel = load.hotel;
		this.coada.clear();
		this.coada.addAll(load.coada);
	}
}
