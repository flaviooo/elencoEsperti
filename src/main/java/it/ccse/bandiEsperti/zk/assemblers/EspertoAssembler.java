package it.ccse.bandiEsperti.zk.assemblers;

import it.ccse.bandiEsperti.business.dto.AllegatoDTO;
import it.ccse.bandiEsperti.business.dto.EspertoDTO;
import it.ccse.bandiEsperti.business.model.Esperto;

public class EspertoAssembler {

	public static EspertoDTO toDTO(Esperto esperto)
	{
		EspertoDTO dto = new EspertoDTO();
		dto.setId(esperto.getId());
		dto.setCognome(esperto.getCognome());
		dto.setNome(esperto.getNome());
		dto.setStato(esperto.getStato());
		dto.setDataNascita(esperto.getDataNascita());
		dto.setUsername(esperto.getUsername());
		dto.setCodiceDomanda(esperto.getCodiceDomanda());
		dto.setPassword(esperto.getPassword());
		dto.setEmail(esperto.getEmail());
		dto.setTelefono(esperto.getTel());
		dto.setNumPubblicazioni(esperto.getPubblicazioni().size());
		if (esperto.getDettaglio() != null){
			dto.setGiudizio(esperto.getDettaglio().getGiudizio());
			dto.setTipoIncarico(esperto.getDettaglio().getTipoIncarico());
		}
		if (esperto.getAllegato() != null){
			AllegatoDTO allDTO = new AllegatoDTO();
			allDTO.setContent(esperto.getAllegato().getAllegato());
			allDTO.setContentType(esperto.getAllegato().getContentType());
			allDTO.setEstensione(esperto.getAllegato().getEstensione());
			dto.setAllegatoPubblicazioni(allDTO);
		}
		dto.setInviato(esperto.getInviato());
		if (esperto.getDataInvio() != null){
			dto.setDataInvio(esperto.getDataInvio());
		}
		return dto;
	}

	
}
