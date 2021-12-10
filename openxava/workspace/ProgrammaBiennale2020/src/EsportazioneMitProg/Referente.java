package EsportazioneMitProg;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL)
public class Referente {
    private String cap;

    private String luogoIstat;

    private String cfPiva;

    private String cognome;

    private String indirizzo;

    private String nome;

    private String civico;

    private String provincia;

    public String getCap ()
    {
        return cap;
    }

    public void setCap (String cap)
    {
        this.cap = cap;
    }

    public String getLuogoIstat ()
    {
        return luogoIstat;
    }

    public void setLuogoIstat (String luogoIstat)
    {
        this.luogoIstat = luogoIstat;
    }

    public String getCfPiva ()
    {
        return cfPiva;
    }

    public void setCfPiva (String cfPiva)
    {
        this.cfPiva = cfPiva;
    }

    public String getCognome ()
    {
        return cognome;
    }

    public void setCognome (String cognome)
    {
        this.cognome = cognome;
    }

    public String getIndirizzo ()
    {
        return indirizzo;
    }

    public void setIndirizzo (String indirizzo)
    {
        this.indirizzo = indirizzo;
    }

    public String getNome ()
    {
        return nome;
    }

    public void setNome (String nome)
    {
        this.nome = nome;
    }

    public String getCivico ()
    {
        return civico;
    }

    public void setCivico (String civico)
    {
        this.civico = civico;
    }

    public String getProvincia ()
    {
        return provincia;
    }

    public void setProvincia (String provincia)
    {
        this.provincia = provincia;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cap = "+cap+", luogoIstat = "+luogoIstat+", cfPiva = "+cfPiva+", cognome = "+cognome+", indirizzo = "+indirizzo+", nome = "+nome+", civico = "+civico+", provincia = "+provincia+"]";
    }
}
