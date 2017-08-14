package com.dromedicas.servicio.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.dromedicas.domain.Sucursal;
import com.dromedicas.service.SucursalService;

@Path("/sucursal")
@Stateless
public class SucursalServiceRS {
	
	@EJB
	private SucursalService sServices;
	
	
	@GET
	@Produces("application/json")
	public List<Sucursal> listarSucursaels(){
		
		List<Sucursal> s =  this.sServices.findAllSucursals();
		
		return s;
	}

}
