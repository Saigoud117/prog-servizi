package org.openxava.test.tests;

import org.openxava.tests.*;

/**
 * @author Javier Paniza
 */

public class CustomerNewOnInitTest extends ModuleTestBase {

	public CustomerNewOnInitTest(String testName) {
		super(testName, "CustomerNewOnInit");		
	}
	
	public void testNewOnInit() throws Exception {
		assertNoErrors();
		assertAction("Mode.list");
		assertNoAction("Mode.detailAndFirst");
		assertEditable("number");
	}
	
	public void testGetValueFromAGroupInSectionAfterNew_hideShowSectionWhenOnlyOne() throws Exception {
		setValue("name", "Juanillo");
		execute("CustomerNewOnInit.getName");		
		assertMessage("The name is Juanillo");
		
		assertExists("name");
		assertExists("seller.name");
		execute("CustomerNewOnInit.hideSection");
		assertAction("CustomerNewOnInit.hideSection"); // To verify that the UI is not broken
		assertNoErrors();
		assertNotExists("name");
		assertNotExists("seller.name");		
		execute("CustomerNewOnInit.showSection");
		assertAction("CustomerNewOnInit.showSection"); // To verify that the UI is not broken
		assertNoErrors();
		assertExists("name");
		assertExists("seller.name");
	}
					
}
