package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Emailcampania;

public interface EmailcampaniaDao {
	
	public List<Emailcampania> findAllEmailcampanias();

	public Emailcampania obtenerEmailcampaniaById(Emailcampania instance);

	public void insertEmailcampania(Emailcampania instance);

	public void updateEmailcampania(Emailcampania instance);

	public void deleteEmailcampania(Emailcampania instance);
}
