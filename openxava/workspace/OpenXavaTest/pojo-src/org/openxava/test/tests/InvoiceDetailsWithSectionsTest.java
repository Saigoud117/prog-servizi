package org.openxava.test.tests;

/**
 * 
 * @author Javier Paniza
 */

public class InvoiceDetailsWithSectionsTest extends CustomizeListTestBase /*ModuleTestBase*/ {
	
	public InvoiceDetailsWithSectionsTest(String testName) {
		super(testName, "InvoiceDetailsWithSections");		
	}
	
	public void testFocusInDialogWithAllMembersInSections_indexOfOutBoundInList() throws Exception { 
		// In the next order to reproduce a bug in the second assert that only occurs if we execute the first assert before
		assertIndexOfOutBoundInList(); 
		assertFocusInDialogWithAllMembersInSections();
	}
	
	private void assertFocusInDialogWithAllMembersInSections() throws Exception {   
		execute("Mode.detailAndFirst");
		execute("Invoice.editDetailWithSections", "row=0,viewObject=xava_view_details");
		assertFocusOn("serviceType");
	}
	
	private void assertIndexOfOutBoundInList() throws Exception { 
		// A bug only reproducible following the next EXACT steps
		
		assertListColumnCount(8);
		removeColumn(7);
		assertListColumnCount(7);
		execute("ListFormat.select", "editor=Charts");
		execute("Chart.selectType", "chartType=PIE");
		setValueInCollection("columns", 0, "name", "vatPercentage");
		execute("ListFormat.select", "editor=List");
		assertListColumnCount(7);
		execute("ListFormat.select", "editor=Charts");
		execute("ListFormat.select", "editor=List");
		assertListColumnCount(7);
	}
				
}