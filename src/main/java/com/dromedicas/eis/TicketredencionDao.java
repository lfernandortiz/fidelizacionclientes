package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Ticketredencion;

public interface TicketredencionDao {
	
	public List<Ticketredencion> findAllTicketredencions();
	
	public Ticketredencion obtenerTicketredencionById(Ticketredencion instance);
		
	public void insertTicketredencion(Ticketredencion instance);
	
	public void updateTicketredencion(Ticketredencion instance);
	
	public void deleteTicketredencion(Ticketredencion instance);

}
