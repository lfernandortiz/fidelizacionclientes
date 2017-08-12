package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Contrato;

public interface ContratoDao {
	
	public List<Contrato> findAllContratos();
	
	public Contrato obtenerContratoById(Contrato instance);
	
	public void insertContrato(Contrato instance);
	
	public void updateContrato(Contrato instance);
	
	public void deleteContrato(Contrato instance);

}
