package it.ccse.bandiEsperti.zk.composers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.ccse.bandiEsperti.business.dto.CompetenzaDTO;
import it.ccse.bandiEsperti.business.dto.EspertoDTO;
import it.ccse.bandiEsperti.business.dto.EspertoRicercaDTO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO;
import it.ccse.bandiEsperti.business.dto.FiltroRicercaEspertiDTO.TipoFiltro;
import it.ccse.bandiEsperti.business.dto.IncaricoDTO;
import it.ccse.bandiEsperti.business.dto.RicercaEspertiDTO;
import it.ccse.bandiEsperti.business.model.Specializzazione;

class AdminComposerHelper {
	
	public RicercaEspertiDTO createReportData(List<EspertoDTO> listaEsperti, FiltroRicercaEspertiDTO filtroRicerca)
	{
		
		RicercaEspertiDTO dto = new RicercaEspertiDTO();
		for (EspertoDTO espertoDTO : listaEsperti)
			dto.getEspertiList().add(toRicercaDTO(espertoDTO));
		
		if (filtroRicerca != null)
		{
			Boolean conPubblicazioni = (Boolean) filtroRicerca.getPubblicazioni().get(TipoFiltro.PUBBLICAZIONIPRESENTI);
			//pubblicazioni
			dto.setConPubblicazioni((conPubblicazioni != null && conPubblicazioni.booleanValue() == true));

			//datore lavoro
			String datoreLavoroFiltro = (String) filtroRicerca.getEsperienze().get(TipoFiltro.DATORELAVORO);
			if (datoreLavoroFiltro != null)
				dto.setDatoreLavoro(datoreLavoroFiltro);
			//esperienza da
			Date dataEsperienzaDa = (Date) filtroRicerca.getEsperienze().get(TipoFiltro.APARTIREDA);
			if (dataEsperienzaDa != null)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				dto.setEsperienzaDa(sdf.format(dataEsperienzaDa));
				dto.setEsperienzaInCorso(true);
			}
			//esperienza in corso
			if (dto.isEsperienzaInCorso() == false)
			{
			  Boolean inCorsoFiltro = (Boolean) filtroRicerca.getEsperienze().get(TipoFiltro.INCORSO);
			  if (inCorsoFiltro != null)
				  dto.setEsperienzaInCorso(inCorsoFiltro.booleanValue());
			}
			
			popolaCompetenze(dto, filtroRicerca);
			popolaIncarichi(dto, filtroRicerca);
			
			//filtro cognome
			String cognomeFiltro = (String) filtroRicerca.getDatiAnagrafici().get(TipoFiltro.COGNOME);
			if (cognomeFiltro != null)
				dto.setFiltroCognome(cognomeFiltro);
			//filtro datanascita
			Date dataLimite = (Date) filtroRicerca.getDatiAnagrafici().get(TipoFiltro.DATALIMITE);
			if (dataLimite != null)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				dto.setFiltroDataNascitaLimite(sdf.format(dataLimite));
			}
		}
		
		return dto;
	}
	
	private void popolaIncarichi(RicercaEspertiDTO dto,
			FiltroRicercaEspertiDTO filtroRicerca) {
		
		List<IncaricoDTO> listaIncarichi = new ArrayList<IncaricoDTO>();
		Boolean incaricoRdsFiltro = (Boolean) filtroRicerca.getPrecedentiIncarichi().get(TipoFiltro.INCARICHIPRESENTIRDS);
		if (incaricoRdsFiltro != null && incaricoRdsFiltro.booleanValue() == true)
		{
			IncaricoDTO incDTO = new IncaricoDTO();
			incDTO.setNome("RdS");
			listaIncarichi.add(incDTO);
		}
		Boolean incaricoAltroFiltro = (Boolean) filtroRicerca.getPrecedentiIncarichi().get(TipoFiltro.INCARICHIPRESENTIALTRO);
		if (incaricoAltroFiltro != null && incaricoAltroFiltro.booleanValue() == true)
		{
			IncaricoDTO incDTO = new IncaricoDTO();
			incDTO.setNome("Altro");
			listaIncarichi.add(incDTO);
		}
		if (listaIncarichi.isEmpty())
			listaIncarichi.add(new IncaricoDTO());
		dto.getIncarichiList().addAll(listaIncarichi);
	}

	private void popolaCompetenze(RicercaEspertiDTO dto,
			FiltroRicercaEspertiDTO filtroRicerca) {
		
		List<Specializzazione> specializzazioni = (List<Specializzazione>) filtroRicerca.getCompetenze().get(TipoFiltro.LISTACOMPETENZE);
		if (specializzazioni != null)
		{
			CompetenzaDTO dtoComp;
			for (Specializzazione spec: specializzazioni)
			{
				dtoComp = new CompetenzaDTO();
				dtoComp.setArea(spec.getTema().getNome());
				dtoComp.setNome(spec.getNome());
				dto.getCompetenzeList().add(dtoComp);
			}
			
		}
		else
			dto.getCompetenzeList().add(new CompetenzaDTO());
		
	}

	private EspertoRicercaDTO toRicercaDTO(EspertoDTO esp)
	{
		EspertoRicercaDTO espDTO = new EspertoRicercaDTO();
		espDTO.setCognome(esp.getCognome());
		espDTO.setNome(esp.getNome());
		espDTO.setDataNascita(esp.getDataNascita());
		return espDTO;
	}

}
