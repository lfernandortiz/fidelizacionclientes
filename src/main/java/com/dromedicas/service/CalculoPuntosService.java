package com.dromedicas.service;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;

/**
 * EJB auxiliar que calcula los estados de los puntos
 * para un afiliado.
 * @author SOFTDromedicas
 *
 */
@Stateless
public class CalculoPuntosService {
	
	
	/**
	 * Suma dias a una objeto Date
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

}
