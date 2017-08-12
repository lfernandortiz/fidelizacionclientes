package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Smscampania;

public interface SmscampaniaDao {
	
	public List<Smscampania> findAllSmscampanias();

	public Smscampania obtenerSmscampaniaById(Smscampania instance);

	public void insertSmscampania(Smscampania instance);

	public void updateSmscampania(Smscampania instance);

	public void deleteSmscampania(Smscampania instance);

}
