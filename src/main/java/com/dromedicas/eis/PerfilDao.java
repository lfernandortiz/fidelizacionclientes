package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Perfil;

public interface PerfilDao {

	public List<Perfil> findAllPerfils();

	public Perfil obtenerPerfilById(Perfil instance);

	public void insertPerfil(Perfil instance);

	public void updatePerfil(Perfil instance);

	public void deletePerfil(Perfil instance);
}
