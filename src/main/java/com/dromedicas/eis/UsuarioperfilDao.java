package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Usuarioperfil;

public interface UsuarioperfilDao {
	
	public List<Usuarioperfil> findAllUsuarioperfils();

	public Usuarioperfil obtenerUsuarioperfilById(Usuarioperfil instance);

	public void insertUsuarioperfil(Usuarioperfil instance);

	public void updateUsuarioperfil(Usuarioperfil instance);

	public void deleteUsuarioperfil(Usuarioperfil instance);

}
