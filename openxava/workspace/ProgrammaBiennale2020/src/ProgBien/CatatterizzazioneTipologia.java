package ProgBien;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
public class CatatterizzazioneTipologia {
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Required
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=false) // The reference can have no value
    @DescriptionsList(descriptionProperties="descrizione") // Thus the reference is displayed using a combo
    private TipologiaCoperturaFinanziaria tipologia; // A regular Java reference
	
	@Required
	private String nome;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public TipologiaCoperturaFinanziaria getTipologia() {
		return tipologia;
	}

	public void setTipologia(TipologiaCoperturaFinanziaria tipologia) {
		this.tipologia = tipologia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
