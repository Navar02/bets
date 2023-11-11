package Adapter;

import java.util.Date;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import businessLogic.BLFacade;
import domain.Event;
import domain.User;

public class UserAdapter extends AbstractTableModel{
	User user;
	BLFacade fac;
	private String[] colNames = new String[] {"Event", "Question","Event Date","Bet"};
	public UserAdapter(User user, BLFacade fa) {
		this.user=user;
		this.fac=fa;
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
			return "Eibar-Barcelona";
			
		case 1:
			int qNum1= user.getVecApuestas().get(rowIndex).getQNum();
			String question =fac.getQuestionByNum(qNum1+1).getQuestion();
			return question;
		case 2:
			return "17/12/2023";
		case 3:
			return user.getVecApuestas().get(rowIndex).getCantidad();
		}
		return null;
	}

}
