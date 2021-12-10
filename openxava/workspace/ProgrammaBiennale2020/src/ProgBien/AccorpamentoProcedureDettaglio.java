package ProgBien;

import javax.persistence.*;
import org.openxava.annotations.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class AccorpamentoProcedureDettaglio {
	
	@ReferenceView("Simple")
	@ManyToOne // Lazy fetching produces a fails on removing a detail from invoice
	private AccorpamentoProcedure accorpamento;
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Required
    @ManyToOne
    @SearchListCondition("${stato.key} like 'N'")
    @ReferenceView("Accorpamento")
	 @JoinColumns({
		 @JoinColumn(
	            name = "Anno0",
	            referencedColumnName = "anno0"),
		 @JoinColumn(
	            name = "CodiceInterno",
	            referencedColumnName = "codiceinterno")
	    })
    private Procedure procedure;
		
	@Column(name = "Principale", nullable = false)
	private boolean principale;

	public AccorpamentoProcedure getAccorpamento() {
		return accorpamento;
	}

	public void setAccorpamento(AccorpamentoProcedure accorpamento) {
		this.accorpamento = accorpamento;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Procedure getProcedure() {
		return procedure;
	}

	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}

	public boolean isPrincipale() {
		return principale;
	}

	public void setPrincipale(boolean principale) {
		this.principale = principale;
	}
	
}
