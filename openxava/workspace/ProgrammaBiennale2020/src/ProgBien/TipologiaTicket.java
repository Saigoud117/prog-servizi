package ProgBien;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

import Filters.*;

@Entity
@Tabs({
	@Tab(
		//name="Current",
		filter=CurrentUserTypeTicketFilter.class,
		//properties="servizio, struttura, descrizione, codicesicurezza, datainizio, datafine, nstruttura, nservizio, inquadrato",
		baseCondition="${admin} = ? or ${admin} = ?"
	)
})
public class TipologiaTicket {

	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Required
	private String descrizione;
	
	@Column(name = "admin")
    private boolean admin;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}	
		
}
