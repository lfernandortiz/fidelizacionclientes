package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Globalstat;

public interface GlobalstatDao {

	public List<Globalstat> findAllGlobalstats();

	public Globalstat obtenerGlobalstatById(Globalstat instance);

	public void insertGlobalstat(Globalstat instance);

	public void updateGlobalstat(Globalstat instance);

	public void deleteGlobalstat(Globalstat instance);
}
