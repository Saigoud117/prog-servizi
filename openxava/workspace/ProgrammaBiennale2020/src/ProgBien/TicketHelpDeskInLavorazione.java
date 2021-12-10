package ProgBien;

import javax.persistence.*;

import org.openxava.annotations.*;

public class TicketHelpDeskInLavorazione {
	
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=false)
    @DescriptionsList(
    		descriptionProperties="tipo.descrizione"
    		) // Thus the reference is displayed using a combo
    private TicketConfigurazioneNotifiche tipo;
	
	@ManyToOne( // The reference is persisted as a database relationship
        fetch=FetchType.LAZY, // The reference is loaded on demand
        optional=false) // The reference can have no value
    @DescriptionsList(
    		descriptionProperties="utente.nome, utente.cognome, utente.cf", 
			depends="this.tipo",
    		condition="e.ticketConfigurazioneNotifiche.oid = ?"
    		) // Thus the reference is displayed using a combo
    private TicketConfigurazioneNotificheDettaglio utente; // A regular Java reference   

	public TicketConfigurazioneNotifiche getTipo() {
		return tipo;
	}

	public void setTipo(TicketConfigurazioneNotifiche tipo) {
		this.tipo = tipo;
	}

	public TicketConfigurazioneNotificheDettaglio getUtente() {
		return utente;
	}

	public void setUtente(TicketConfigurazioneNotificheDettaglio utente) {
		this.utente = utente;
	}
	
}
