package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Pais;

public interface PaisDao {

		public List<Pais> findAllPais();

		public Pais obtenerPaisById(Pais instance);
		
		public void insertPais(Pais instance);

		public void updatePais(Pais instance);

		public void deletePais(Pais instance);
	
}
