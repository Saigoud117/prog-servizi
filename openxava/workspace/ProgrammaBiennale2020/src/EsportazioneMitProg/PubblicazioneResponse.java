package EsportazioneMitProg;

import java.util.*;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_NULL)
public class PubblicazioneResponse {
	private String id;

    private String error;

    private ArrayList<Validate> validate;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getError ()
    {
        return error;
    }

    public void setError (String error)
    {
        this.error = error;
    }

    public ArrayList<Validate> getValidate() {
		return validate;
	}

	public void setValidate(ArrayList<Validate> validate) {
		this.validate = validate;
	}

	@Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", error = "+error+", validate = "+validate+"]";
    }
}
