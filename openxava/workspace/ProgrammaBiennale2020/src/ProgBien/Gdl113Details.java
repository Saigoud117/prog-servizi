package ProgBien;

import java.math.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;
import org.openxava.jpa.*;

import Calculators.*;
import actions.*;

@Entity
public class Gdl113Details {

	@ReferenceView("Simple")
	@ManyToOne // Lazy fetching produces a fails on removing a detail from invoice
	private Gdl113 gdl;
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Required
	@OnChange(UpdateTotaleGdl.class)
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(
		descriptionProperties="key, descrizione",
		condition="e.gruppicodici = 'O'"
    ) // Thus the reference is displayed using a combo
    private CodiciIct fase; // A regular Java reference
	    
	@Required
	@OnChange(UpdateTotaleGdl.class)
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(
		descriptionProperties="key, descrizione, coefficiente",
		depends="this.fase",
		condition="e.gruppicodici in (select c.tipo2 from CodiciIct c where c.key = ?)") // Thus the reference is displayed using a combo
    private CodiciIct ruolo; // A regular Java reference

    @ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    //@DescriptionsList(descriptionProperties="cf, nome, cognome") // Thus the reference is displayed using a combo
    @ReferenceView("Simple")
    private Dipendenti dipendenti; // A regular Java reference
    
    @Hidden
    @Stereotype("MONEY")
    @Column(name = "Totale", length = 16, precision = 12, nullable = true)
    private BigDecimal amount;
    
    @Required
    @Column(name = "Percentuale", length = 5, precision = 3, nullable = false)
    @DefaultValueCalculator(GdlPercentageCalculator.class)
    @OnChange(UpdateTotaleGdl.class)
    private BigDecimal percentuale;
	
	@Stereotype("MONEY")
	@Depends("this.gdl, this.fase.key, this.ruolo.key, this.percentuale")  // When the user changes product or quantity
	public BigDecimal getTotaleCalculated() {
		if (this.gdl == null || this.fase == null || this.ruolo == null)
			return new BigDecimal(0) ;
		else
		{
			EntityManager em = XPersistence.createManager();
			CodiciIct r = (CodiciIct) em.createQuery(
			        "select c from CodiciIct c where c.key = :key")  // JPQL query (1)
			    .setParameter("key", this.ruolo.getKey())
			    .getSingleResult();  // To obtain one single entity (2)
			em.close(); // You have to close the manager
						
			BigDecimal coefficiente = r.getCoefficiente().multiply(BigDecimal.valueOf(0.01));
			
			BigDecimal percent = percentuale.multiply(BigDecimal.valueOf(0.01));
			
			ProcedureDefinitive p = this.gdl.getProcedure();
				
			if (percent != null)
			{
				if (this.fase.getKey().equals("O1P"))
					if (p.getGdl113Program() != null && (p.isFondoenable() || p.isQuotagdlenable() || p.isProgramenable()))
						return p.getGdl113Program().multiply(coefficiente).multiply(percent).setScale(2, RoundingMode.UP);
					else
						return new BigDecimal(0);
				else if (this.fase.getKey().equals("O2Q"))
					if (p.getGdl113Affida() != null && (p.isFondoenable() || p.isQuotagdlenable() || p.isAffidaenable()))
						return p.getGdl113Affida().multiply(coefficiente).multiply(percent).setScale(2, RoundingMode.UP);
					else
						return new BigDecimal(0);
				else if (this.fase.getKey().equals("O3R"))
					if (p.getGdl113Exec() != null && (p.isFondoenable() || p.isQuotagdlenable() || p.isExecenable()))
					{
						if (p.isQuotacollaudoenable())
							return (p.getGdl113Exec().add(p.getGdl113Collaudo())).multiply(coefficiente).multiply(percent).setScale(2, RoundingMode.UP);	
						else
							return p.getGdl113Exec().multiply(coefficiente).multiply(percent).setScale(2, RoundingMode.UP);
					}
					else
						return new BigDecimal(0);
				else
					return coefficiente;
			}
			else
				return new BigDecimal(0);
		}
	}	
	
	@PreUpdate // Just before updating the database
	@PrePersist // Just before inserting first time
	public void saveTotali() throws Exception {
		BigDecimal tot = getTotaleCalculated();
		setAmount(tot);
	}	

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public CodiciIct getFase() {
		return fase;
	}

	public void setFase(CodiciIct fase) {
		this.fase = fase;
	}

	public CodiciIct getRuolo() {
		return ruolo;
	}

	public void setRuolo(CodiciIct ruolo) {
		this.ruolo = ruolo;
	}

	public Dipendenti getDipendenti() {
		return dipendenti;
	}

	public void setDipendenti(Dipendenti dipendenti) {
		this.dipendenti = dipendenti;
	}
		

	public BigDecimal getPercentuale() {
		return percentuale;
	}

	public void setPercentuale(BigDecimal percentuale) {
		this.percentuale = percentuale;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Gdl113 getGdl() {
		return gdl;
	}

	public void setGdl(Gdl113 gdl) {
		this.gdl = gdl;
	}		

}
