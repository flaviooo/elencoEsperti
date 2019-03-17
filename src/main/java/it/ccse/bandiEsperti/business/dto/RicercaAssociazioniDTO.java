package it.ccse.bandiEsperti.business.dto;


import java.util.ArrayList;
import java.util.List;

public class RicercaAssociazioniDTO {

	private List<EspertoDTO> espertiList = new ArrayList<EspertoDTO>();
	private List<TemaDTO> temiList = new ArrayList<TemaDTO>();
	private List<DeliberaDTO> delibereList = new ArrayList<DeliberaDTO>();
	
	public List<EspertoDTO> getEspertiList() {
		return espertiList;
	}
	public void setEspertiList(List<EspertoDTO> espertiList) {
		this.espertiList = espertiList;
	}
	public List<TemaDTO> getTemiList() {
		return temiList;
	}
	public void setTemiList(List<TemaDTO> temiList) {
		this.temiList = temiList;
	}
	public List<DeliberaDTO> getDelibereList() {
		return delibereList;
	}
	public void setDelibereList(List<DeliberaDTO> delibereList) {
		this.delibereList = delibereList;
	}
	


		
}
