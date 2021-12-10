package actions;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.*;

import EsportazioneMitProg.*;
import ProgBien.*;
import WebAPI.*;
import retrofit2.*;
import util.*;

public class JsonExport extends ViewBaseAction  {
	public void execute() throws Exception {	

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		@SuppressWarnings("unchecked")
		List<Procedure> ProcedureFabbisogno = (List<Procedure>)XPersistence.getManager()
			    .createQuery(
			        "from Procedure p where p.stato.key in ('M', 'N', 'Z') and p.archived = false and p.deleted = false and (p.valorestimatoappalto >= 40000.00 or (p.valorestimatoappalto < 40000.00 and p.ultimopianoapprovato is not null)) order by p.codiceinterno ASC")  // JPQL query
			    .getResultList();
		
		// fix per le due sotto soglia da modifiche piano pubblicato
    	@SuppressWarnings("unchecked")
		List<ProcedureProgramma> ProcedureModifiche = (List<ProcedureProgramma>)XPersistence.getManager()
			    .createQuery(
			    	"from ProcedureProgramma p where p.stato.key in ('M', 'N', 'Z') and p.cancellazione = false and p.archived = false and p.deleted = false and (p.valorestimatoappalto >= 40000.00 or (p.valorestimatoappalto < 40000.00 and p.ultimopianoapprovato is not null and p.cui <> '~MINORI-SOGLIA-40K~')) order by p.oid ASC")  // JPQL query
			    .getResultList();
		
		/*
		@SuppressWarnings("unchecked")
		List<ProcedureProgramma> ProcedureModifiche = (List<ProcedureProgramma>)XPersistence.getManager()
			    .createQuery(
			        "from ProcedureProgramma p where p.stato.key in ('M', 'N', 'Z') and p.cancellazione = false and p.archived = false and p.deleted = false and p.valorestimatoappalto >= 40000.00 order by p.oid ASC")  // JPQL query
			    .getResultList();
		*/
		
		@SuppressWarnings("unchecked")
		List<ProcedureDefinitive> ProcedureConfermate = (List<ProcedureDefinitive>)XPersistence.getManager()
			    .createQuery(
			    		"from ProcedureDefinitive p where p.stato.key = 'C' and p.archived = false and p.deleted = false and (p.valorestimatoappalto >= 40000.00 or (p.valorestimatoappalto < 40000.00 and p.cui <> '~MINORI-SOGLIA-40K~'))")  // JPQL query
			    .getResultList();
		
		@SuppressWarnings("unchecked")
		List<Procedure> ProcedureNonRiproposte = (List<Procedure>)XPersistence.getManager()
			    .createQuery(
			        "from Procedure p where p.stato.key = 'K' and p.archived = false and p.deleted = false and p.valorestimatoappalto >= 40000.00")  // JPQL query
			    .getResultList();
		
		@SuppressWarnings("unchecked")
		List<ProcedureDefinitive> ProcedureCancellateAggiornamentiAnnuali = (List<ProcedureDefinitive>)XPersistence.getManager()
			    .createQuery(
			        "from ProcedureDefinitive p where p.stato.key = 'K' and p.archived = false and p.deleted = false")  // JPQL query
			    .getResultList();
		
		Pubblicazione ent = (Pubblicazione)getView().getEntity();
        EntityManager em = XPersistence.getManager();   
        ent = em.merge(ent);
		
        PubblicazioneFornitureServizi p = new PubblicazioneFornitureServizi();
        if (!ent.isAggiornamento())
	        p = ProgrammaBiennaleExport.getDatiPubblicazione(ent, ProcedureFabbisogno, ProcedureConfermate, ProcedureNonRiproposte, ProcedureCancellateAggiornamentiAnnuali);
        else
        	p = ProgrammaBiennaleExport.getDatiModifichePubblicazione(ent, ProcedureModifiche, ProcedureConfermate);
		
		try {

			Login loginGbj = RetrofitLogin();
			String json = mapper.writeValueAsString(p);
			
			PubblicazioneResponse response = new PubblicazioneResponse();
			if (loginGbj != null)
				response =  RetrofitPubblicaFornitureServizi(loginGbj.getToken(), "2", p);
			
			EsportazioneDati e = new EsportazioneDati();
		    Date date = new Date();
		    e.setData(date);
		    e.setProduzione(false);
		    e.setJsonSentData(json);
		    
		    e.setJsonAnswer(mapper.writeValueAsString(response));
		    
		    String message = "Esportazione.Error";
		    
		    if (response.getId() != null && response.getId() != "null")
		    {
		    	e.setSuccess(true);
		    	e.setIdEsportazione(Integer.parseInt(response.getId()));
		    	ent.setIdRicevuto(response.getId());
		    	message = "Esportazione.Success";
		    }
		    else
		    	e.setSuccess(false);
		    XPersistence.getManager().persist(e);
		    XPersistence.commit();
		    getView().refresh();
		    addMessage(message);
		}
		catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	private PubblicazioneResponse RetrofitPubblicaFornitureServizi(String token, String modalitaInvio, PubblicazioneFornitureServizi p) throws IOException {
		ApiService service = WebClient.retrofit().create(ApiService.class);
		Call<PubblicazioneResponse> repos = service.PubblicaFornitureServizi(token, modalitaInvio, p);
		Response<PubblicazioneResponse> response = repos.clone().execute();
		
		PubblicazioneResponse r = new PubblicazioneResponse();
		
		if (response.code() >= 400)
		{
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			r = mapper.readValue(response.errorBody().string(), PubblicazioneResponse.class);
		}
		else
		{
			r = response.body();
		}
		
		return r;
		
	}
	
	private Login RetrofitLogin() throws IOException {
		
		String username = ProgrammaBiennalePreferences.getDefaultUsername();
		String password = ProgrammaBiennalePreferences.getDefaultPassword();
		String clientId = ProgrammaBiennalePreferences.getDefaultClientId();
		String clientKey = ProgrammaBiennalePreferences.getDefaultClientKey();
		
		ApiService service = WebClient.retrofit().create(ApiService.class);
		Call<Login> repos = service.WsLogin(username, password, clientId, clientKey);
		Response<Login> response = repos.clone().execute();
		Login loginObj = response.body();

		return loginObj;
	}
}

