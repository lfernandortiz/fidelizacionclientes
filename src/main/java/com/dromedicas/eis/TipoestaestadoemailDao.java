package com.dromedicas.eis;

import java.util.List;

import com.dromedicas.domain.Tipoestaestadoemail;

public interface TipoestaestadoemailDao {
	
	public List<Tipoestaestadoemail> findAllTipoestaestadoemails();

	public Tipoestaestadoemail obtenerTipoestaestadoemailById(Tipoestaestadoemail instance);

	public void insertTipoestaestadoemail(Tipoestaestadoemail instance);

	public void updateTipoestaestadoemail(Tipoestaestadoemail instance);

	public void deleteTipoestaestadoemail(Tipoestaestadoemail instance);

}
