package ProgBien;

import javax.persistence.*;
import org.openxava.annotations.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class AccorpamentoProcedureProgrammaDettaglio {
	
	@ReferenceView("Simple")
	@ManyToOne // Lazy fetching produces a fails on removing a detail from invoice
	private AccorpamentoProcedureProgramma accorpamento;
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Required
    @ManyToOne
    @SearchListCondition("${stato.key} like 'N' or ${stato.key} like 'M'")
    @ReferenceView("Accorpamento")
    private ProcedureProgramma procedure;
		
	@Column(name = "Principale", nullable = false)
	private boolean principale;

	public AccorpamentoProcedureProgramma getAccorpamento() {
		return accorpamento;
	}

	public void setAccorpamento(AccorpamentoProcedureProgramma accorpamento) {
		this.accorpamento = accorpamento;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public ProcedureProgramma getProcedure() {
		return procedure;
	}

	public void setProcedure(ProcedureProgramma procedure) {
		this.procedure = procedure;
	}

	public boolean isPrincipale() {
		return principale;
	}

	public void setPrincipale(boolean principale) {
		this.principale = principale;
	}
	
}
