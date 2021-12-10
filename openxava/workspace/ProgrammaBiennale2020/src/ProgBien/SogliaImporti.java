package ProgBien;

import java.util.*;
import javax.persistence.*;
import org.openxava.annotations.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Tabs({
@Tab(
	baseCondition="${deleted} = false"
)
})
public class SogliaImporti extends Deletable {
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Required
	@Column(name = "DataInizio", nullable = false)
    private Date datainizio;
	
	@Required
	@Column(name = "DataFine", nullable = false)
    private Date datafine;
    
    @OneToMany (mappedBy="soglia", cascade=CascadeType.REMOVE)
    @OrderBy("percentScaglione")
    private Collection<SogliaScaglioni> scaglioni;  
    
    @Required
    @Column(precision = 5, scale=4, nullable = true)
    private java.math.BigDecimal percentIncentivi;
    
    @Required
    @Column(precision = 5, scale=4, nullable = true)
    private java.math.BigDecimal percentInnovazione;
    
    @Required
    @Column(precision = 5, scale=4, nullable = true)
    private java.math.BigDecimal percentProgrammazione;
    
    @Required
    @Column(precision = 5, scale=4, nullable = true)
    private java.math.BigDecimal percentAffidamento;
    
    @Required
    @Column(precision = 5, scale=4, nullable = true)
    private java.math.BigDecimal percentEsecuzione;
    
    @Required
    @Column(precision = 5, scale=4, nullable = true)
    private java.math.BigDecimal percentCollaudo;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
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

	public Collection<SogliaScaglioni> getScaglioni() {
		return scaglioni;
	}

	public void setScaglioni(Collection<SogliaScaglioni> scaglioni) {
		this.scaglioni = scaglioni;
	}

	public java.math.BigDecimal getPercentIncentivi() {
		return percentIncentivi;
	}

	public void setPercentIncentivi(java.math.BigDecimal percentIncentivi) {
		this.percentIncentivi = percentIncentivi;
	}

	public java.math.BigDecimal getPercentInnovazione() {
		return percentInnovazione;
	}

	public void setPercentInnovazione(java.math.BigDecimal percentInnovazione) {
		this.percentInnovazione = percentInnovazione;
	}

	public java.math.BigDecimal getPercentProgrammazione() {
		return percentProgrammazione;
	}

	public void setPercentProgrammazione(java.math.BigDecimal percentProgrammazione) {
		this.percentProgrammazione = percentProgrammazione;
	}

	public java.math.BigDecimal getPercentAffidamento() {
		return percentAffidamento;
	}

	public void setPercentAffidamento(java.math.BigDecimal percentAffidamento) {
		this.percentAffidamento = percentAffidamento;
	}

	public java.math.BigDecimal getPercentEsecuzione() {
		return percentEsecuzione;
	}

	public void setPercentEsecuzione(java.math.BigDecimal percentEsecuzione) {
		this.percentEsecuzione = percentEsecuzione;
	}

	public java.math.BigDecimal getPercentCollaudo() {
		return percentCollaudo;
	}

	public void setPercentCollaudo(java.math.BigDecimal percentCollaudo) {
		this.percentCollaudo = percentCollaudo;
	}
}
