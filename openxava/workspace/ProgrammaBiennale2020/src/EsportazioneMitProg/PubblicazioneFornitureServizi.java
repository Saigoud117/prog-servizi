package EsportazioneMitProg;

import java.util.*;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL)
public class PubblicazioneFornitureServizi {
    private String ufficio;

    private Referente referente;

    private String anno;

    private String numeroApprovazione;

    private String dataApprovazione;

    private String codiceFiscaleSA;

    private String dataPubblicazioneApprovazione;

    private String idRicevuto;

    private String descrizione;

    private String ultimaModificaSCP;

    private String urlAttoApprovazione;

    private ArrayList<Acquisti> acquisti;

    private String titoloAttoApprovazione;

    private String primaPubblicazioneSCP;

    private String id;

    private ArrayList<AcquistiNonRiproposti> acquistiNonRiproposti;

	public String getUfficio() {
		return ufficio;
	}



	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}



	public Referente getReferente() {
		return referente;
	}



	public void setReferente(Referente referente) {
		this.referente = referente;
	}


	public String getAnno() {
		return anno;
	}



	public void setAnno(String anno) {
		this.anno = anno;
	}



	public String getNumeroApprovazione() {
		return numeroApprovazione;
	}



	public void setNumeroApprovazione(String numeroApprovazione) {
		this.numeroApprovazione = numeroApprovazione;
	}



	public String getDataApprovazione() {
		return dataApprovazione;
	}



	public void setDataApprovazione(String dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}



	public String getCodiceFiscaleSA() {
		return codiceFiscaleSA;
	}



	public void setCodiceFiscaleSA(String codiceFiscaleSA) {
		this.codiceFiscaleSA = codiceFiscaleSA;
	}



	public String getDataPubblicazioneApprovazione() {
		return dataPubblicazioneApprovazione;
	}



	public void setDataPubblicazioneApprovazione(String dataPubblicazioneApprovazione) {
		this.dataPubblicazioneApprovazione = dataPubblicazioneApprovazione;
	}



	public String getIdRicevuto() {
		return idRicevuto;
	}



	public void setIdRicevuto(String idRicevuto) {
		this.idRicevuto = idRicevuto;
	}



	public String getDescrizione() {
		return descrizione;
	}



	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}



	public String getUltimaModificaSCP() {
		return ultimaModificaSCP;
	}



	public void setUltimaModificaSCP(String ultimaModificaSCP) {
		this.ultimaModificaSCP = ultimaModificaSCP;
	}



	public String getUrlAttoApprovazione() {
		return urlAttoApprovazione;
	}



	public void setUrlAttoApprovazione(String urlAttoApprovazione) {
		this.urlAttoApprovazione = urlAttoApprovazione;
	}



	public ArrayList<Acquisti> getAcquisti() {
		return acquisti;
	}



	public void setAcquisti(ArrayList<Acquisti> acquisti) {
		this.acquisti = acquisti;
	}



	public String getTitoloAttoApprovazione() {
		return titoloAttoApprovazione;
	}



	public void setTitoloAttoApprovazione(String titoloAttoApprovazione) {
		this.titoloAttoApprovazione = titoloAttoApprovazione;
	}



	public String getPrimaPubblicazioneSCP() {
		return primaPubblicazioneSCP;
	}



	public void setPrimaPubblicazioneSCP(String primaPubblicazioneSCP) {
		this.primaPubblicazioneSCP = primaPubblicazioneSCP;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public ArrayList<AcquistiNonRiproposti> getAcquistiNonRiproposti() {
		return acquistiNonRiproposti;
	}



	public void setAcquistiNonRiproposti(ArrayList<AcquistiNonRiproposti> acquistiNonRiproposti) {
		this.acquistiNonRiproposti = acquistiNonRiproposti;
	}



	@Override
    public String toString()
    {
        return "ClassPojo [ufficio = "+ufficio+", referente = "+referente+", anno = "+anno+", numeroApprovazione = "+numeroApprovazione+", dataApprovazione = "+dataApprovazione+", codiceFiscaleSA = "+codiceFiscaleSA+", dataPubblicazioneApprovazione = "+dataPubblicazioneApprovazione+", idRicevuto = "+idRicevuto+", descrizione = "+descrizione+", ultimaModificaSCP = "+ultimaModificaSCP+", urlAttoApprovazione = "+urlAttoApprovazione+", acquisti = "+acquisti+", titoloAttoApprovazione = "+titoloAttoApprovazione+", primaPubblicazioneSCP = "+primaPubblicazioneSCP+", id = "+id+", acquistiNonRiproposti = "+acquistiNonRiproposti+"]";
    }
}
