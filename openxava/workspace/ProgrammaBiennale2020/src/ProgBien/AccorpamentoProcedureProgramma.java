package ProgBien;


import java.util.*;

import javax.persistence.*;
import org.openxava.annotations.*;

import org.hibernate.annotations.GenericGenerator;

@Views({
@View(name="Simple", // This view is used only when “Simple” is specified
members="descrizione" // Shows only number and name in the same line
)})
@Entity
public class AccorpamentoProcedureProgramma {
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Required
    @Column(name = "Descrizione", length = 256, nullable = false)
    private String descrizione;
	
	@OneToMany (mappedBy="accorpamento", cascade=CascadeType.REMOVE)
    @AddAction("AccorpamentoProcedureProgramma.addAccorpamentoProcedureProgrammaDettaglio") // @AddAction instead
	@ListProperties("procedure.descrizione, principale")
    private Collection<AccorpamentoProcedureProgrammaDettaglio> accorpamentoProcedureDettaglio;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Collection<AccorpamentoProcedureProgrammaDettaglio> getAccorpamentoProcedureDettaglio() {
		return accorpamentoProcedureDettaglio;
	}

	public void setAccorpamentoProcedureDettaglio(
			Collection<AccorpamentoProcedureProgrammaDettaglio> accorpamentoProcedureDettaglio) {
		this.accorpamentoProcedureDettaglio = accorpamentoProcedureDettaglio;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}		

}
