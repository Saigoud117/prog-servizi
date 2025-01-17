package ProgBien;

import java.math.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.hibernate.envers.*;
import org.openxava.annotations.*;
import org.openxava.util.*;

@Entity
@Views({
@View(members="procedura; descrizionevoce; tipologia; sottotipologia; tipoCopertura; caratterizzazioneCopertura; capitolo; pdc; importonetto; aliquotaiva; importoIvaCalculated; totaleCalculated; percentualeA1; percentualeA2; ")})
@Tabs({
	@Tab(
		properties="descrizionevoce, tipologia.nome, sottotipologia.nome, tipoCopertura.nome, caratterizzazioneCopertura.nome, capitolo, pdc.codice, importonetto, aliquotaiva, percentualeA1, percentualeA2, importoIvaCalculated, totaleCalculated"
	)
})
@Audited
public class QuadroEconomicoDefinitivo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ReferenceView("Simple")
	@ManyToOne // Lazy fetching produces a fails on removing a detail from invoice
	private ProcedureDefinitive procedura;		

	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Required
    @Column(name = "DescrizioneVoce", length = 256, nullable = false)
    private String descrizionevoce;
	
	@Required
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=false) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="nome",
    		condition="e.keyPrincipale is null") // Thus the reference is displayed using a combo
    private TipologiaVoceQuadroEconomico tipologia; // A regular Java reference
	
	@Required
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=false) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="nome",
    		depends="this.tipologia",
    		condition="e.keyPrincipale.key = ?") // Thus the reference is displayed using a combo
    private TipologiaVoceQuadroEconomico sottotipologia; // A regular Java reference
	
	@Required
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(descriptionProperties="descrizione") // Thus the reference is displayed using a combo
    private TipologiaCoperturaFinanziaria tipoCopertura; // A regular Java reference
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="nome",
    		depends="this.tipoCopertura",
    		condition="e.tipologia.key = ?") // Thus the reference is displayed using a combo) // Thus the reference is displayed using a combo
    private CatatterizzazioneTipologia caratterizzazioneCopertura; // A regular Java reference

	@Column(name = "Capitolo", length = 14, nullable = true)
	private String capitolo;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
	@ReferenceView("Simple")
    private Pdc pdc; // A regular Java reference
	
	@Required
	@Stereotype("MONEY")
    @Column(name = "ImportoNetto", precision = 16, scale = 2)
    private BigDecimal importonetto;
	
	@Column(name = "AliquotaIva", precision = 10, scale = 2, nullable = true)
    private BigDecimal aliquotaiva;
	
	@Stereotype("MONEY")
	@Hidden
	@Column(precision = 16, scale = 2)
    private BigDecimal importoiva;
	
	@Stereotype("MONEY")
	@Hidden
	@Column(precision = 16, scale = 2)
    private BigDecimal amount;
	
	@Stereotype("MONEY")
    @Depends("importonetto, aliquotaiva")
    public BigDecimal getImportoIvaCalculated() {
    	
		if (aliquotaiva == null)
			return new BigDecimal("0");
		else
			return importonetto.multiply(aliquotaiva).multiply(BigDecimal.valueOf(0.01)).setScale(2, RoundingMode.UP);
    }

	@Stereotype("MONEY")
	@Depends("importonetto, aliquotaiva")  // When the user changes product or quantity
	public BigDecimal getTotaleCalculated() {
		if (aliquotaiva == null)
			return importonetto;
		else
		{
			BigDecimal importoiva = getImportoIvaCalculated();
			return importonetto.add(importoiva);
		}
	}
	
	@Column(name = "PercentualeA1", precision = 10, scale = 2, nullable = true)
    private BigDecimal percentualeA1;
    
    @Column(name = "PercentualeA2", precision = 10, scale = 2, nullable = true)
    private BigDecimal percentualeA2;
	
	public void saveTotali() throws Exception {
		BigDecimal tot = getTotaleCalculated();
		BigDecimal ivaTot = getImportoIvaCalculated();
	    
		setAmount(tot);
		setImportoiva(ivaTot);
	}
	
	@PrePersist
	private void syncTotali() throws Exception { 
		
		if ((tipoCopertura.getKey().equals("BIL") || tipoCopertura.getKey().equals("CAP")) && (caratterizzazioneCopertura == null) ) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "must_specify_characterization"
	            )
	        );
	    }
		
		if (percentualeA1 == null || percentualeA2 == null)
		{
			// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "must_indicate_percent"
	            )
	        );
		} 
		else if (percentualeA1.add(percentualeA2).compareTo(new BigDecimal(100)) == 1)
		{
			// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "sum_percent_must_be_100"
	            )
	        );
		}
		
		saveTotali();
	}	
	
	@PreUpdate
	private void validate() throws Exception {
		
		if ((tipoCopertura.getKey().equals("BIL") || tipoCopertura.getKey().equals("CAP")) && (caratterizzazioneCopertura == null) ) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "must_specify_characterization"
	            )
	        );
	    }
		
		if (percentualeA1 == null || percentualeA2 == null)
		{
			// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "must_indicate_percent"
	            )
	        );
		} 
		else if (percentualeA1.add(percentualeA2).compareTo(new BigDecimal(100)) == 1)
		{
			// The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "sum_percent_must_be_100"
	            )
	        );
		}
		
		saveTotali();
	}	
	

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getDescrizionevoce() {
		return descrizionevoce;
	}

	public void setDescrizionevoce(String descrizionevoce) {
		this.descrizionevoce = descrizionevoce;
	}	

	public TipologiaVoceQuadroEconomico getTipologia() {
		return tipologia;
	}

	public void setTipologia(TipologiaVoceQuadroEconomico tipologia) {
		this.tipologia = tipologia;
	}

	public TipologiaVoceQuadroEconomico getSottotipologia() {
		return sottotipologia;
	}

	public void setSottotipologia(TipologiaVoceQuadroEconomico sottotipologia) {
		this.sottotipologia = sottotipologia;
	}

	public TipologiaCoperturaFinanziaria getTipoCopertura() {
		return tipoCopertura;
	}

	public void setTipoCopertura(TipologiaCoperturaFinanziaria tipoCopertura) {
		this.tipoCopertura = tipoCopertura;
	}

	public CatatterizzazioneTipologia getCaratterizzazioneCopertura() {
		return caratterizzazioneCopertura;
	}

	public void setCaratterizzazioneCopertura(CatatterizzazioneTipologia caratterizzazioneCopertura) {
		this.caratterizzazioneCopertura = caratterizzazioneCopertura;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCapitolo() {
		return capitolo;
	}

	public void setCapitolo(String capitolo) {
		this.capitolo = capitolo;
	}	

	public Pdc getPdc() {
		return pdc;
	}

	public void setPdc(Pdc pdc) {
		this.pdc = pdc;
	}

	public BigDecimal getImportonetto() {
		return importonetto;
	}

	public void setImportonetto(BigDecimal importonetto) {
		this.importonetto = importonetto;
	}	

	public BigDecimal getAliquotaiva() {
		return aliquotaiva;
	}

	public void setAliquotaiva(BigDecimal aliquotaiva) {
		this.aliquotaiva = aliquotaiva;
	}	

	public BigDecimal getImportoiva() {
		return importoiva;
	}

	public void setImportoiva(BigDecimal importoiva) {
		this.importoiva = importoiva;
	}	

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public ProcedureDefinitive getProcedura() {
		return procedura;
	}

	public void setProcedura(ProcedureDefinitive procedura) {
		this.procedura = procedura;
	}

	public BigDecimal getPercentualeA1() {
		return percentualeA1;
	}

	public void setPercentualeA1(BigDecimal percentualeA1) {
		this.percentualeA1 = percentualeA1;
	}

	public BigDecimal getPercentualeA2() {
		return percentualeA2;
	}

	public void setPercentualeA2(BigDecimal percentualeA2) {
		this.percentualeA2 = percentualeA2;
	}
}
