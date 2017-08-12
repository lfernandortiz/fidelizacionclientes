package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Transaccion;

public interface TransaccionDao {
	public List<Transaccion> findAllTransaccions();

	public Transaccion obtenerTransaccionById(Transaccion instance);

	public void insertTransaccion(Transaccion instance);

	public void updateTransaccion(Transaccion instance);

	public void deleteTransaccion(Transaccion instance);
}
