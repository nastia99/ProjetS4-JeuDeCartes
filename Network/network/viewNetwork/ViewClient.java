package network.viewnetwork;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import network.analyse.listener.TCPPrintListener;
import network.networkengine.NetworkController;
import network.networkengine.NetworkControllerClient;
import network.tcp.controller.NetworkControllerTCP;
import network.tcp.controller.NetworkControllerTCPClient;
import network.udp.controller.NetworkControllerUDP;

import javax.swing.JLabel;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class ViewClient implements Runnable, ActionListener,KeyListener, View, TCPPrintListener  {

	private JPanel contentPane;
	private JTextArea textArea;
	private JLabel lblClient;
	private Canvas canvas;
	private JLabel lblServeur ;
	private JButton btnConnexion;
	private JButton btnDeconnexion;
	private JLabel lblLatence;
	ViewClient window = this;
	private JFrame frame;
	private final ExecutorService workers = Executors.newCachedThreadPool();
	private NetworkControllerTCPClient ctcp;
	private JTextField textField;
	private JTextField textField_1;
	private NetworkControllerUDP ncudp = null;
	private NetworkControllerClient ncc;
	private Canvas canvas_1;
	private JLabel lblClientudp;
	private JLabel lblGroupemulticastNone;
	private JLabel lblPortmulticastNone;
	private JTextArea textArea_1; 
	private JTextField textField_2;
	private JLabel lblEnvoyerMessageUdp;
	private JButton btnStart;
	private JButton btnDown;
	private JLabel lbladdrIPGame;
	private JButton btnRejoindre;
	private boolean running = true;
	private JTextField textField_3;
	private JScrollPane scrollUDP;
	private JScrollPane scrollTCP;

	/**
	 * Launch the application.
	 * @param ncudp 
	 */
	
	public ViewClient(NetworkControllerUDP ncudp, NetworkControllerTCPClient c, NetworkControllerClient ncc) {
		super();
		this.ncudp = ncudp;
		this.ctcp = c;
		this.ncc = ncc;
		initialize();
		
	}
	
	@Override
	public void setController(NetworkControllerUDP networkControllerService, NetworkControllerTCP networkControllerTCP,
			NetworkController networkController) {
		this.ctcp = (NetworkControllerTCPClient) networkControllerTCP;
		this.ncudp = networkControllerService;
		this.ncc = (NetworkControllerClient) networkController;
	}
	
	@Override
	public void run() {
		
		try {
			frame.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		synchronized(ncc) {
			ncc.notifyAll();	
			try {
				ncc.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
		
		while(running ) {
			this.running();
			this.latency();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
		
	}
	
	public void setVisibleFrame(boolean b) {
		frame.setVisible(b);
	}

	/**
	 * Create the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 900, 348);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblClient = new JLabel("Client ");
		lblClient.setBounds(440, 21, 180, 14);
		contentPane.add(lblClient);
		
		canvas = new Canvas();
		canvas.setBounds(408, 21, 19, 19);
		canvas.setBackground(Color.RED);
		contentPane.add(canvas);
		
		lblServeur = new JLabel("Serveur");
		lblServeur.setBounds(408, 46, 212, 14);
		contentPane.add(lblServeur);
		
		btnConnexion = new JButton("Connexion");
		btnConnexion.setBounds(408, 127, 101, 23);
		contentPane.add(btnConnexion);
		btnConnexion.addActionListener(this);
		
		btnDeconnexion = new JButton("Deconnexion");
		btnDeconnexion.setBounds(408, 176, 101, 23);
		btnDeconnexion.setEnabled(false);
		contentPane.add(btnDeconnexion);
		btnDeconnexion.addActionListener(this);
		
		
		textArea = new JTextArea();
		//textArea.setEditable(false);
		scrollTCP = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollTCP.setBounds(630, 16, 244, 268);
		contentPane.add(scrollTCP);
		
		textField = new JTextField();
		textField.setBounds(519, 146, 101, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblLatence = new JLabel("latence");
		lblLatence.setBounds(408, 67, 189, 14);
		contentPane.add(lblLatence);
		
		textField_1 = new JTextField();
		textField_1.setBounds(519, 92, 101, 20);
		textField_1.addKeyListener(this);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblEnvoyerMessage = new JLabel("Envoyer Message");
		lblEnvoyerMessage.setBounds(408, 95, 101, 14);
		contentPane.add(lblEnvoyerMessage);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(362, 0, 9, 309);
		contentPane.add(separator);
		
		canvas_1 = new Canvas();
		canvas_1.setBounds(10, 0, 19, 19);
		canvas_1.setBackground(Color.RED);
		contentPane.add(canvas_1);
		
		lblClientudp = new JLabel("ClientUDP");
		lblClientudp.setBounds(40, 0, 61, 19);
		contentPane.add(lblClientudp);
		
		lblGroupemulticastNone = new JLabel("GroupeMulticast : none");
		lblGroupemulticastNone.setBounds(10, 26, 202, 14);
		contentPane.add(lblGroupemulticastNone);
		
		lblPortmulticastNone = new JLabel("PortMulticast : none");
		lblPortmulticastNone.setBounds(10, 46, 202, 14);
		contentPane.add(lblPortmulticastNone);
		
		textArea_1 = new JTextArea();
		//textArea_1.setEditable(false);
		scrollUDP = new JScrollPane(textArea_1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollUDP.setBounds(10, 116, 331, 182);
		contentPane.add(scrollUDP);
		
		textField_2 = new JTextField();
		textField_2.setBounds(200, 43, 108, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		textField_2.addKeyListener(this);
		
		lblEnvoyerMessageUdp = new JLabel("Envoyer message UDP");
		lblEnvoyerMessageUdp.setBounds(200, 26, 152, 14);
		contentPane.add(lblEnvoyerMessageUdp);
		
		btnStart = new JButton("Start");
		btnStart.setBounds(123, 0, 89, 23);
		contentPane.add(btnStart);
		btnStart.addActionListener(this);
		
		btnDown = new JButton("Down");
		btnDown.setBounds(218, 0, 89, 23);
		contentPane.add(btnDown);
		btnDown.addActionListener(this);
		
		
		JLabel lblGame = new JLabel("Game :");
		lblGame.setBounds(10, 71, 43, 19);
		contentPane.add(lblGame);
		
		lbladdrIPGame = new JLabel("New label");
		lbladdrIPGame.setBounds(52, 73, 256, 14);
		contentPane.add(lbladdrIPGame);
		
		btnRejoindre = new JButton("rejoindre");
		btnRejoindre.setBounds(10, 91, 89, 23);
		contentPane.add(btnRejoindre);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(519, 118, 101, 20);
		contentPane.add(textField_3);
		btnRejoindre.addActionListener(this);
		
		
	}

	private void running() {
		workers.submit(() -> {
			if(ncudp.networkServerUDPisRunning()) {
				canvas_1.setBackground(Color.GREEN);
				lblGroupemulticastNone.setText("GroupeMulticast : "+ncudp.getAddr());
				lblPortmulticastNone.setText("PortMulticast : "+ncudp.getPort());
				btnStart.setEnabled(false);
				btnDown.setEnabled(true);

			} else{
				canvas_1.setBackground(Color.RED);
				lblGroupemulticastNone.setText("GroupeMulticast : none");
				lblPortmulticastNone.setText("PortMulticast : none");
				btnStart.setEnabled(true);
				btnDown.setEnabled(false);
			}


			if(ctcp.isConnectToServer()) {
				canvas.setBackground(Color.GREEN);
				String name = ctcp.getNameServer();
				lblServeur.setText("Connect on "+name+":"+ctcp.getSocket().getPort());
				btnConnexion.setEnabled(false);
				textField.setEnabled(false);
				textField_1.setEnabled(true);
				btnDeconnexion.setEnabled(true);
				lblClient.setText("Client "+ctcp.getSocket().getLocalPort());
			} else {
				canvas.setBackground(Color.RED);
				lblServeur.setText("None Server");
				btnConnexion.setEnabled(true);
				textField.setEnabled(true);
				textField_1.setEnabled(false);
				btnDeconnexion.setEnabled(false);
				lblClient.setText("Client not connected");
			}
		});
	}

	private ArrayList<String> splitTxt(String txt) {
		ArrayList<String> list = new ArrayList<>();
		for(String retval: txt.split("-")) {
			list.add(retval);
		}
		return list;
	}

	private void latency() {
		workers.submit(() -> {
			String time = ""+ctcp.getLatency();
		
			lblLatence.setText("Latence : "+time +" ms");
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnDeconnexion) {
			ncc.deconnectTCPClient();
		
		}
		
		
		if(e.getSource()==btnConnexion && textField.getText() != null && Integer.parseInt(textField.getText()) >= 0 && Integer.parseInt(textField.getText()) < 65536) {
				ncc.connectTCPClient(textField_3.getText(),Integer.parseInt(textField.getText()),null);
			
		}
		
		
		if(e.getSource()==btnStart && !ncudp.networkServerUDPisRunning()) {
			ncc.startUDP();
			btnStart.setEnabled(false);
			btnDown.setEnabled(true);
		}
		
		if(e.getSource()==btnDown && ncudp.networkServerUDPisRunning()) {
			ncc.downUDP();
			btnStart.setEnabled(true);
			btnDown.setEnabled(false);
		}
		
		if(e.getSource()==btnRejoindre && ncudp.networkServerUDPisRunning()) {
			ncc.downUDP();
			btnStart.setEnabled(true);
			btnDown.setEnabled(false);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		if(e.getSource()==textField_1 && key==KeyEvent.VK_ENTER) {
		
				ctcp.sendMessageTCP(textField_1.getText());
				textField_1.setText("");
		}		
		
		if(e.getSource()==textField_2 && key==KeyEvent.VK_ENTER) {
		
		
				try {
					ncc.sendMulticast(textField_2.getText());
				} catch (Exception e1) {
		
					e1.printStackTrace();
				}
				textField_2.setText("");
			
		}		
	}

	public void printMessage(String string) {
		textArea_1.append(string+"\n");
	/*	scrollUDP = new JScrollPane(textArea_1);
		scrollUDP.setBounds(10, 116, 331, 182);
		contentPane.add(scrollUDP);*/
	}
	
	public void setGamelbl(String msg) {
		lbladdrIPGame.setText(msg);
	}

	@Override
	public void afficherMessage(String message) {
		textArea.append(message+"\n");
		
	}

	public void printMessageUDP(String message) {
		textArea_1.append(message+"\n");
	/*	scrollUDP = new JScrollPane(textArea_1);
		scrollUDP.setBounds(10, 116, 331, 182);  
		contentPane.add(scrollUDP);*/
	}


	@Override
	public void afficherMessage(String message, Socket s, boolean sOrR) {
		// TODO Auto-generated method stub
		
	}
}
