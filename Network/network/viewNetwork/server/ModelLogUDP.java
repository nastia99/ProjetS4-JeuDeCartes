package network.viewnetwork.server;

import java.util.ArrayList;

public class ModelLogUDP extends ModelData {
	private ArrayList<InfoLogsUDP> lq = new ArrayList<>();
	private final static String[] entetes = {"IP Multicast","Port Multicast", "Contenu"};
	
	public ModelLogUDP() {
		//AUCUN BEOSIN
	}
	
	public void addInfo(InfoLogsUDP il) {
		lq.add(il);
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
			return lq.get(rowIndex).getIpM();
		case 1:
			return lq.get(rowIndex).getpM();
		case 2:
			return lq.get(rowIndex).getContenu();
		default:
			return null;
		}
	}

	public InfoLogsUDP lastInfo() {
		if(lq.isEmpty()) {
			return null;
		}
		else {
			int i = lq.size();
			return lq.get(i-1);
		}
	}
}
