package ProgBien;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
public class CronoprogrammaDettaglioFaseStraodinaria {
	
	@ReferenceView("Simple")
	@ManyToOne // Lazy fetching produces a fails on removing a detail from invoice
	private Cronoprogramma cronoprogramma;
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Required
	@ManyToOne
	@ReferenceView("Simple")
	@JoinColumns({
	 @JoinColumn(
	           name = "CF",
	           referencedColumnName = "cf"),
	 @JoinColumn(
	           name = "Nome",
	         referencedColumnName = "nome"),
	 @JoinColumn(
	           name = "Cognome",
	         referencedColumnName = "cognome")
	    })
    private Dipendenti dipendenti; // A regular Java reference

	public Cronoprogramma getCronoprogramma() {
		return cronoprogramma;
	}

	public void setCronoprogramma(Cronoprogramma cronoprogramma) {
		this.cronoprogramma = cronoprogramma;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Dipendenti getDipendenti() {
		return dipendenti;
	}

	public void setDipendenti(Dipendenti dipendenti) {
		this.dipendenti = dipendenti;
	}
	
}
