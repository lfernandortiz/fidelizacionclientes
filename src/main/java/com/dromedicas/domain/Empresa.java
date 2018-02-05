package com.dromedicas.domain;

import java.io.Serializable;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the empresa database table.
 * 
 */
@ManagedBean(name="empresa")
@SessionScoped
@Entity
@NamedQuery(name="Empresa.findAll", query="SELECT e FROM Empresa e order by e.nombreEmpresa")
@XmlRootElement
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idempresa;

	private String direccion;

	private String email;	
	

	@Column(name="email_notificaciones")
	private String emailNotificaciones;

	private String nit;

	@Column(name="nombre_empresa")
	private String nombreEmpresa;

	private String telefono;

	//bi-directional many-to-one association to Contrato
	@OneToMany(fetch = FetchType.EAGER,  mappedBy="empresa", cascade = CascadeType.ALL)
	private Set<Contrato> contratos;

	//bi-directional many-to-one association to Sucursal
	@OneToMany(fetch = FetchType.EAGER, mappedBy="empresa" )
	private Set<Sucursal> sucursals;

	//bi-directional many-to-one association to Vendedor
	@OneToMany(fetch = FetchType.EAGER, mappedBy="empresa", cascade = CascadeType.ALL)
	private Set<Vendedor> vendedors;

	public Empresa() {
	}

	public int getIdempresa() {
		return this.idempresa;
	}

	public void setIdempresa(int idempresa) {
		this.idempresa = idempresa;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailNotificaciones() {
		return this.emailNotificaciones;
	}

	public void setEmailNotificaciones(String emailNotificaciones) {
		this.emailNotificaciones = emailNotificaciones;
	}

	public String getNit() {
		return this.nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getNombreEmpresa() {
		return this.nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Set<Contrato> getContratos() {
		return this.contratos;
	}

	public void setContratos(Set<Contrato> contratos) {
		this.contratos = contratos;
	}

	public Contrato addContrato(Contrato contrato) {
		getContratos().add(contrato);
		contrato.setEmpresa(this);

		return contrato;
	}

	public Contrato removeContrato(Contrato contrato) {
		getContratos().remove(contrato);
		contrato.setEmpresa(null);

		return contrato;
	}

	public Set<Sucursal> getSucursals() {
		return this.sucursals;
	}

	public void setSucursals(Set<Sucursal> sucursals) {
		this.sucursals = sucursals;
	}

	public Sucursal addSucursal(Sucursal sucursal) {
		getSucursals().add(sucursal);
		sucursal.setEmpresa(this);

		return sucursal;
	}

	public Sucursal removeSucursal(Sucursal sucursal) {
		getSucursals().remove(sucursal);
		sucursal.setEmpresa(null);

		return sucursal;
	}

	public Set<Vendedor> getVendedors() {
		return this.vendedors;
	}

	public void setVendedors(Set<Vendedor> vendedors) {
		this.vendedors = vendedors;
	}

	public Vendedor addVendedor(Vendedor vendedor) {
		getVendedors().add(vendedor);
		vendedor.setEmpresa(this);

		return vendedor;
	}

	public Vendedor removeVendedor(Vendedor vendedor) {
		getVendedors().remove(vendedor);
		vendedor.setEmpresa(null);

		return vendedor;
	}
	
	

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((emailNotificaciones == null) ? 0 : emailNotificaciones.hashCode());
		result = prime * result + idempresa;
		result = prime * result + ((nit == null) ? 0 : nit.hashCode());
		result = prime * result + ((nombreEmpresa == null) ? 0 : nombreEmpresa.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (emailNotificaciones == null) {
			if (other.emailNotificaciones != null)
				return false;
		} else if (!emailNotificaciones.equals(other.emailNotificaciones))
			return false;
		if (idempresa != other.idempresa)
			return false;
		if (nit == null) {
			if (other.nit != null)
				return false;
		} else if (!nit.equals(other.nit))
			return false;
		if (nombreEmpresa == null) {
			if (other.nombreEmpresa != null)
				return false;
		} else if (!nombreEmpresa.equals(other.nombreEmpresa))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Empresa [idempresa=" + idempresa + ", direccion=" + direccion + ", email=" + email
				+ ", emailNotificaciones=" + emailNotificaciones + ", nit=" + nit + ", nombreEmpresa=" + nombreEmpresa
				+ ", telefono=" + telefono + ", contratos=" + contratos + ", sucursals=" + sucursals + ", vendedors="
				+ vendedors + "]";
	}
	
	

}