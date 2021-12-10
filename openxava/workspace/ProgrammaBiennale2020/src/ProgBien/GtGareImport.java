package ProgBien;

import java.math.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@Views({
@View(name="Simple", // This view is used only when “Simple” is specified
members="codice, dataAvvio, oggetto" // Shows only number and name in the same line
)
})
public class GtGareImport {
	
	@Id
	@Required
	@Column(name = "codice", length = 6, nullable = false)
	private String codice;
	
	@Required
	@Column(name = "dataavvio", nullable = false)
    private Date dataAvvio;
	
	@Column(name = "numprot", length = 20, nullable = true)
    private String numeroProtocollo;
	
	@Column(name = "nGara", length = 12, nullable = true)
    private String numeroGara;
	
	@Column(name = "lcig", length = 1200, nullable = true)
	private String listaCig;
	
	@Required
	@Column(name = "cf", length = 16, nullable = false)
	private String cf;
	
	@Required
	@Column(name = "cognome", length = 50, nullable = false)
	private String cognome;
	
	@Required
	@Column(name = "nome", length = 50, nullable = false)
	private String nome;
	
	@Required
	@Column(name = "oggetto", length = 2000, nullable = false)
	private String oggetto;
	
	@Column(name = "tipoproc", length = 50, nullable = true)
	private String tipoProcedura;
	
	@Stereotype("MONEY")
    @Column(name = "importo", precision = 16, scale = 2, nullable = true)
    private BigDecimal importo;
	
	private String cui;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Date getDataAvvio() {
		return dataAvvio;
	}

	public void setDataAvvio(Date dataAvvio) {
		this.dataAvvio = dataAvvio;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getNumeroGara() {
		return numeroGara;
	}

	public void setNumeroGara(String numeroGara) {
		this.numeroGara = numeroGara;
	}

	public String getListaCig() {
		return listaCig;
	}

	public void setListaCig(String listaCig) {
		this.listaCig = listaCig;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getTipoProcedura() {
		return tipoProcedura;
	}

	public void setTipoProcedura(String tipoProcedura) {
		this.tipoProcedura = tipoProcedura;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}
}
