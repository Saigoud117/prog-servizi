package ProgBien;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

public class IncentiviDialog {
	
	@Required
	@ReadOnly
    private String descrizionevoce;
	
	@ReadOnly
	@Required
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=false) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="nome") // Thus the reference is displayed using a combo
    private TipologiaVoceQuadroEconomico tipologia; // A regular Java reference
	
	@ReadOnly
	@Required
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=false) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="nome") // Thus the reference is displayed using a combo
    private TipologiaVoceQuadroEconomico sottotipologia; // A regular Java reference
	
	@ReadOnly
	@Required
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(descriptionProperties="descrizione") // Thus the reference is displayed using a combo
    private TipologiaCoperturaFinanziaria tipoCopertura; // A regular Java reference
	
	@ReadOnly
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="nome") // Thus the reference is displayed using a combo) // Thus the reference is displayed using a combo
    private CatatterizzazioneTipologia caratterizzazioneCopertura; // A regular Java reference
	
	@Stereotype("MONEY")
	@ReadOnly
	@Column(precision = 16, scale = 2)
    private BigDecimal amount;
	
	@ReadOnly
	@Column(precision = 10, scale = 2)
	private BigDecimal percentualeA1;
	
	@ReadOnly
	@Column(precision = 10, scale = 2)
	private BigDecimal percentualeA2;

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
