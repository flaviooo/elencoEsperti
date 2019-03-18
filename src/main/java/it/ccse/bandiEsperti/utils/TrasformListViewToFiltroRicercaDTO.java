package it.ccse.bandiEsperti.utils;

import java.util.ArrayList;
import java.util.List;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaDTO;
import it.ccse.bandiEsperti.zk.view.BeRow;
import it.ccse.bandiEsperti.zk.view.BeRows;


public class TrasformListViewToFiltroRicercaDTO {
	
	private static TrasformListViewToFiltroRicercaDTO instance;
	
	private TrasformListViewToFiltroRicercaDTO(){}
	
	public static TrasformListViewToFiltroRicercaDTO getInstance(){
	    if(instance == null){
	        instance = new TrasformListViewToFiltroRicercaDTO();
	    }
	    return instance;
	}
	
	public List<FiltroRicercaDTO> execute(BeRows beRows) throws Exception {
		
		List<FiltroRicercaDTO> listFiltroRicercaDTO = new ArrayList<FiltroRicercaDTO>();
		List<BeRow> rows = beRows.getRows();
		
		for(BeRow beRow: rows){
			FiltroRicercaDTO filtroRicercaDTO = new FiltroRicercaDTO();
			if(beRow.getCampiRicercaSelezionato()!=null && 
				(beRow.getTestoSelezionato()!=null || beRow.getLocalitaSelezionata()!=null) &&
				beRow.getOperatoreRicercaSelezionato()!=null){
				filtroRicercaDTO.setColumnName(beRow.getCampiRicercaSelezionato().getNomeColonna());
				if(beRow.getTestoSelezionato()!=null){
					filtroRicercaDTO.setColumnValue(beRow.getTestoSelezionato());
				}else{
					filtroRicercaDTO.setColumnValue(String.valueOf(beRow.getLocalitaSelezionata()));
				}
				filtroRicercaDTO.setConditonValue(beRow.getOperatoreRicercaSelezionato());
				filtroRicercaDTO.setTableName(beRow.getCampiRicercaSelezionato().getNomeTabella());
				if(filtroRicercaDTO.getConditonValue().equals("like")){
					filtroRicercaDTO.setColumnValue("%"+filtroRicercaDTO.getColumnValue()+"%");
				}
				if(beRow.getCondizioneSelezionata()!=null && beRow.getCondizioneSelezionata()!=null){
					filtroRicercaDTO.setBetweenCondition(beRow.getCondizioneSelezionata().getNome());
				}
				
				listFiltroRicercaDTO.add(filtroRicercaDTO);
			
			}	
		    
		}
		if(listFiltroRicercaDTO.size()>0){
			listFiltroRicercaDTO.get(listFiltroRicercaDTO.size()-1).setBetweenCondition("");
		}
		return listFiltroRicercaDTO;
	}
	
}
