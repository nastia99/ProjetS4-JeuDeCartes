package network.viewnetwork.server;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import network.networkserver.NetworkServer;
import network.tcp.server.ClientHandler;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;


public class PanelMain extends JPanel {

	
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel lblEtatServer;
	
	private JLabel lblTCP;
	private Component label;
	private JLabel label_1;
	private JLabel lblNbWait;
	private JLabel lblnbConnection;
	private JTable tableConnected;
	private JScrollPane scrollPaneConnected;
	private JTable tableWait;
	private JScrollPane scrollPaneWait;

	/**
	 * Create the panel.
	 */
	public PanelMain() {
		super();
		this.setBounds(126, 34, 649, 465);
		
		//panel.add(scrollPane);
		setLayout(null);
		table = new JTable();
		centerTable(table);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(22, 73, 314, 78);
		
		this.add(scrollPane);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		tableWait = new JTable();
		tableWait.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		centerTable(tableWait);
		scrollPaneWait = new JScrollPane(tableWait);
		scrollPaneWait.setBounds(22, 283, 286, 171);
		this.add(scrollPaneWait);
		scrollPaneWait.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 649, 465);
		add(panel);
		
		lblEtatServer = new JLabel("Etats des Serveurs");
		lblEtatServer.setForeground(UIManager.getColor("Label.foreground"));
		lblEtatServer.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		
			
			lblTCP = new JLabel("Details service TCP");
			lblTCP.setForeground(UIManager.getColor("Label.foreground"));
			lblTCP.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			
			label_1 = new JLabel("Clients connectés");
			label_1.setForeground(UIManager.getColor("Label.foreground"));
			label_1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			
			lblNbWait = new JLabel("0");
			lblNbWait.setForeground(UIManager.getColor("Label.foreground"));
			lblNbWait.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			
			label = new JLabel("Clients en attente");
			label.setForeground(UIManager.getColor("Label.foreground"));
			label.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			
			lblnbConnection = new JLabel("0");
			lblnbConnection.setForeground(UIManager.getColor("Label.foreground"));
			lblnbConnection.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			
			tableConnected = new JTable();
			tableConnected.setBackground(Color.WHITE);
			tableConnected.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
			centerTable(tableConnected);
			scrollPaneConnected = new JScrollPane(tableConnected);
			scrollPaneConnected.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			
			
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(20)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(lblEtatServer, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(label, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
										.addGap(26)
										.addComponent(lblNbWait, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblTCP))))
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblnbConnection, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
									.addComponent(scrollPaneConnected, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE))
								.addContainerGap())
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(51)
								
								.addContainerGap())))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblEtatServer, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						)
						.addGap(153)
						.addComponent(lblTCP)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNbWait, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblnbConnection, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPaneConnected, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			panel.setLayout(gl_panel);
		
		this.init();
		
	
	}
	
	public void init() {
		
	}
	
	
	public void serverNotification(ArrayList<NetworkServer> listServer, String nbPlayerWait, String nbPlayerConnected,
			ArrayList<ClientHandler> wait, ArrayList<ClientHandler> connected) {
		//MAJ MODEL TABLE
		
				ModelServer ms = new ModelServer(listServer);
				this.remove(table);
				
				ModelClient mcWait = new ModelClient(wait);
				this.remove(tableWait);
				
				ModelClient mcConnected = new ModelClient(connected);
				this.remove(tableConnected);
				//MAJ JTABLE
				
				table = new JTable(ms);
				centerTable(table);
				table.setCellSelectionEnabled(false);
				scrollPane = new JScrollPane(table);
				scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				scrollPane.setBounds(22, 73, 314, 78);
				this.add(scrollPane);

				
				tableWait = new JTable(mcWait);
				tableWait.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
				centerTable(tableWait);
				scrollPaneWait = new JScrollPane(tableWait);
				scrollPaneWait.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				scrollPaneWait.setBounds(24, 283, 300, 171);
				this.add(scrollPaneWait);
				
				tableConnected = new JTable(mcConnected);
				tableConnected.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
				centerTable(tableConnected);
				scrollPaneConnected = new JScrollPane(tableConnected);
				scrollPaneConnected.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				scrollPaneConnected.setBounds(334, 283, 298, 171);
				this.add(scrollPaneConnected);

				// SET VALUE CONNECTION TCP
				
				lblNbWait.setText(nbPlayerWait);
				lblnbConnection.setText(nbPlayerConnected);
	}
	
	private void centerTable(JTable t) {
		DefaultTableCellRenderer g = new DefaultTableCellRenderer();
		g.setHorizontalAlignment(JLabel.CENTER);
		for(int i = 0; i< t.getColumnCount(); i++) {
			t.getColumnModel().getColumn(i).setCellRenderer(g);
		}
	}
}
