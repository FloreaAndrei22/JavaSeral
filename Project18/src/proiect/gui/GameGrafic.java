package proiect.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import proiect.logic.GameState;
import proiect.logic.GameState.GameStateCallbacks;
import proiect.logic.SaveGameOptions;
import proiect.model.TipCamera;

public class GameGrafic extends JFrame {

	private GameState state;

	private JButton btnBuyCameraDouble;
	private JButton btnProceseazaClient;
	private JButton btnSaveGame;
	private JButton btnLoadGame;

	private JLabel labelNrCamere;
	private JLabel labelNrCamereGoale;
	private JLabel labelBuget;
	private JLabel labelNrClientiLaCoada;
	private JLabel labelUrmatorulClient;

	private JLabel labelStatusCamere;

	public GameGrafic(GameState state) {
		this.state = state;
		state.setCallbacks(new GameStateCallbacks() {

			@Override
			public void proceseazaMeniuAdaugaClientFaraCamera() {
				// TODO Auto-generated method stub

			}

			@Override
			public TipCamera getTipCamera() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void afisareInformatii() {
				if (state == null) {
					return;
				}
				if (labelNrCamere != null) {
					labelNrCamere.setText("nr camere: " + state.getNrCamere());
				}
				if (labelNrCamereGoale != null) {
					labelNrCamereGoale.setText("nr camere goale: " + state.getNrCamereGoale());
				}
				if (labelBuget != null) {
					labelBuget.setText("buget: " + state.getBuget());
				}
				if (labelNrClientiLaCoada != null) {
					labelNrClientiLaCoada.setText("nr clienti coada: " + state.getNrClientiCoada());
				}
				if (labelUrmatorulClient != null) {
					if (state.getUrmatorulClient() != null) {
						labelUrmatorulClient.setText("urmatorul client: " + state.getUrmatorulClient().toString());
					} else {
						labelUrmatorulClient.setText("nu exista inca; coada e goala...");
					}
				}
				if (labelStatusCamere != null) {
					String status = state.getstatus().replace("\n", "<br>");
					// labelStatusCamere.setText("<html><b>SALUT</b> JAVA <br>
					// CE <i>MAI</i> FACI <br>KKK</html>");
					labelStatusCamere.setText("<html>" + status + "</html>");
				}
			}
		});

		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("WELCOME TO HOTEL");
		// setLayout(null);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		btnBuyCameraDouble = new JButton("Buy Camera");
		add(btnBuyCameraDouble);
		btnProceseazaClient = new JButton("Proceseaza Client");
		add(btnProceseazaClient);
		add(Box.createRigidArea(new Dimension(5, 5)));
		btnSaveGame = new JButton("Save Game");
		add(btnSaveGame);
		btnLoadGame = new JButton("Load Game");
		add(btnLoadGame);

		add(Box.createRigidArea(new Dimension(5, 15)));
		labelNrCamere = new JLabel("nr camere: " + 0);
		add(labelNrCamere);
		labelNrCamereGoale = new JLabel("nr camere goale: " + 0);
		add(labelNrCamereGoale);
		labelBuget = new JLabel("buget: " + 0);
		add(labelBuget);
		labelNrClientiLaCoada = new JLabel("nr clienti coada: " + 0);
		add(labelNrClientiLaCoada);
		labelUrmatorulClient = new JLabel("urmatorul client: " + 0);
		add(labelUrmatorulClient);
		add(Box.createRigidArea(new Dimension(5, 15)));
		labelStatusCamere = new JLabel("STATUS: -");
		add(labelStatusCamere);

		Font labelFont = labelNrCamere.getFont();
		labelNrCamere.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
		labelNrCamereGoale.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
		labelBuget.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
		labelNrClientiLaCoada.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
		labelUrmatorulClient.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
		labelStatusCamere.setFont(new Font(labelFont.getName(), Font.PLAIN, 20));

		// TODO: variabila font pentru toate
		btnBuyCameraDouble.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
		btnProceseazaClient.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
		btnSaveGame.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
		btnLoadGame.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));

	}

	public void start() {
		state.start();

		btnBuyCameraDouble.addActionListener((actionEvent) -> {
			// TODO: radio button - verific care e selectat, fac camera in
			// functie de el
			state.cumparaCamere0(TipCamera.DOUBLE);
		});
		btnProceseazaClient.addActionListener((e) -> {
			state.proceseazaClient();
		});
		btnSaveGame.addActionListener((ActionEvent actionEvent) -> {
			SaveGameOptions.save(state);
		});
		btnLoadGame.addActionListener((ActionEvent e) -> {
			System.out.println(state.hashCode());
			// state.die(); - inchidem threaduri, punem referinte pe null
			// state = SaveGame.load();
			// state.setCallbacks(...);
			// state.start();
			state.setState(SaveGameOptions.load());
			System.out.println(state.hashCode());
		});
	}

}
