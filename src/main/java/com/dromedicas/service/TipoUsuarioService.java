package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dromedicas.domain.Tipousuario;
import com.dromedicas.eis.TipousuarioDao;

@Stateless
public class TipoUsuarioService {
	
	@EJB
	private TipousuarioDao dao;
	
	public List<Tipousuario> findAllTipousuarios(){
		return dao.findAllTipousuarios();
	}

	public Tipousuario obtenerTipousuarioById(Tipousuario instance){
		return dao.obtenerTipousuarioById(instance);
	}
	
	public Tipousuario obtenerTipousuarioById(int id){
		return dao.obtenerTipousuarioById(id);
	}

	public void insertTipousuario(Tipousuario instance){
		dao.insertTipousuario(instance);
	}

	public void updateTipousuario(Tipousuario instance){
		dao.updateTipousuario(instance);
	}

	public void deleteTipousuario(Tipousuario instance){
		dao.deleteTipousuario(instance);
	}

}
