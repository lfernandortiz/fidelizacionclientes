package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the referido database table.
 * 
 */
@Entity
@NamedQuery(name="Referido.findAll", query="SELECT r FROM Referido r")
public class Referido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idreferido;

	private String emailreferido;

	//bi-directional many-to-one association to Afiliado
	@ManyToOne
	@JoinColumn(name="idafiliado")
	private Afiliado afiliado;

	public Referido() {
	}

	public int getIdreferido() {
		return this.idreferido;
	}

	public void setIdreferido(int idreferido) {
		this.idreferido = idreferido;
	}

	public String getEmailreferido() {
		return this.emailreferido;
	}

	public void setEmailreferido(String emailreferido) {
		this.emailreferido = emailreferido;
	}

	public Afiliado getAfiliado() {
		return this.afiliado;
	}

	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}

}