package ProgBien;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
public class Nuts {
	@Id
	@Required
    @Column(name = "Codice", length = 11, nullable = false)
    private String codice;	

	@Required
    @Column(name = "Descrizione", length = 50, nullable = false)
    private String descrizione;
	
	@Required
    @Column(name = "Tipo", length = 6, nullable = false)
    private String tipo;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
