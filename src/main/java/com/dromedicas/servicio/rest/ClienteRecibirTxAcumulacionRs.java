package com.dromedicas.servicio.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Contrato;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Tipotransaccion;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.EmpresaService;
import com.dromedicas.service.OperacionPuntosService;
import com.dromedicas.service.SucursalService;
import com.dromedicas.service.TipoTransaccionService;
import com.dromedicas.service.TransaccionService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Stateless
public class ClienteRecibirTxAcumulacionRs {

	Logger log = Logger.getLogger(ClienteRecibirTxAcumulacionRs.class);

	@EJB
	private SucursalService sucursalService;	
	
	@EJB
	private AfiliadoService afiliadoService;
	
	@EJB
	private TipoTransaccionService tipoTxService;
	
	@EJB
	private TransaccionService txService;
		
	@EJB
	private OperacionPuntosService puntosService;
	
	@EJB
	private EmpresaService empresaService;

	private String servicio = "wsjson/fptransacciones";
	
	
}
