package ProgBien;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@Views({
	@View(
			members="key; gruppicodici; tipo2; descrizione; coefficiente; note;" // Shows only number and name in the same line
		),
	@View(name="Note", // This view is used only when “Note” is specified
		members="note" // Shows only number and name in the same line
		)
})
public class CodiciIct implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Required
    @Column(name = "key", length = 3, nullable = false)
    private String key;
    
    @Required
    @ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=false) // The reference can have no value
    @DescriptionsList(descriptionProperties="descrizione") // Thus the reference is displayed using a combo
    private GruppiCodici gruppicodici; // A regular Java reference

	@Required
    @Column(name = "Tipo2", length = 1, nullable = false)
    private String tipo2;

	@Required
    @Column(name = "Descrizione", length = 250, nullable = false)
    private String descrizione;
	
    @Column(name = "Coefficiente", length = 5, precision = 2, nullable = true)
    private BigDecimal coefficiente;
        
    @Stereotype("TEXT_AREA") // This is for a big text, a text area or equivalent will be used
    @Column(name = "Note", length = 500, nullable = true)
    private String note;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public GruppiCodici getGruppicodici() {
		return gruppicodici;
	}

	public void setGruppicodici(GruppiCodici gruppicodici) {
		this.gruppicodici = gruppicodici;
	}

	public String getTipo2() {
		return tipo2;
	}

	public void setTipo2(String tipo2) {
		this.tipo2 = tipo2;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public BigDecimal getCoefficiente() {
		return coefficiente;
	}

	public void setCoefficiente(BigDecimal coefficiente) {
		this.coefficiente = coefficiente;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
		
}
