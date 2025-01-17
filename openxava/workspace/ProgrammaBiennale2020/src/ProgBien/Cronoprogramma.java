package ProgBien;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.util.*;

import ProgBien.Enumerators.*;

@Entity
@Views({
@View(
members="anno; dataInizioFaseA; dataFineFaseA; dataInizioFaseB; dataFineFaseB; dataInizioFaseC; dataFineFaseC; FaseStraordinaria[faseRiferimento; dataInizioFaseStraordinaria; dataFineFaseStraordinaria; listaRup;]; note;"
),
@View(name="Simple", // This view is used only when �Simple� is specified
members="anno, note" // Shows only number and name in the same line
),
@View(name="FaseStraordinaria", // This view is used only when �Simple� is specified
members="dataInizioFaseStraordinaria, dataFineFaseStraordinaria" // Shows only number and name in the same line
)
})
public class Cronoprogramma {
	
	@Id
	@Required
    @Column(name = "Anno", updatable=false, nullable = false)
    private Integer anno;
	
	@Required
	@Stereotype("DATETIME")
	private Date dataInizioFaseA;
	
	@Required
	@Stereotype("DATETIME")
	private Date dataFineFaseA;
	
	@Required
	@Stereotype("DATETIME")
	private Date dataInizioFaseB;
	
	@Required
	@Stereotype("DATETIME")
	private Date dataFineFaseB;
	
	@Required
	@Stereotype("DATETIME")
	private Date dataInizioFaseC;
	
	@Required
	@Stereotype("DATETIME")
	private Date dataFineFaseC;
	
	@Column(name = "FaseRiferimentoStraordinaria", nullable = true)
	private FaseRiferimento faseRiferimento;    
	
	@Stereotype("DATETIME")
	private Date dataInizioFaseStraordinaria;
	
	@Stereotype("DATETIME")
	private Date dataFineFaseStraordinaria;
	
	@OneToMany (mappedBy="cronoprogramma", cascade=CascadeType.REMOVE)
    @AddAction("Cronoprogramma.addCronoprogrammaDettaglioFaseStraodinaria") // @AddAction instead
    private Collection<CronoprogrammaDettaglioFaseStraodinaria> listaRup;	
	
	@Stereotype("TEXT_AREA") // This is for a big text, a text area or equivalent will be used
    @Column(name = "Note", length = 500, nullable = true)
    private String note;
	
	@PrePersist // Just before inserting first time
	private void validatePersist() throws Exception {
		if (dataInizioFaseStraordinaria != null && dataFineFaseStraordinaria != null && faseRiferimento == null) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "you_must_choose_a_fase"
	            )
	        );
	    }
	}
	
	@PreUpdate // Just before updating the database
	private void validateUpdate()  throws Exception {
		if (dataInizioFaseStraordinaria != null && dataFineFaseStraordinaria != null && faseRiferimento == null) { // The validation logic
	        // The validation exception from Bean Validation
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "you_must_choose_a_fase"
	            )
	        );
	    }
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public Date getDataInizioFaseA() {
		return dataInizioFaseA;
	}

	public void setDataInizioFaseA(Date dataInizioFaseA) {
		this.dataInizioFaseA = dataInizioFaseA;
	}

	public Date getDataFineFaseA() {
		return dataFineFaseA;
	}

	public void setDataFineFaseA(Date dataFineFaseA) {
		this.dataFineFaseA = dataFineFaseA;
	}

	public Date getDataInizioFaseB() {
		return dataInizioFaseB;
	}

	public void setDataInizioFaseB(Date dataInizioFaseB) {
		this.dataInizioFaseB = dataInizioFaseB;
	}

	public Date getDataFineFaseB() {
		return dataFineFaseB;
	}

	public void setDataFineFaseB(Date dataFineFaseB) {
		this.dataFineFaseB = dataFineFaseB;
	}

	public Date getDataInizioFaseC() {
		return dataInizioFaseC;
	}

	public void setDataInizioFaseC(Date dataInizioFaseC) {
		this.dataInizioFaseC = dataInizioFaseC;
	}

	public Date getDataFineFaseC() {
		return dataFineFaseC;
	}

	public void setDataFineFaseC(Date dataFineFaseC) {
		this.dataFineFaseC = dataFineFaseC;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public FaseRiferimento getFaseRiferimento() {
		return faseRiferimento;
	}

	public void setFaseRiferimento(FaseRiferimento faseRiferimento) {
		this.faseRiferimento = faseRiferimento;
	}

	public Date getDataInizioFaseStraordinaria() {
		return dataInizioFaseStraordinaria;
	}

	public void setDataInizioFaseStraordinaria(Date dataInizioFaseStraordinaria) {
		this.dataInizioFaseStraordinaria = dataInizioFaseStraordinaria;
	}

	public Date getDataFineFaseStraordinaria() {
		return dataFineFaseStraordinaria;
	}

	public void setDataFineFaseStraordinaria(Date dataFineFaseStraordinaria) {
		this.dataFineFaseStraordinaria = dataFineFaseStraordinaria;
	}

	public Collection<CronoprogrammaDettaglioFaseStraodinaria> getListaRup() {
		return listaRup;
	}

	public void setListaRup(Collection<CronoprogrammaDettaglioFaseStraodinaria> listaRup) {
		this.listaRup = listaRup;
	}
	
}
