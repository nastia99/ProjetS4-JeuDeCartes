package network.viewnetwork.server;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import network.tcp.server.ClientHandler;

public class ModelClient extends AbstractTableModel {
	private ArrayList<ClientHandler> lq = new ArrayList<>();
	private final  String[] entetes = {"Adresse Ip","Port"};
	
	public ModelClient(ArrayList<ClientHandler> lq) {
		this.lq = lq;
	}

	@Override
	public int getRowCount() {
		return lq.size();
	}

	@Override
	public int getColumnCount() {
		return entetes.length;
	}
	
	@Override
	public String getColumnName(int cIndex) {
		return entetes[cIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
		case 0:
			return ""+lq.get(rowIndex).getSocket().getInetAddress();
		case 1:
			return lq.get(rowIndex).getSocket().getPort();
		default:
			return null;
		}
	}

}
