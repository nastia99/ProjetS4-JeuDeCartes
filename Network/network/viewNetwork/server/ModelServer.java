package network.viewnetwork.server;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import network.networkserver.NetworkServer;

public class ModelServer extends AbstractTableModel {
	private ArrayList<NetworkServer> lq = new ArrayList<>();
	private final static String[] entetes = {"Etat","Service","Adresse","Port"};
	
	public ModelServer(ArrayList<NetworkServer> lq) {
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
			return lq.get(rowIndex).getEtat();
		case 1:
			return lq.get(rowIndex).getNameService();
		case 2:
			return lq.get(rowIndex).getAdressIp();
		case 3:
			return lq.get(rowIndex).getPortListen();
		default:
			return null;
		}
	}

}
