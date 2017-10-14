package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Tipodocumento;

public interface TipodocumentoDao {

	public List<Tipodocumento> findAllTipodocumentos();

	public Tipodocumento obtenerTipodocumentoById(Tipodocumento instance);
	
	public Tipodocumento obtenerTipodocumentoByIdString(String instance);	

	public void insertTipodocumento(Tipodocumento instance);

	public void updateTipodocumento(Tipodocumento instance);

	public void deleteTipodocumento(Tipodocumento instance);
}
