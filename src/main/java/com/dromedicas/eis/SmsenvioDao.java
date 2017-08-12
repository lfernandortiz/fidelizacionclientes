package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Smsenvio;

public interface SmsenvioDao {
	
	public List<Smsenvio> findAllSmsenvios();

	public Smsenvio obtenerSmsenvioById(Smsenvio instance);

	public void insertSmsenvio(Smsenvio instance);

	public void updateSmsenvio(Smsenvio instance);

	public void deleteSmsenvio(Smsenvio instance);

}
