package ProgBien;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
public class TicketConfigurazioneNotificheDettaglio {
	
	@ReferenceView("Simple")
	@ManyToOne // Lazy fetching produces a fails on removing a detail from invoice
	private TicketConfigurazioneNotifiche ticketConfigurazioneNotifiche;
	
	@Id
    @Hidden // The property is not shown to the user. It's an internal identifier
    @GeneratedValue(generator="system-uuid") // Universally Unique Identifier (1)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length=32)
    private String oid;
	
	@Required
    @ManyToOne
    private Dipendenti utente;

	public TicketConfigurazioneNotifiche getTicketConfigurazioneNotifiche() {
		return ticketConfigurazioneNotifiche;
	}

	public void setTicketConfigurazioneNotifiche(TicketConfigurazioneNotifiche ticketConfigurazioneNotifiche) {
		this.ticketConfigurazioneNotifiche = ticketConfigurazioneNotifiche;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Dipendenti getUtente() {
		return utente;
	}

	public void setUtente(Dipendenti utente) {
		this.utente = utente;
	}
}
