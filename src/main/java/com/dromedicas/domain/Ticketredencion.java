package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ticketredencion database table.
 * 
 */
@Entity
@NamedQuery(name="Ticketredencion.findAll", query="SELECT t FROM Ticketredencion t")
public class Ticketredencion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idticketredencion;

	@Lob
	@Column(length=1000000)
	private byte[] imgticket;

	//bi-directional many-to-one association to Transaccion
	@ManyToOne
	@JoinColumn(name="idtransaccion")
	private Transaccion transaccion;

	public Ticketredencion() {
	}

	public int getIdticketredencion() {
		return this.idticketredencion;
	}

	public void setIdticketredencion(int idticketredencion) {
		this.idticketredencion = idticketredencion;
	}

	public byte[] getImgticket() {
		return this.imgticket;
	}

	public void setImgticket(byte[] imgticket) {
		this.imgticket = imgticket;
	}

	public Transaccion getTransaccion() {
		return this.transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

}