package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Emailestado;

public interface EmailestadoDao {
	
	public List<Emailestado> findAllEmailestados();

	public Emailestado obtenerEmailestadoById(Emailestado instance);

	public void insertEmailestado(Emailestado instance);

	public void updateEmailestado(Emailestado instance);

	public void deleteEmailestado(Emailestado instance);

}
