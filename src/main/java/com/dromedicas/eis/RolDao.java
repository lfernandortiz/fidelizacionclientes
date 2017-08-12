package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Rol;

public interface RolDao {
	
	public List<Rol> findAllRols();

	public Rol obtenerRolById(Rol instance);
	
	public Rol obtenerRolById(String id);

	public void insertRol(Rol instance);

	public void updateRol(Rol instance);

	public void deleteRol(Rol instance);

}
