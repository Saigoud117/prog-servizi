package actions;

import java.util.*;
import javax.ejb.*;
import org.openxava.actions.*;

public class SearchExcludingDeletedAction extends SearchByViewKeyAction { // The standard OpenXava action to search
 
    private boolean isDeletable() { // To see if this entity has a deleted property
        return getView().getMetaModel()
            .containsMetaProperty("deleted");
    }
 
    @SuppressWarnings("rawtypes")
	@Override
    protected Map getValuesFromView() // Gets the values displayed in this view
        throws Exception // These values are used as keys for the search
    {
        if (!isDeletable()) { // If not deletable we run the standard logic
            return super.getValuesFromView();
        }
       @SuppressWarnings("unchecked")
	Map<String, Object> values = super.getValuesFromView();
        values.put("deleted", false); // We populate deleted property with false
        return values;
    }
 
    @SuppressWarnings("rawtypes")
	@Override
    protected Map getMemberNames() // The members to be read from the entity
        throws Exception
    {
        if (!isDeletable()) { // If not deletable we run the standard logic
            return super.getMemberNames();
        }
        @SuppressWarnings("unchecked")
		Map<String, Object> members = super.getMemberNames();
        members.put("deleted", null);  // We want to get the deleted property from entity,
        return members; // though it might not be in the view
    }
 
    @Override
    protected void setValuesToView(@SuppressWarnings("rawtypes") Map values) // Assigns the values from the
        throws Exception // entity to the view
    {
        if (isDeletable() && // If it has an deleted property and
            (Boolean) values.get("deleted")) { // it is true
                throw new ObjectNotFoundException(); // The same exception OpenXava
        } // throws when the object is not found
        else {
            super.setValuesToView(values); // Otherwise we run the standard logic
        }
    }
}