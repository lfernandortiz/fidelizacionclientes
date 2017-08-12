package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Tipoemail;

public interface TipoemailDao {
	public List<Tipoemail> findAllTipoemails();

	public Tipoemail obtenerTipoemailById(Tipoemail instance);

	public void insertTipoemail(Tipoemail instance);

	public void updateTipoemail(Tipoemail instance);

	public void deleteTipoemail(Tipoemail instance);
}
