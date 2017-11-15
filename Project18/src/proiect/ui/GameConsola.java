package proiect.ui;

import proiect.AiDatFalimentException;
import proiect.logic.GameState;
import proiect.logic.GameState.GameStateCallbacks;
import proiect.logic.SaveGameOptions;
import proiect.model.TipCamera;

// se ocupa de interactiunea cu consola
public class GameConsola {
	
	// FPS
	public static final int TIME_UNIT = 1000;

	private GameState state;
	private transient Meniu menu;
	private transient boolean running;

	public GameConsola(GameState state) {
		// intreaba daca se vrea joc nou sau se incarca din fisier
		// daca se vrea joc nou, initializam cu valori noi
		this.state = state;
		state.setCallbacks(new GameStateCallbacks() {
			
			@Override
			public void afisareInformatii() {
				afisareStatistici();
			}

			@Override
			public void proceseazaMeniuAdaugaClientFaraCamera() {
				menu.afisareOptiuniClient(new Meniu.MenuOptiuneClient() {
					@Override
					public void trimiteAcasa() {
						state.trimiteClientAcasa();
					}

					@Override
					public void skipLaCumparaCamera() {
						state.cumparaCameraSiCazeazaClient();
					}
				});
			}

			@Override
			public TipCamera getTipCamera() {
				return menu.alegeTipCamera();
			}
		});
	}

	public void start() {
		afisareStatistici();
		running = true;
		menu = new Meniu();
		System.out.println("running: " + running);

		state.start();
		new Thread(() -> {
			while (running) {
				menu.afisareMeniu(new MenuOptions() {
					@Override
					public void afisareStatistici() {
						GameConsola.this.afisareStatistici();
					}

					@Override
					public void cumparaCamere() {
						cumparaCamere0();
					}

					@Override
					public void proceseazaClient() {
						GameConsola.this.proceseazaClient();
					}

					@Override
					public void salveazaJoc() {
						SaveGameOptions.save(state);
					}
				});
			}
		}).start();

	}

	// TODO: trimite syso pe partea UI
	protected void afisareStatistici() {
		System.out.println("=================================");
		System.out.println("====Ai in hotel " + state.getNrCamere() + " camere");
		System.out.println("====Din care camere goale: " + state.getNrCamereGoale());
		System.out.println("====Ai in banca " + state.getBuget() + " bani");
		System.out.println("=================================");
		if (state.getBuget() < 0) {
			// FIXME: inca ruleaza threadul de UI care asteapta citirea
			running = false;
			menu.dispose();
			throw new AiDatFalimentException("nu mai sunt bani... pleaca acasa!");
		}
	}

	private void cumparaCamere0() {
		state.cumparaCamere0(menu.alegeTipCamera());
	}

	private void proceseazaClient() {
		state.proceseazaClient();
	}

}
