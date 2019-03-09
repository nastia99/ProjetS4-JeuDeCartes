package network.viewnetwork.server;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


@SuppressWarnings("serial")
public class PanelLog extends JPanel {

	private JScrollPane scrollPane;
	private JTable table;
	private ModelLogTCP ml;
	private ModelLogUDP mlu;
	private JTable tableU;
	private Component scrollPaneU;

	/**
	 * Create the panel.
	 */
	public PanelLog() {
		super();
		this.setBounds(126, 34, 649, 465);
		this.setLayout(null);
		
		ml = new ModelLogTCP();
		mlu = new ModelLogUDP();
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		centerTable(table);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 253, 629, 182);
		this.add(scrollPane);
		
		
		tableU = new JTable();
		tableU.setBackground(Color.WHITE);
		tableU.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		centerTable(tableU);
		scrollPaneU = new JScrollPane(table);
		scrollPaneU.setBounds(10, 49, 629, 182);
		this.add(scrollPaneU);
		
	}
	
	private void centerTable(JTable t) {
		DefaultTableCellRenderer g = new DefaultTableCellRenderer();
		g.setHorizontalAlignment(JLabel.CENTER);
		for(int i = 0; i< t.getColumnCount(); i++) {
			t.getColumnModel().getColumn(i).setCellRenderer(g);
		}
	}

	public void addNewInfoTCP(InfoLogs il) {
		ml.addInfo(il);
		
		this.remove(scrollPane);
		table = new JTable(ml);
		table.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		centerTable(table);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 253, 629, 182);
		scrollPane.setEnabled(true);
		this.add(scrollPane);
	}
	
	public void addNewInfoUDP(InfoLogsUDP il) {
		mlu.addInfo(il);
		
		this.remove(scrollPaneU);
		tableU = new JTable(mlu);
		tableU.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		centerTable(tableU);
		scrollPaneU = new JScrollPane(tableU);
		scrollPaneU.setBounds(10, 49, 629, 182);
		scrollPaneU.setEnabled(true);
		this.add(scrollPaneU);
	}

	public InfoLogsUDP getLastInfo() {
		return mlu.lastInfo();
	}

	public InfoLogs getLastInfoTCP() {
		return ml.lastInfoTCP();
	}


}
