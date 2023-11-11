package Adapter;

import javax.swing.table.AbstractTableModel;

import domain.User;

public class UserAdapter extends AbstractTableModel{
	User user;
	private String[] colNames = new String[] {"Event", "Question","Event Date","Bet"};
	public UserAdapter(User user) {
		this.user=user;
	}
	@Override
	public int getRowCount() {
		return user.getVecApuestas().size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}
	
	public String getColumnName(int columnIndex){
		return colNames[columnIndex];
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch (columnIndex) {
		case 0:
			return user.getVecApuestas().get(rowIndex).getName();
		case 1:
			return user.getVecApuestas().get(rowIndex).getQNum();
		case 2:
			return user.getVecApuestas().get(rowIndex).getPronostico();
		case 3:
			return user.getVecApuestas().get(rowIndex).getCantidad();
		}
		return null;
	}

}
