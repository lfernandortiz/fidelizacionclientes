package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Usuarioweb;

public interface UsuariowebDao {
	
	public List<Usuarioweb> findAllUsuariowebs();

	public Usuarioweb obtenerUsuariowebById(Usuarioweb instance);
	
	public Usuarioweb obtenerUsuariowebById(int id);

	public void insertUsuarioweb(Usuarioweb instance);

	public void updateUsuarioweb(Usuarioweb instance);

	public void deleteUsuarioweb(Usuarioweb instance);

}
