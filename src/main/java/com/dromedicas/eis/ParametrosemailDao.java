package com.dromedicas.eis;

import com.dromedicas.domain.Parametrosemail;

public interface ParametrosemailDao {
	
	public Parametrosemail obtenerParametrosemailById(Parametrosemail instance);
	
	public void insertParametrosemail(Parametrosemail instance);
	
	public void updateParametrosemail(Parametrosemail instance);
	
	public void deleteParametrosemail(Parametrosemail instance);

}
