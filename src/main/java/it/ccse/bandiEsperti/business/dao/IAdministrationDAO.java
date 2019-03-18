package it.ccse.bandiEsperti.business.dao;

import java.io.Serializable;


public interface IAdministrationDAO {
	boolean checkIfExist(
			Serializable serializable) throws Exception;
}
