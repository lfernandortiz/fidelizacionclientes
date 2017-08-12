package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Smsplantilla;

public interface SmsplantillaDao {
	
	public List<Smsplantilla> findAllSmsplantillas();

	public Smsplantilla obtenerSmsplantillaById(Smsplantilla instance);

	public void insertSmsplantilla(Smsplantilla instance);

	public void updateSmsplantilla(Smsplantilla instance);

	public void deleteSmsplantilla(Smsplantilla instance);

}
