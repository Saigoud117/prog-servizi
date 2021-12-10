package ProgBien;

import java.io.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.util.*;

import Calculators.*;
import Filters.*;

@Entity
@Tab(
	//name="Current",
	filter=AbilitazioneFilter.class,
	//properties="servizio, struttura, descrizione, codicesicurezza, datainizio, datafine, nstruttura, nservizio, inquadrato",
	baseCondition="${deleted} = false and ((? = 1 and ${servizi.servizio} in (?)) and ${servizi.datainizio} < now() and ${servizi.datafine} > now()) or (? = 1 and ${cf} like ?) and (abilitato is null or abilitato = false)"
)
public class RichiestaAbilitazione extends Deletable  {
	
	@Id
	@ReadOnly
    @Required
    @DefaultValueCalculator(value=CurrentUserCfCalculator.class)
    @Column(name = "CF", length = 16, nullable = false)
    private String cf;

    @Required
    @Column(name = "Cognome", length = 50, nullable = false)
    private String cognome;

    @Required
    @Column(name = "Nome", length = 50, nullable = false)
    private String nome;
    
    @Required
    @Stereotype("EMAIL")
    @Column(name = "Email", length = 250, nullable = false)
    private String email;

    @Column(name = "RUP", nullable = false)
    private boolean rup;

    @Column(name = "Matricola", nullable = true)
    private Integer matricola;
    
    @Required
    @ManyToOne
    @ReferenceView("Simple")
    @JoinColumn(name = "Id_servizio", referencedColumnName = "oid")
    private Servizi servizi;
    
    @Hidden
    private boolean abilitato;
    
    @Required
    @ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=false) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="key, descrizione",
    		condition="e.gruppicodici = 'A' and e.key not in ('A01')") // Thus the reference is displayed using a combo
    private CodiciIct profilo; // A regular Java reference
    
    @PrePersist
	private void sendNotifyEmail() throws AddressException, UnsupportedEncodingException, MessagingException {
    	
    	if ((!Users.getCurrent().equals("admin") || !util.ProgBienUtils.checkAdmin()) && this.getProfilo().getKey().equals("A02")) {
			throw new javax.validation.ValidationException(
				XavaResources.getString(
					"operation_not_allowed_abilitazione"
				)
			);
		}
    	
    	util.ProgBienUtils.sendNotifyEmailAbilitazione("Richiesta", this.getCf(), this.getEmail(), this.getServizi().getServizio(), this.getServizi().getDescrizione());
	}
    
    @PreUpdate
	private void preUpdate() throws AddressException, UnsupportedEncodingException, MessagingException {
    	if ((!Users.getCurrent().equals("admin") || !util.ProgBienUtils.checkAdmin()) && this.getProfilo().getKey().equals("A02")) {
			throw new javax.validation.ValidationException(
				XavaResources.getString(
					"operation_not_allowed_abilitazione"
				)
			);
		}
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

	public boolean isRup() {
		return rup;
	}

	public void setRup(boolean rup) {
		this.rup = rup;
	}

	public Integer getMatricola() {
		return matricola;
	}

	public void setMatricola(Integer matricola) {
		this.matricola = matricola;
	}

	public Servizi getServizi() {
		return servizi;
	}

	public void setServizi(Servizi servizi) {
		this.servizi = servizi;
	}

	public boolean isAbilitato() {
		return abilitato;
	}

	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CodiciIct getProfilo() {
		return profilo;
	}

	public void setProfilo(CodiciIct profilo) {
		this.profilo = profilo;
	}
}
