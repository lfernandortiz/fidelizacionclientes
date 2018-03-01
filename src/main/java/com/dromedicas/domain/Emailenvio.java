package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the emailenvio database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Emailenvio.findAll", query="SELECT e FROM Emailenvio e")})
public class Emailenvio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idemailenvio;

	private int estadoemail;
	
	@Column(length=60)
	private String email;
	
	private String mensaje;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaenvio;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaestado;

	//bi-directional many-to-one association to Emailcampania
	@OneToMany(mappedBy="emailenvio")
	private Set<Emailcampania> emailcampanias;

	//bi-directional many-to-one association to Afiliado
	@ManyToOne
	@JoinColumn(name="afiliadoid")
	private Afiliado afiliado;

	//bi-directional many-to-many association to Campania
	@ManyToMany
	@JoinTable(
		name="emailcampania"
		, joinColumns={
			@JoinColumn(name="idemail")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idcampania")
			}
		)
	private Set<Campania> campanias1;

	//bi-directional many-to-many association to Campania
	@ManyToMany
	@JoinTable(
		name="emailcampania"
		, joinColumns={
			@JoinColumn(name="idemail")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idcampania")
			}
		)
	private Set<Campania> campanias2;

	//bi-directional many-to-many association to Campania
	@ManyToMany
	@JoinTable(
		name="emailcampania"
		, joinColumns={
			@JoinColumn(name="idemail")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idcampania")
			}
		)
	private Set<Campania> campanias3;

	//bi-directional many-to-one association to Tipoemail
	@ManyToOne
	@JoinColumn(name="tipoemail")
	private Tipoemail tipoemailBean;

	//bi-directional many-to-many association to Tipoestaestadoemail
	@ManyToMany
	@JoinTable(
		name="emailestado"
		, joinColumns={
			@JoinColumn(name="idemailenvio")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idtipoestadoemail")
			}
		)
	private Set<Tipoestaestadoemail> tipoestaestadoemails1;

	//bi-directional many-to-many association to Tipoestaestadoemail
	@ManyToMany
	@JoinTable(
		name="emailestado"
		, joinColumns={
			@JoinColumn(name="idemailenvio")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idtipoestadoemail")
			}
		)
	private Set<Tipoestaestadoemail> tipoestaestadoemails2;

	//bi-directional many-to-one association to Transaccion
	@ManyToOne
	@JoinColumn(name="idtransaccion")
	private Transaccion transaccion;

	//bi-directional many-to-one association to Emailestado
	@OneToMany(mappedBy="emailenvio")
	private Set<Emailestado> emailestados;

	//bi-directional one-to-one association to Globalstat
	@OneToOne(mappedBy="emailenvio")
	private Globalstat globalstat;

	public Emailenvio() {
	}

	public int getIdemailenvio() {
		return this.idemailenvio;
	}

	public void setIdemailenvio(int idemailenvio) {
		this.idemailenvio = idemailenvio;
	}

	public int getEstadoemail() {
		return this.estadoemail;
	}

	public void setEstadoemail(int estadoemail) {
		this.estadoemail = estadoemail;
	}

	public Date getFechaenvio() {
		return this.fechaenvio;
	}

	public void setFechaenvio(Date fechaenvio) {
		this.fechaenvio = fechaenvio;
	}

	public Date getFechaestado() {
		return this.fechaestado;
	}

	public void setFechaestado(Date fechaestado) {
		this.fechaestado = fechaestado;
	}

	public Set<Emailcampania> getEmailcampanias() {
		return this.emailcampanias;
	}

	public void setEmailcampanias(Set<Emailcampania> emailcampanias) {
		this.emailcampanias = emailcampanias;
	}

	public Emailcampania addEmailcampania(Emailcampania emailcampania) {
		getEmailcampanias().add(emailcampania);
		emailcampania.setEmailenvio(this);

		return emailcampania;
	}

	public Emailcampania removeEmailcampania(Emailcampania emailcampania) {
		getEmailcampanias().remove(emailcampania);
		emailcampania.setEmailenvio(null);

		return emailcampania;
	}

	public Afiliado getAfiliado() {
		return this.afiliado;
	}

	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}

	public Set<Campania> getCampanias1() {
		return this.campanias1;
	}

	public void setCampanias1(Set<Campania> campanias1) {
		this.campanias1 = campanias1;
	}

	public Set<Campania> getCampanias2() {
		return this.campanias2;
	}

	public void setCampanias2(Set<Campania> campanias2) {
		this.campanias2 = campanias2;
	}

	public Set<Campania> getCampanias3() {
		return this.campanias3;
	}

	public void setCampanias3(Set<Campania> campanias3) {
		this.campanias3 = campanias3;
	}

	public Tipoemail getTipoemailBean() {
		return this.tipoemailBean;
	}

	public void setTipoemailBean(Tipoemail tipoemailBean) {
		this.tipoemailBean = tipoemailBean;
	}

	public Set<Tipoestaestadoemail> getTipoestaestadoemails1() {
		return this.tipoestaestadoemails1;
	}

	public void setTipoestaestadoemails1(Set<Tipoestaestadoemail> tipoestaestadoemails1) {
		this.tipoestaestadoemails1 = tipoestaestadoemails1;
	}

	public Set<Tipoestaestadoemail> getTipoestaestadoemails2() {
		return this.tipoestaestadoemails2;
	}

	public void setTipoestaestadoemails2(Set<Tipoestaestadoemail> tipoestaestadoemails2) {
		this.tipoestaestadoemails2 = tipoestaestadoemails2;
	}

	public Transaccion getTransaccion() {
		return this.transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

	public Set<Emailestado> getEmailestados() {
		return this.emailestados;
	}

	public void setEmailestados(Set<Emailestado> emailestados) {
		this.emailestados = emailestados;
	}

	public Emailestado addEmailestado(Emailestado emailestado) {
		getEmailestados().add(emailestado);
		emailestado.setEmailenvio(this);

		return emailestado;
	}

	public Emailestado removeEmailestado(Emailestado emailestado) {
		getEmailestados().remove(emailestado);
		emailestado.setEmailenvio(null);

		return emailestado;
	}

	public Globalstat getGlobalstat() {
		return this.globalstat;
	}

	public void setGlobalstat(Globalstat globalstat) {
		this.globalstat = globalstat;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	

}