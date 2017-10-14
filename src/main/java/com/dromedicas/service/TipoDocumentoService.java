package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;

import com.dromedicas.domain.Tipodocumento;
import com.dromedicas.eis.TipodocumentoDao;

@ManagedBean
@Stateless
public class TipoDocumentoService {
	
	@EJB
	private TipodocumentoDao dao;
	
	public List<Tipodocumento> findAllTipodocumento(){
		return dao.findAllTipodocumentos();
	}
	
	public Tipodocumento obtenerTipodocumentoByIdString(String instance){
		return dao.obtenerTipodocumentoByIdString(instance);
	}

	public void insertTipodocumento(Tipodocumento instance){
		dao.insertTipodocumento(instance);
	}

	public void updateTipodocumento(Tipodocumento instance){
		dao.updateTipodocumento(instance);
	}

	public void deleteTipodocumento(Tipodocumento instance){
		dao.deleteTipodocumento(instance);
	}
	

}
