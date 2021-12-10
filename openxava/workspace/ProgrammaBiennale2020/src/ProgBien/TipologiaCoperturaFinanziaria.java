package ProgBien;

import javax.persistence.*;
import javax.persistence.Entity;

import org.openxava.annotations.*;

@Entity
public class TipologiaCoperturaFinanziaria {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Required
    @Column(name = "key", length = 3, nullable = false)
    private String key;
	
	@Required
	private String nome;
	
	@Required
	@Stereotype("TEXT_AREA") // This is for a big text, a text area or equivalent will be used
	private String descrizione;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}
