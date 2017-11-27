package proiect.logic;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
	public static final int PAUSE = 1000;

	private AtomicInteger buget;
	private Hotel hotel;
	private final Queue<Client> coada = new LinkedList<>();
	private transient boolean running;
	private transient GameStateCallbacks callbacks;
	private transient ScheduledExecutorService service;
	// TODO: de utilizat 2 lockuri in loc de unul,
	// un lock pentru coada de clienti si un lock pentru buget
	private transient Lock lock = new ReentrantLock();

	public GameState() {
		// intreaba daca se vrea joc nou sau se incarca din fisier
		// daca se vrea joc nou, initializam cu valori noi
		buget = new AtomicInteger(1_000_000);
		hotel = new Hotel();
	}

	public void setCallbacks(GameStateCallbacks callbacks) {
		this.callbacks = callbacks;
	}

	public void start() {
		running = true;

		service = Executors.newScheduledThreadPool(2);

		// service.execute(() -> {
		// while (running) {
		// try {
		// Thread.sleep(PAUSE);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// calculeazaZiua();
		// }
		// });

		service.scheduleAtFixedRate(() -> {
			if (running) {
				// lock.lock();
				// {
				calculeazaZiua();
				// }
				// lock.unlock();
			}
		}, 0, PAUSE, TimeUnit.MILLISECONDS);

		service.scheduleAtFixedRate(() -> {
			if (running) {
				// lock.lock();
				// {
				genereazaClient();
				// }
				// lock.unlock();
			}
		}, 0, PAUSE / 3, TimeUnit.MILLISECONDS);

	}

	public void cumparaCamere0(TipCamera tip) {
		if (tip == null) {
			System.err.println("nu e bine, mah...");
			return;
		}
		int costCamera = tip.getPret();
		lock.lock();
		{
			if (buget.get() >= costCamera) {
				buget.addAndGet(-costCamera);
				hotel.adaugaCamera(new Camera(tip));
			} else {
				// TODO: trimite afisarea catre UI
				System.err.println("Fonduri insuficiente...");
			}
		}
		lock.unlock();
	}

	public void proceseazaClient() {
		System.err.println("urmeaza sa procesam un client si avem la coada " + coada.size());
		// luam clientul de la coada si il bagam in camera
		if (coada.peek() == null) {
			return;
		}
		System.err.println("clientul vrea o camera de " + coada.peek().getNrAnimale());
		lock.lock();
		{
			int nrLocuriCerute = coada.peek().getNrAnimale();
			Camera camera = hotel.getCameraDisponibila(nrLocuriCerute);
			if (camera == null) {
				callbacks.proceseazaMeniuAdaugaClientFaraCamera();
				return;
			}
			camera.setClient(coada.poll());
		}
		lock.unlock();
	}

	public void trimiteClientAcasa() {
		lock.lock();
		{
			coada.poll();
			System.out.println("Mai ai la coada " + coada.size());
		}
		lock.unlock();
	}

	public void cumparaCameraSiCazeazaClient() {
		// alternativ dau return la camera cumaparata
		cumparaCamere0(callbacks.getTipCamera());
		lock.lock();
		{
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
		lock.unlock();
	}

	private void calculeazaZiua() {
		int deIncasat = hotel.getIncasari();
		int dePlatit = hotel.getIntretinere();
		hotel.verificaClienti();
		buget.addAndGet(deIncasat - dePlatit);
		if (callbacks != null) {
			callbacks.afisareInformatii();
		}
	}

	public void genereazaClient() {
		// am 10% sanse sa imi dea un client
		lock.lock();
		{
			if (Util.isNextClientAvailable()) {
				Client c = new Client("Ghita", 2);
				coada.offer(c);
				System.out.println("Avem inca un client, si o coada de " + coada.size());
			}
		}
		lock.unlock();
	}

	public int getNrCamere() {
		return hotel.getNrCamere();
	}

	public int getNrCamereGoale() {
		return hotel.getNrCamereGoale();
	}

	public int getBuget() {
		return buget.get();
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
