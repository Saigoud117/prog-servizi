package pdfReport;

import org.openxava.util.*;

public class MyReportParametersProvider implements IReportParametersProvider {
 
    public String getOrganization() {
        return "report eseguito da " + Users.getCurrent();
    }
 
}
