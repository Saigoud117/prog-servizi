package org.openxava.test.tests;

import javax.persistence.*;

import org.apache.commons.lang.*;
import org.openxava.test.model.*;
import org.openxava.tests.*;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

/**
 * 
 * @author Javier Paniza
 */

public class ApplicantTest extends ModuleTestBase {
	
	private boolean modulesLimit = true;
	
	public ApplicantTest(String testName) {
		super(testName, "Applicant");		
	}
		
	public void testCancelAddColumnsFromSearhReference() throws Exception { 
		execute("CRUD.new");
		execute("Reference.search", "keyProperty=skill.description");
		assertValueInList(0, 0, "PROGRAMMING");
		execute("List.addColumns");
		execute("AddColumns.cancel");
		assertNoAction("AddColumns.cancel");
		assertValueInList(0, 0, "PROGRAMMING");
	}
	
	public void testGetEntityWithEmptyReferences_duplicateActionsNotAdded() throws Exception {  		
		assertListRowCount(1);
		execute("CRUD.new");
		assertNoAction("JPACRUD.create");
		execute("Applicant.showCreate");
		assertAction("JPACRUD.create"); 
		assertActionsCount("JPACRUD.create", 1);
		setValue("name", "JUNIT APPLICANT");
		execute("JPACRUD.create");
		assertNoErrors();
		execute("Mode.list");
		assertListRowCount(2);
		assertValueInList(1, 0, "JUNIT APPLICANT CREATED"); // The CREATED is for a @PrePersist needed for the test testPolymorphicReferenceFromBaseClass_savingTwiceWithNoRefreshAfterAndHiddenKey()
		execute("CRUD.deleteRow", "row=1");
		assertListRowCount(1);
	}
		
	private void assertActionsCount(String action, int expectedCount) { 
		String inputName = "ox_OpenXavaTest_Applicant__action___" + action.replace(".", "___"); 
		assertEquals(expectedCount, getHtmlPage().getElementsByName(inputName).size());		
	}
	
	private void assertHelp() throws Exception { 
		try {
			getHtmlPage().getAnchorByHref("http://openxava.wikispaces.com/help_en");
		}
		catch (ElementNotFoundException ex) {		
			fail("Help link is not correct"); 
		}
	}

	public void testModulesMenu_help() throws Exception { 
		modulesLimit = false;
		resetModule();
		
		assertModulesCount(20); 
		assertTrue(AbstractWall.class.isAnnotationPresent(MappedSuperclass.class)); // This should be the first one, but as it's MappedSupperclass in not shown
		assertFirstModuleInMenu("Academic year", ""); // Not in i18n, to test a case 

		HtmlElement searchBox = getHtmlPage().getHtmlElementById("search_modules_text");
		searchBox.type("INVOICE");
		assertEquals("INVOICE", searchBox.getAttribute("value"));		
		getWebClient().waitForBackgroundJavaScriptStartingBefore(10000);
		assertModulesCount(20); 
		assertFirstModuleInMenu("Current year invoices", "Current year invoices management"); 
		
		HtmlAnchor loadMoreModules = (HtmlAnchor) getHtmlPage().getHtmlElementById("more_modules").getParentNode();
		loadMoreModules.click();
		getWebClient().waitForBackgroundJavaScriptStartingBefore(10000);
		assertModulesCount(52); // We have to adjust this when we add new modules that content "invoice"
		
		
		searchBox.type(" \b");
		assertEquals("", searchBox.getAttribute("value"));
		getWebClient().waitForBackgroundJavaScriptStartingBefore(10000);
		assertModulesCount(20); 
		
		loadMoreModules = (HtmlAnchor) getHtmlPage().getHtmlElementById("more_modules").getParentNode();
		loadMoreModules.click();
		getWebClient().waitForBackgroundJavaScriptStartingBefore(10000);
		assertTrue(getModulesCount() > 300);
		
		searchBox.type("artist");
		getWebClient().waitForBackgroundJavaScriptStartingBefore(10000);
		assertModulesCount(1); // Test not duplicate when module in application.xml
		assertFirstModuleInMenu("Artist", ""); 
		
		assertHelp();
	}

	private void assertFirstModuleInMenu(String expectedName, String expectedDescription) {
		HtmlElement module = getHtmlPage().getHtmlElementById("modules_list_popup").getElementsByAttribute("div", "class", "module-row ").get(0);
		HtmlElement moduleName = module.getElementsByAttribute("div", "class", "module-name").get(0);
		assertEquals(expectedName, moduleName.asText());
		HtmlElement moduleDescription = module.getElementsByAttribute("div", "class", "module-description").get(0);
		assertEquals(expectedDescription, moduleDescription.asText());
	}

	public void testPolymorphicReferenceFromBaseClass_savingTwiceWithNoRefreshAfterAndHiddenKey_showHideButtons() throws Exception {  
		// Polymorphic reference from base class
		execute("Mode.detailAndFirst");
		assertNoErrors();
		assertValue("name", "JUANILLO"); 
		assertValue("skill.description", "PROGRAMMING");
		assertValue("skill.language", "JAVA");
		assertValue("platform", "MULTIPLATFORM");		 
		
		// Saving twice with no refresh after and hidden key
		execute("CRUD.new");
		setValue("name", "PEPITO");
		execute("Applicant.saveNotRefresh");
		assertNoErrors();
		assertMessage("Applicant created successfully");
		assertValue("name", "PEPITO"); // Without CREATED suffix, so not refresh
		execute("Applicant.saveNotRefresh");
		assertNoErrors();
		assertMessage("Applicant modified successfully");
		assertValue("name", "PEPITO");	
		execute("CRUD.delete");
		assertNoErrors();		
		
		// Show/hide buttons
		assertAction("Applicant.hideButtons");
		execute("Applicant.hideButtons");
		assertNoAction("CRUD.new");
		assertNoAction("Applicant.hideButtons");
		execute("Reference.createNew", "model=ProgrammingSkill,keyProperty=skill.description");
		assertAction("NewCreation.saveNew");
		execute("NewCreation.cancel");
		execute("Applicant.showButtons", "xava.keyProperty=name");
		assertAction("CRUD.new");
		assertAction("Applicant.hideButtons");
	}
	
	public void testHtmlHeadNotDuplicated_excludedActionsInControllers() throws Exception { 
		String html = getHtmlPage().getWebResponse().getContentAsString();
		assertEquals(1, StringUtils.countMatches(html, "<head>"));
		execute("CRUD.new"); 
		assertAction("Navigation.previous");
		assertAction("Navigation.next");
		assertAction("CRUD.refresh");
		assertNoAction("Navigation.first");
		assertNoAction("CRUD.save");
	}
	
	public void testListCustomizationWithTabDefaultOrder() throws Exception {  
		assertListCustomizationWithTabDefaultOrder(); 
		resetModule();
		assertListCustomizationWithTabDefaultOrder(); // Failed the second time after reseting module
	}
	
	private void assertListCustomizationWithTabDefaultOrder() throws Exception { 
		assertListColumnCount(1);
		assertListAllConfigurations("All"); 
		execute("List.addColumns");
		checkRow("selectedProperties", "skill.description"); 
		execute("AddColumns.addColumns");
		assertListColumnCount(2);
		assertListAllConfigurations("All"); 
		
		resetModule();
		assertListColumnCount(2);
		assertListAllConfigurations("All");  
		execute("List.addColumns");
		execute("AddColumns.restoreDefault");
		assertListColumnCount(1);
		assertListAllConfigurations("All"); 
	}
	
	protected String getModuleURL() { 
		return modulesLimit?super.getModuleURL():"http://" + getHost() + ":" + getPort() + "/OpenXavaTest/modules/Applicant";
	}
	
	private void assertModulesCount(int expectedCount) {
		assertEquals(expectedCount, getModulesCount());
	}
	
	private int getModulesCount() {
		return getHtmlPage().getHtmlElementById("modules_list_popup").getElementsByAttribute("div", "class", "module-name").size();
	}
			
}
