package ProgBien;

import java.util.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;
import org.openxava.util.*;

@Entity
@Views({
@View(name="Simple", // This view is used only when “Simple” is specified
members="tipo" // Shows only number and name in the same line
)})
public class TicketConfigurazioneNotifiche {
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Required
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=false)
    @DescriptionsList(
    		descriptionProperties="descrizione"
    		) // Thus the reference is displayed using a combo
    private TipologiaTicket tipo;
	
	@OneToMany (mappedBy="ticketConfigurazioneNotifiche", cascade=CascadeType.REMOVE)
    @AddAction("TicketConfigurazioneNotifiche.addTicketConfigurazioneNotificheDettaglio") // @AddAction instead
	@ListProperties("utente.nome, utente.cognome, utente.cf")
    private Collection<TicketConfigurazioneNotificheDettaglio> ticketConfigurazioneNotificheDettaglio;
	
	@PrePersist
	private void validate() {
		if (util.ProgBienUtils.checkTicketConfigurazione(this.tipo))
		{
			throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "item_already_exist"
	            )
	        );
		}
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public TipologiaTicket getTipo() {
		return tipo;
	}

	public void setTipo(TipologiaTicket tipo) {
		this.tipo = tipo;
	}

	public Collection<TicketConfigurazioneNotificheDettaglio> getTicketConfigurazioneNotificheDettaglio() {
		return ticketConfigurazioneNotificheDettaglio;
	}

	public void setTicketConfigurazioneNotificheDettaglio(
			Collection<TicketConfigurazioneNotificheDettaglio> ticketConfigurazioneNotificheDettaglio) {
		this.ticketConfigurazioneNotificheDettaglio = ticketConfigurazioneNotificheDettaglio;
	}
}
