package ProgBien;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
public class TipologiaVoceQuadroEconomico implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @Required
    @Column(name = "key", length = 3, nullable = false)
    private String key;
	
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=true) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="nome",
    		condition="e.keyPrincipale is null"
		) // Thus the reference is displayed using a combo
    private TipologiaVoceQuadroEconomico keyPrincipale; // A regular Java reference
	
	@Required
	@Stereotype("TEXT_AREA") // This is for a big text, a text area or equivalent will be used
	private String nome;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public TipologiaVoceQuadroEconomico getKeyPrincipale() {
		return keyPrincipale;
	}

	public void setKeyPrincipale(TipologiaVoceQuadroEconomico keyPrincipale) {
		this.keyPrincipale = keyPrincipale;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
