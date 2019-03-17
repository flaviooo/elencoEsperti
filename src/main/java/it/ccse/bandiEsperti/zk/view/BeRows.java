package it.ccse.bandiEsperti.zk.view;

import it.ccse.bandiEsperti.utils.Counter;


import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

public class BeRows extends Rows{

	
	private static final long serialVersionUID = -9129561828231078397L;
	List<BeRow> rows = new ArrayList<BeRow>();
	Counter counter = new  Counter(0);
	
	
	public synchronized void addRow(BeRow row){
		int index = counter.getCounterAdd();
		rows.add(index,row);
		row.setParent(this);
		row.setIndex(index);
		
	}
	public boolean isLast(BeRow row){
		boolean last = false;
		if(row.getIndex()==(getRows().size()-1)){
			last=true;
		}
		return last;
	}
	
	public Row getRow(int index){
		return rows.get(index);
	}
	
	public synchronized void removeRow(Row row){
		counter.getCounterMinus();
		//Row row = rows.get(index);
		int index = rows.indexOf(row);
		
		if(index>0){
			index--;
			if(rows.get(index)!=null){
				BeRow beRow = (BeRow) rows.get(index);
				beRow.getAggiungi().setDisabled(false);
			}
		}
		row.detach();
		rows.remove(row);
	}
	
	public List<BeRow> getRows() {
		return rows;
	}
	public void setRows(List<BeRow> rows) {
		this.rows = rows;
	}
	
	
	
}
