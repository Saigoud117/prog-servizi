package ProgBien;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
public class Ausa {

    @Id
    @Required
    @Column(name = "Codice", length = 10, nullable = true)
    private String codice;

    @Required
    @Column(name = "CodiceFiscale", length = 16, nullable = false)
    private String codicefiscale;

    @Required
    @Column(name = "Denominazione", length = 30, nullable = false)
    private String denominazione;

    public void setCodice(String aValue) {
        codice = aValue;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodicefiscale(String aValue) {
        codicefiscale = aValue;
    }

    public String getCodicefiscale() {
        return codicefiscale;
    }

    public void setDenominazione(String aValue) {
        denominazione = aValue;
    }

    public String getDenominazione() {
        return denominazione;
    }

}
