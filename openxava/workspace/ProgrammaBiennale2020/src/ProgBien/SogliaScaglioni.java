package ProgBien;

import javax.persistence.*;
import org.openxava.annotations.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class SogliaScaglioni {
	
	@ManyToOne // Lazy fetching produces a fails on removing a detail from invoice
	private SogliaImporti soglia;

	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Stereotype("MONEY")
    @Column(name = "valoreDa", precision = 19, scale=2, nullable = true)
    private java.math.BigDecimal valoreDa;
	
	@Stereotype("MONEY")
    @Column(name = "valoreA", precision = 19, scale=2, nullable = true)
    private java.math.BigDecimal valoreA;
	
	@Required
    @Column(precision = 5, scale=4, nullable = true)
    private java.math.BigDecimal percentScaglione;
	
	@Required
    private boolean soloRimanenza;
	
	public SogliaImporti getSoglia() {
		return soglia;
	}

	public void setSoglia(SogliaImporti soglia) {
		this.soglia = soglia;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public java.math.BigDecimal getValoreDa() {
		return valoreDa;
	}

	public void setValoreDa(java.math.BigDecimal valoreDa) {
		this.valoreDa = valoreDa;
	}

	public java.math.BigDecimal getValoreA() {
		return valoreA;
	}

	public void setValoreA(java.math.BigDecimal valoreA) {
		this.valoreA = valoreA;
	}

	public java.math.BigDecimal getPercentScaglione() {
		return percentScaglione;
	}

	public void setPercentScaglione(java.math.BigDecimal percentScaglione) {
		this.percentScaglione = percentScaglione;
	}

	public boolean isSoloRimanenza() {
		return soloRimanenza;
	}

	public void setSoloRimanenza(boolean soloRimanenza) {
		this.soloRimanenza = soloRimanenza;
	}
	
}
