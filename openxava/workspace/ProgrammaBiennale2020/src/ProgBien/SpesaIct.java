package ProgBien;

import javax.persistence.*;
import org.openxava.annotations.*;

@Entity
public class SpesaIct implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Required
	@ManyToOne
	@ReferenceView("Simple")
	@JoinColumns({
	 @JoinColumn(
	           name = "Sigla",
	           referencedColumnName = "sigla"),
	 @JoinColumn(
	           name = "Id_servizio",
	           referencedColumnName = "Id_servizio")
	    })
    private ProgettiIct progettiict; // A regular Java reference
	
	@Id
    @Required
    @Column(name = "Anno", nullable = true)
    private Integer anno;
	
	@Id
    @Required
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(descriptionProperties="key") // Thus the reference is displayed using a combo
    private CodiciIct tipospesa; // A regular Java reference

    @Column(name = "Importo", length = 16, precision = 2, nullable = true)
    private java.math.BigDecimal importo;

	public ProgettiIct getProgettiict() {
		return progettiict;
	}

	public void setProgettiict(ProgettiIct progettiict) {
		this.progettiict = progettiict;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public CodiciIct getTipospesa() {
		return tipospesa;
	}

	public void setTipospesa(CodiciIct tipospesa) {
		this.tipospesa = tipospesa;
	}

	public java.math.BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(java.math.BigDecimal importo) {
		this.importo = importo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}    

}