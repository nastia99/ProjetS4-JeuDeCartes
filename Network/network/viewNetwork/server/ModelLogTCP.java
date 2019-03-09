package network.viewnetwork.server;

import java.util.ArrayList;

	@SuppressWarnings("serial")
	public class ModelLogTCP extends ModelData {
		private ArrayList<InfoLogs> lq = new ArrayList<>();
		private static String[] entetes = {"IP Source","Port Source","IP Destination","Port Destination","Contenu"};
		
		public ModelLogTCP() {
			//AUCUN BESOIN
		}
		
		public void addInfo(InfoLogs il) {
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
				return lq.get(rowIndex).getIpS();
			case 1:
				return lq.get(rowIndex).getpSrc();
			case 2:
				return lq.get(rowIndex).getIpD();
			case 3:
				return lq.get(rowIndex).getpDest();
			case 4:
				return lq.get(rowIndex).getContenu();
			default:
				return null;
			}
		}

		public InfoLogs lastInfoTCP() {
			if(lq.isEmpty()) {
				return null;
			}
			else {
				int i = lq.size();
				return lq.get(i-1);
			}
		}

	}


