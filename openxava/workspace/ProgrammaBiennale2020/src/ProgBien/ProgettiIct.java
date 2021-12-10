package ProgBien;

import java.util.*;
import javax.persistence.*;

import org.openxava.annotations.*;

import Filters.*;

@Entity
@IdClass(ProgettiIctKey.class)
@Views({
@View(name="Simple", // This view is used only when “Simple” is specified
members="sigla, descrizione" // Shows only number and name in the same line
),
@View(name="Servizi", extendsView="Simple", members="servizi")
})
@Tab(
	//name="Current",
	filter=CurrentUserServizioStrutturaFilter.class,
	//properties="servizio, struttura, descrizione, codicesicurezza, datainizio, datafine, nstruttura, nservizio, inquadrato",
	baseCondition="(? = 1 or ${servizi.servizio} in (?)) and (? = 1 or ${servizi.struttura} in (?)) and ${servizi.datainizio} < now() and ${servizi.datafine} > now()"
	)
public class ProgettiIct implements  java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Required
    @Column(name = "Sigla", length = 20, nullable = false)
    private String sigla;

    @Id
    @ManyToOne(fetch=FetchType.EAGER) 
    @ReferenceView("Simple")
    @JoinColumn(name = "Id_servizio", referencedColumnName = "oid",nullable=false,unique=false,insertable=true,updatable=true)
    private Servizi servizi;

    @Required
    @Column(name = "Descrizione", length = 250, nullable = false)
    private String descrizione;

    @Required
    @Column(name = "DataInizio", nullable = false)
    private Date datainizio;

    @Column(name = "DataFine", nullable = true)
    private Date datafine;
    
    @ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="key, descrizione",
    		condition="e.gruppicodici = 'T'",
    		showReferenceView=true
    ) // Thus the reference is displayed using a combo
    @ReferenceView("Note")
    private CodiciIct tipologia; // A regular Java reference
    
    @ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="key, descrizione",
    		condition="e.gruppicodici = 'D'",
    		showReferenceView=true
    		) // Thus the reference is displayed using a combo
    @ReferenceView("Note")
    private CodiciIct dim1; // A regular Java reference
    
    @ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="key, descrizione",
    		depends="dim1",
    		condition="e.gruppicodici in (select c.tipo2 from CodiciIct c where c.key = ?)",
    		showReferenceView=true) // Thus the reference is displayed using a combo
    @ReferenceView("Note")
    private CodiciIct dim2; // A regular Java reference
    
    @Stereotype("TEXT_AREA") // This is for a big text, a text area or equivalent will be used
    @Column(name = "Note", length = 1500, nullable = true)
    private String note;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}	

	public Servizi getServizi() {
		return servizi;
	}

	public void setServizi(Servizi servizi) {
		this.servizi = servizi;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDatainizio() {
		return datainizio;
	}

	public void setDatainizio(Date datainizio) {
		this.datainizio = datainizio;
	}

	public Date getDatafine() {
		return datafine;
	}

	public void setDatafine(Date datafine) {
		this.datafine = datafine;
	}

	public CodiciIct getTipologia() {
		return tipologia;
	}

	public void setTipologia(CodiciIct tipologia) {
		this.tipologia = tipologia;
	}

	public CodiciIct getDim1() {
		return dim1;
	}

	public void setDim1(CodiciIct dim1) {
		this.dim1 = dim1;
	}

	public CodiciIct getDim2() {
		return dim2;
	}

	public void setDim2(CodiciIct dim2) {
		this.dim2 = dim2;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
		
}
