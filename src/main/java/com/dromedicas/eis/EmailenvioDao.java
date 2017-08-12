package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Emailenvio;

public interface EmailenvioDao {
	
	public List<Emailenvio> findAllEmailenvios();

	public Emailenvio obtenerEmailenvioById(Emailenvio instance);

	public void insertEmailenvio(Emailenvio instance);

	public void updateEmailenvio(Emailenvio instance);

	public void deleteEmailenvio(Emailenvio instance);

}
