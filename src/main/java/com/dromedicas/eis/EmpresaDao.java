package com.dromedicas.eis;

import java.util.List;


import com.dromedicas.domain.Empresa;

public interface EmpresaDao {
	
	public List<Empresa> findAllEmpresas();
	
	public Empresa obtenerEmpresaById(Empresa instance);
	
	public Empresa obtenerEmpresaById(String instance);
	
	public void insertEmpresa(Empresa instance);
	
	public void updateEmpresa(Empresa instance);
	
	public void deleteEmpresa(Empresa instance);

}
