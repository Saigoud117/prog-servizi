

// File generated by OpenXava: Fri Jun 15 11:27:36 CEST 2018
// Archivo generado por OpenXava: Fri Jun 15 11:27:36 CEST 2018

// WARNING: NO EDIT
// OJO: NO EDITAR
// Component: Clerk		Java interface for entity/Interfaz java para Entidad

package org.openxava.test.model;

import java.math.*;
import java.rmi.RemoteException;


public interface IClerk  extends org.openxava.model.IModel {	

	// Properties/Propiedades 	
	public static final String PROPERTY_onVacation = "onVacation"; 
	Boolean getOnVacation() throws RemoteException;
	void setOnVacation(Boolean onVacation) throws RemoteException; 	
	public static final String PROPERTY_arrivalTime = "arrivalTime"; 
	java.sql.Time getArrivalTime() throws RemoteException;
	void setArrivalTime(java.sql.Time arrivalTime) throws RemoteException; 	
	public static final String PROPERTY_endingTime = "endingTime"; 
	String getEndingTime() throws RemoteException;
	void setEndingTime(String endingTime) throws RemoteException; 	
	public static final String PROPERTY_name = "name"; 
	String getName() throws RemoteException;
	void setName(String name) throws RemoteException; 	
	public static final String PROPERTY_officeNumber = "officeNumber"; 	
	int getOfficeNumber() throws RemoteException; 	
	public static final String PROPERTY_number = "number"; 	
	int getNumber() throws RemoteException; 	
	public static final String PROPERTY_zoneNumber = "zoneNumber"; 	
	int getZoneNumber() throws RemoteException; 	
	public static final String PROPERTY_comments = "comments"; 
	java.lang.String getComments() throws RemoteException;
	void setComments(java.lang.String comments) throws RemoteException;		

	// References/Referencias

	// Methods 


}