package ProgBien;

import java.math.*;
import java.util.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

import Filters.*;

@Entity
@Views({
@View(
	members="procedure; gruppoDiLavoro[gdl113details; totaleGdl113Programmazione, totaleGdl113Affidamento, totaleGdl113Esecuzione;];" // Shows only number and name in the same line
),
@View(name="Simple", // This view is used only when “Simple” is specified
members="procedure;" // Shows only number and name in the same line
)
})
@Tab(
	//name="Current",
	filter=CurrentUserServizioStrutturaFilter.class,
	properties="procedure.anno0, procedure.codiceinterno, procedure.descrizione, procedure.servizi.servizio, procedure.servizi.struttura",
	baseCondition="(? = 1 or ${procedure.servizi.servizio} in (?)) and (? = 1 or ${procedure.servizi.struttura} in (?)) and ${procedure.servizi.datainizio} < now() and ${procedure.servizi.datafine} > now()"
)
public class Gdl113 {

	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;

	@Required
	@NoFrame
	@ManyToOne(fetch=FetchType.LAZY)
    @ReferenceView("Custom")
    private ProcedureDefinitive procedure;
	
	@OneToMany (mappedBy="gdl", cascade=CascadeType.REMOVE)
    private Collection<Gdl113Details> gdl113details;
	
	@Stereotype("MONEY")
    @Depends("quadroeconomico")
    public BigDecimal getTotaleGdl113Programmazione() {
    			
    	BigDecimal sum = BigDecimal.ZERO;   	
    	for (Gdl113Details g: gdl113details) {
    		if (g.getFase().getKey().equals("O1P"))
			{
				sum = sum.add(g.getTotaleCalculated());
			}
		}    	
        return sum;
    }
	
	@Stereotype("MONEY")
    @Depends("quadroeconomico")
    public BigDecimal getTotaleGdl113Affidamento() {
    			
    	BigDecimal sum = BigDecimal.ZERO;   	
    	for (Gdl113Details g: gdl113details) {
    		if (g.getFase().getKey().equals("O2Q"))
			{
				sum = sum.add(g.getTotaleCalculated());
			}
		}    	
        return sum;
    }
	
	@Stereotype("MONEY")
    @Depends("quadroeconomico")
    public BigDecimal getTotaleGdl113Esecuzione() {
    			
    	BigDecimal sum = BigDecimal.ZERO;   	
    	for (Gdl113Details g: gdl113details) {
    		if (g.getFase().getKey().equals("O3R"))
			{
				sum = sum.add(g.getTotaleCalculated());
			}
		}    	
        return sum;
    }	
	
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public ProcedureDefinitive getProcedure() {
		return procedure;
	}

	public void setProcedure(ProcedureDefinitive procedure) {
		this.procedure = procedure;
	}
	
	public Collection<Gdl113Details> getGdl113details() {
		return gdl113details;
	}

	public void setGdl113details(Collection<Gdl113Details> gdl113details) {
		this.gdl113details = gdl113details;
	}
}
