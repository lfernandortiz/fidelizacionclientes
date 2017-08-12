package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Eventowebservice;

public interface EventowebserviceDao {	
	
	public List<Eventowebservice> findAllEventowebservices();

	public Eventowebservice obtenerEventowebserviceById(Eventowebservice instance);

	public void insertEventowebservice(Eventowebservice instance);

	public void updateEventowebservice(Eventowebservice instance);

	public void deleteEventowebservice(Eventowebservice instance);

}
