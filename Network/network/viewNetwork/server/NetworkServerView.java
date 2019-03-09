package network.viewnetwork.server;


import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import network.analyse.listener.TCPPrintListener;
import network.analyse.listener.UDPPrintListener;
import network.networkserver.NetworkServer;
import network.tcp.server.ClientHandler;
import java.awt.Font;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Toolkit;

public class NetworkServerView implements ListenServer, ActionListener, TCPPrintListener,UDPPrintListener {

	private JFrame frmServerManagementMow;
	private PanelLog panelPrint;
	private PanelMain pm;
	private JButton btnLogs;
	private JButton btnAcceuil;
	private JPanel card;
	private JLabel label;
	private JLabel label_1;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NetworkServerView window = new NetworkServerView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the application.
	 */
	public NetworkServerView(boolean b) {
		boolean IS_VISIBLE = b;
		initialize(IS_VISIBLE); // false to disable developper interface
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(boolean isVisible) {
		frmServerManagementMow = new JFrame();
		frmServerManagementMow.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\steve\\Downloads\\Cow_33885.ico"));
		frmServerManagementMow.setForeground(UIManager.getColor("windowText"));
		frmServerManagementMow.setTitle("Server Management MOW");
		frmServerManagementMow.setBounds(100, 100, 791, 528);
		frmServerManagementMow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServerManagementMow.getContentPane().setLayout(null);
		
		card = new JPanel();
		card.setBounds(126, 34, 649, 465);
		card.setLayout(new CardLayout());
		
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setForeground(new Color(0, 0, 0));
		panel.setBounds(0, 0, 775, 34);
		frmServerManagementMow.getContentPane().add(panel);
		panel.setBackground(new Color(0, 0, 0));
		panel.setLayout(null);
		
		JLabel lblNetworkServerMow = new JLabel("Network Server MOW");
		lblNetworkServerMow.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNetworkServerMow.setBounds(308, 11, 167, 12);
		panel.add(lblNetworkServerMow);
		lblNetworkServerMow.setIcon(new ImageIcon("C:\\Users\\steve\\Downloads\\Cow_33885.ico"));
		lblNetworkServerMow.setForeground(UIManager.getColor("window"));
		
		label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("C:\\Users\\steve\\OneDrive\\Pictures\\1351716543_28184.jpg"));
		label_1.setBounds(10, 0, 775, 56);
		panel.add(label_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(0, 34, 126, 176);
		frmServerManagementMow.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		btnAcceuil = new JButton("Acceuil");
		btnAcceuil.setBackground(SystemColor.activeCaption);
		btnAcceuil.setBounds(10, 11, 89, 23);
		btnAcceuil.addActionListener(this);
		panel_3.add(btnAcceuil);
		
		btnLogs = new JButton("Logs");
		btnLogs.setBackground(SystemColor.activeCaption);
		btnLogs.setBounds(10, 45, 89, 23);
		btnLogs.addActionListener(this);
		panel_3.add(btnLogs);
		frmServerManagementMow.setVisible(isVisible);
		
		panelPrint = new PanelLog();
		pm = new PanelMain();
		
		card.add("log",panelPrint);
		card.add("main",pm);
		
		//frmServerManagementMow.getContentPane().add(card);
		frmServerManagementMow.getContentPane().add(card);
		((CardLayout) card.getLayout()).show(card,"main");
		
		label = new JLabel("");
		label.setBounds(0, 207, 533, 292);
		frmServerManagementMow.getContentPane().add(label);
		label.setIcon(new ImageIcon("C:\\Users\\steve\\OneDrive\\Pictures\\vache.jpg"));
	}

	

	@Override
	public void serverNotification(ArrayList<NetworkServer> listServer, String nbPlayerWait, String nbPlayerConnected,
			ArrayList<ClientHandler> wait, ArrayList<ClientHandler> connected) {
			pm.serverNotification(listServer, nbPlayerWait, nbPlayerConnected, wait, connected);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnLogs) {
			((CardLayout) card.getLayout()).show(card,"log");
		}
		if(e.getSource() == btnAcceuil) {
			((CardLayout) card.getLayout()).show(card,"main");
		}
		
	}

	@Override
	public void printUDP(String message, String ip, String p) {
		InfoLogsUDP il = new InfoLogsUDP(message, ip, p);
		panelPrint.addNewInfoUDP(il);
	}

	@Override
	public void afficherMessage(String message,Socket s,boolean sOrR) {
		
		if(sOrR) {
			InfoLogs il = new InfoLogs(""+s.getLocalAddress(), ""+s.getLocalPort(),""+s.getInetAddress(), ""+s.getPort(), message);
			panelPrint.addNewInfoTCP(il);
		} 
		else {
			InfoLogs il = new InfoLogs(""+s.getInetAddress(), ""+s.getPort(), ""+s.getLocalAddress(), ""+s.getLocalPort(), message);
			panelPrint.addNewInfoTCP(il);
		}
		
	}

	public InfoLogsUDP getLastMsgUDP() {
		return panelPrint.getLastInfo();
	}

	public InfoLogs getLastMsgTCP() {
		return panelPrint.getLastInfoTCP();
	}

	@Override
	public void afficherMessage(String message) {
		// TODO Auto-generated method stub
		
	}
}
