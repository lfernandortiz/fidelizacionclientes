package com.dromedicas.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.eis.AfiliadoDao;

@Stateless
public class AfiliadoService {
	
	@EJB	
	private AfiliadoDao afiliadoDao;
	
	public List<Afiliado> findAllAfiliados(){
		return this.afiliadoDao.findAllAfiliados();
	}
	
	public Afiliado obtenerAfiliadoById(Afiliado instance){
		return this.afiliadoDao.obtenerAfiliadoById(instance);
	}
	
	public Afiliado obtenerAfiliadoByDocumento(Afiliado instance){
		return this.afiliadoDao.obtenerAfiliadoByDocumento(instance);
	}
	
	public void insertAfiliado(Afiliado instance){
		this.afiliadoDao.insertAfiliado(instance);
	}
	
	public void updateAfiliado(Afiliado instance){
		this.afiliadoDao.updateAfiliado(instance);
	}
	
	public void deleteAfiliado(Afiliado instance){
		this.afiliadoDao.deleteAfiliado(instance);
	}
	
	
}
