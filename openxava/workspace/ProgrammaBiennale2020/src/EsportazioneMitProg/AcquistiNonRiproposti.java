package EsportazioneMitProg;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL)
public class AcquistiNonRiproposti {
	private String descrizione;

    private String motivo;

    private String priorita;

    private String cui;

    private String importo;

    private String cup;

    public String getDescrizione ()
    {
        return descrizione;
    }

    public void setDescrizione (String descrizione)
    {
        this.descrizione = descrizione;
    }

    public String getMotivo ()
    {
        return motivo;
    }

    public void setMotivo (String motivo)
    {
        this.motivo = motivo;
    }

    public String getPriorita ()
    {
        return priorita;
    }

    public void setPriorita (String priorita)
    {
        this.priorita = priorita;
    }

    public String getCui ()
    {
        return cui;
    }

    public void setCui (String cui)
    {
        this.cui = cui;
    }

    public String getImporto ()
    {
        return importo;
    }

    public void setImporto (String importo)
    {
        this.importo = importo;
    }

    public String getCup ()
    {
        return cup;
    }

    public void setCup (String cup)
    {
        this.cup = cup;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [descrizione = "+descrizione+", motivo = "+motivo+", priorita = "+priorita+", cui = "+cui+", importo = "+importo+", cup = "+cup+"]";
    }
}
