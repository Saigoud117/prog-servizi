package ProgBien;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
public class ProgImmat {
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Required
	@ManyToOne
	@ReferenceView("Servizi")
	@JoinColumns({
	 @JoinColumn(
	           name = "Sigla",
	           referencedColumnName = "sigla"),
	 @JoinColumn(
	           name = "Id_servizio",
	           referencedColumnName = "Id_servizio")
	    })
    private ProgettiIct progettiict; // A regular Java reference
	
    @Required
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(descriptionProperties="key, descrizione") // Thus the reference is displayed using a combo
    private CodiciIct codice; // A regular Java reference

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public ProgettiIct getProgettiict() {
		return progettiict;
	}

	public void setProgettiict(ProgettiIct progettiict) {
		this.progettiict = progettiict;
	}

	public CodiciIct getCodice() {
		return codice;
	}

	public void setCodice(CodiciIct codice) {
		this.codice = codice;
	}
    
    

}
