package com.dromedicas.servicio.rest;

import java.util.ArrayList;
import java.util.List;

import com.dromedicas.domain.Transaccion;

public class UtilTransactionWrap {
	
	private List<Transaccion> txList = new ArrayList<Transaccion>();
			
	private	List<String> idsList = new ArrayList<String>();
	
	public UtilTransactionWrap(){
		
	}

	public List<Transaccion> getTxList() {
		return txList;
	}

	public void setTxList(List<Transaccion> txList) {
		this.txList = txList;
	}

	public List<String> getIdsList() {
		return idsList;
	}

	public void setIdsList(List<String> idsList) {
		this.idsList = idsList;
	}
	
}
