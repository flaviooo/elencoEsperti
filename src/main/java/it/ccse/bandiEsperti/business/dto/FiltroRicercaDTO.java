package it.ccse.bandiEsperti.business.dto;

public class FiltroRicercaDTO {
	
	private String tableName;
	private String columnName;
	private String conditonValue;
	private String columnValue;
	
	public String getColumnValue() {
		return columnValue;
	}
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
	private String betweenCondition="";
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getConditonValue() {
		return conditonValue;
	}
	public void setConditonValue(String conditonValue) {
		this.conditonValue = conditonValue;
	}

	public String getBetweenCondition() {
		return betweenCondition;
	}
	public void setBetweenCondition(String betweenCondition) {
		this.betweenCondition = betweenCondition;
	}
	
}
