package it.ccse.bandiEsperti.business.dto;

public class CompetenzaDTO {
	
	private String nome;
	private String area;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getCompetenzaEstesa()
	{
		boolean flagNome = (nome != null && !nome.equals(""));
		boolean flagArea = (area != null && !area.equals(""));
		if (flagNome && flagArea)
			return area+": "+nome;
		else
			return "";
	}

}
