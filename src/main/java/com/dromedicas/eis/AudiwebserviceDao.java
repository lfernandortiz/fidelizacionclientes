package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Audiwebservice;

public interface AudiwebserviceDao {
	
	public List<Audiwebservice> findAllAudiwebservices();
	
	public Audiwebservice obtenerAudiwebserviceById(Audiwebservice instance);
		
	public void insertAudiwebservice(Audiwebservice instance);
	
	public void updateAudiwebservice(Audiwebservice instance);
	
	public void deleteAudiwebservice(Audiwebservice instance);

}
