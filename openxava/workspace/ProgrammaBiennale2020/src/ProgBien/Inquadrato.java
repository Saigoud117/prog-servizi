package ProgBien;

import javax.persistence.*;

import org.openxava.annotations.*;

import Filters.*;

import java.io.*;
import java.util.*;

@Entity
@Table(name = " Inquadrato")
@Views({
	@View(name="Dashbord", members="dipendenti, servizi")
})
@Tabs({
	@Tab(
		name="Referenti",
		filter=CurrentUserServizioStrutturaFilter.class,
		editors ="List",
		baseCondition="(? = 1 or ${servizi.servizio} in (?)) and (? = 1 or ${servizi.struttura} in (?)) and ${profilo} is not null and ${datainizio} < now() and ${datafine} > now()",
		properties="dipendenti.cf, dipendenti.nome, dipendenti.cognome, datainizio, datafine, servizi.descrizione, profilo.descrizione"
	)
})
public class Inquadrato implements Serializable {

	// @EmbeddedId
    // private InquadratoKey id;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Required
	@Column(name = "DataInizio", length = 10, nullable = false)
	private Date datainizio;
	
	@Required
	@Column(name = "DataFine", length = 10, nullable = false)
	private Date datafine;
	
    @Column(name = "Qualifica", length = 10, nullable = true)
    private String qualifica;

	// @ManyToOne
    // @MapsId("servizi") //This is the name of attr in EmployerDeliveryAgentPK class
    // @JoinColumn(name = "Id_servizio")
    // private Servizi servizi;

    // @ManyToOne
    // @MapsId("cf")
    // @JoinColumn(name = "CF")
    // private Dipendenti dipendenti; 
    
    @Id
    @ManyToOne
    @ReferenceView("Simple")
    @JoinColumn(name = "Id_servizio", referencedColumnName = "oid")
    private Servizi servizi;

    @Id
    @ManyToOne
    @ReferenceView("Simple")
    @JoinColumn(name = "CF", referencedColumnName = "cf")
    private Dipendenti dipendenti; 
    
	// public InquadratoKey getId() {
	// 	return id;
	// }

	// public void setId(InquadratoKey id) {
	// 	this.id = id;
	// }
    
    @ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="key, descrizione",
    		condition="e.gruppicodici = 'A'") // Thus the reference is displayed using a combo
    private CodiciIct profilo; // A regular Java reference

	public Date getDatainizio() {
		return datainizio;
	}

	public void setDatainizio(Date datainizio) {
		this.datainizio = datainizio;
	}

	public Date getDatafine() {
		return datafine;
	}

	public void setDatafine(Date datafine) {
		this.datafine = datafine;
	}
	
	public String getQualifica() {
		return qualifica;
	}

	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}

	// public Servizi getServizi() {
	// 	return servizi;
	// }

	// public void setServizi(Servizi servizi) {
	// 	this.servizi = servizi;
	// }

	// public Dipendenti getDipendenti() {
	// 	return dipendenti;
	// }

	// public void setDipendenti(Dipendenti dipendenti) {
	//	this.dipendenti = dipendenti;
	// }
	
	public Servizi getServizi() {
		return servizi;
	}

	public void setServizi(Servizi servizi) {
		this.servizi = servizi;
	}

	public Dipendenti getDipendenti() {
		return dipendenti;
	}

	public void setDipendenti(Dipendenti dipendenti) {
		this.dipendenti = dipendenti;
	}

	public CodiciIct getProfilo() {
		return profilo;
	}

	public void setProfilo(CodiciIct profilo) {
		this.profilo = profilo;
	}	
}