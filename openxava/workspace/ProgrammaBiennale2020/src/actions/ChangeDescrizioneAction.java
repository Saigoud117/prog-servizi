package actions;

import java.text.*;
import java.util.*;

import org.openxava.actions.*;
import util.*;

public class ChangeDescrizioneAction extends OnChangePropertyBaseAction {
	public void execute() throws Exception {
		
		if (getNewValue() == null) return;
		
		boolean update = (boolean)getNewValue();
		if (update) {
			String descr = ProgrammaBiennalePreferences.getDefaultDescrizionePubblicazioneAggiornamento();
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String date = String.valueOf(year).concat("/").concat(String.valueOf(year + 1));
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date now = new Date();
			getView().setValue("descrizione", descr.replace("~REPLACE~", date).replace("~REPLACE-DATA~", formatter.format(now)));
		}
		else
		{
			String descr = ProgrammaBiennalePreferences.getDefaultDescrizionePubblicazione();
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String date = String.valueOf(year).concat("/").concat(String.valueOf(year + 1));
			getView().setValue("descrizione", descr.replace("~REPLACE~", date));
		}
	}
}
