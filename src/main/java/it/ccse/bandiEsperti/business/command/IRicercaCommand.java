package it.ccse.bandiEsperti.business.command;

import java.util.Set;

import it.ccse.bandiEsperti.business.dto.EspertoRicercaDinamicaDTO;

public interface IRicercaCommand {

	Set<EspertoRicercaDinamicaDTO> execute() throws Exception;

	
	
}
