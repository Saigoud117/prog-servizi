package ProgBien;

import java.math.*;
import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.hibernate.envers.*;
import org.openxava.annotations.*;
import org.openxava.util.*;

@Entity
@Audited
public class CopertureRicompreseProgramma implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ReferenceView("Simple")
	@ManyToOne // Lazy fetching produces a fails on removing a detail from invoice
	private ProcedureProgramma procedura;	
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Required
	private Integer anno;
	
	@Stereotype("MONEY")
	@Column(precision = 16, scale = 2)
    private java.math.BigDecimal vincolate;

	@Stereotype("MONEY")
	@Column(precision = 16, scale = 2)
    private java.math.BigDecimal bilancio;
	
	@Stereotype("MONEY")
	@Column(precision = 16, scale = 2)
    private java.math.BigDecimal stanziato;
	
	@Stereotype("MONEY")
	@Column(precision = 16, scale = 2)
    private java.math.BigDecimal nonStanziato;

	@Stereotype("MONEY")
	@Column(precision = 16, scale = 2)
    private java.math.BigDecimal altro;

	@Stereotype("MONEY")
	@Column(precision = 16, scale = 2)
    private java.math.BigDecimal trasfimmo;

	@Stereotype("MONEY")
	@Column(precision = 16, scale = 2)
    private java.math.BigDecimal mutuo;

	@Stereotype("MONEY")
	@Column(precision = 16, scale = 2)
    private java.math.BigDecimal privati;

	@Stereotype("MONEY")
	@Column(precision = 16, scale = 2)
    private java.math.BigDecimal patrimonio;
	
	@Stereotype("MONEY")
	@Depends("vincolate, bilancio, altro, trasfimmo, mutuo, privati, patrimonio")  // When the user changes product or quantity
	public java.math.BigDecimal getAmount() {		
	    return (vincolate == null?BigDecimal.ZERO:vincolate)
	    		.add(bilancio == null?BigDecimal.ZERO:bilancio)
	    		.add(altro == null?BigDecimal.ZERO:altro)
	    		.add(trasfimmo == null?BigDecimal.ZERO:trasfimmo)
	    		.add(mutuo == null?BigDecimal.ZERO:mutuo)
	    		.add(privati == null?BigDecimal.ZERO:privati)
	    		.add(patrimonio == null?BigDecimal.ZERO:patrimonio).setScale(2, RoundingMode.UP); // this property is recalculated and redisplayed
	}
	
	public void saveTotali() throws Exception {
		BigDecimal tot = getAmount();
	    
		setTotale(tot);
	}
	
	@PrePersist
	@PreUpdate
	private void syncTotali() throws Exception { 
		if (anno == 0 || anno > (Calendar.getInstance().get(Calendar.YEAR) + 10) || anno < (Calendar.getInstance().get(Calendar.YEAR)) - 10) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "year_must_be_valid"
	            )
	        );
		}
		
		if (procedura.getRicompreso().toString() == "No") { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "coperture_ricomprese_invalid"
	            )
	        );
		}
		
		saveTotali();
	}	
	
	@Hidden
	@Stereotype("MONEY")
	@Column(precision = 16, scale = 2)
	private java.math.BigDecimal totale;	

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public java.math.BigDecimal getVincolate() {
		return vincolate;
	}

	public void setVincolate(java.math.BigDecimal vincolate) {
		this.vincolate = vincolate;
	}

	public java.math.BigDecimal getBilancio() {
		return bilancio;
	}

	public void setBilancio(java.math.BigDecimal bilancio) {
		this.bilancio = bilancio;
	}

	public java.math.BigDecimal getStanziato() {
		return stanziato;
	}

	public void setStanziato(java.math.BigDecimal stanziato) {
		this.stanziato = stanziato;
	}

	public java.math.BigDecimal getNonStanziato() {
		return nonStanziato;
	}

	public void setNonStanziato(java.math.BigDecimal nonStanziato) {
		this.nonStanziato = nonStanziato;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public java.math.BigDecimal getAltro() {
		return altro;
	}

	public void setAltro(java.math.BigDecimal altro) {
		this.altro = altro;
	}

	public java.math.BigDecimal getTrasfimmo() {
		return trasfimmo;
	}

	public void setTrasfimmo(java.math.BigDecimal trasfimmo) {
		this.trasfimmo = trasfimmo;
	}

	public java.math.BigDecimal getMutuo() {
		return mutuo;
	}

	public void setMutuo(java.math.BigDecimal mutuo) {
		this.mutuo = mutuo;
	}

	public java.math.BigDecimal getPrivati() {
		return privati;
	}

	public void setPrivati(java.math.BigDecimal privati) {
		this.privati = privati;
	}

	public java.math.BigDecimal getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(java.math.BigDecimal patrimonio) {
		this.patrimonio = patrimonio;
	}

	public java.math.BigDecimal getTotale() {
		return totale;
	}

	public void setTotale(java.math.BigDecimal totale) {
		this.totale = totale;
	}  
	
	public ProcedureProgramma getProcedura() {
		return procedura;
	}

	public void setProcedura(ProcedureProgramma procedura) {
		this.procedura = procedura;
	}
}
