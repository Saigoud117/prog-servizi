
// File generated by OpenXava: Fri Jun 15 11:27:37 CEST 2018
// Archivo generado por OpenXava: Fri Jun 15 11:27:37 CEST 2018

// WARNING: NO EDIT
// OJO: NO EDITAR
// Component: Invoice		Aggregate/Agregado: InvoiceDetail

package org.openxava.test.model;

import java.util.*;
import java.math.*;
import java.rmi.RemoteException;
import org.openxava.component.MetaComponent;
import org.openxava.model.meta.MetaModel;
import org.openxava.util.*;

/**
 * 
 * @author MCarmen Gimeno
 */
public class InvoiceDetail implements java.io.Serializable, org.openxava.test.model.IInvoiceDetail {	

	// Constructor
	public InvoiceDetail() {
		initMembers();
	} 

	private void initMembers() { 
		setOid(null); 
		setServiceType(0); 
		setQuantity(0); 
		setUnitPrice(null); 
		setDeliveryDate(null); 
		setRemarks(null); 	
	} 
	
	// Properties/Propiedades 	
	/**
	 * 
	 * 
	 */
	public java.math.BigDecimal getAmount() { 		
		try {			
			org.openxava.test.calculators.DetailAmountCalculator amountCalculator= (org.openxava.test.calculators.DetailAmountCalculator)
				getMetaModel().getMetaProperty("amount").getMetaCalculator().createCalculator();  	
			
			amountCalculator.setUnitPrice(getUnitPrice());  	
			
			amountCalculator.setQuantity(getQuantity()); 
			return (java.math.BigDecimal) amountCalculator.calculate();
		}
		catch (NullPointerException ex) {
			// Usually for multilevel property access with null references 
			return null; 			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(XavaResources.getString("generator.calculate_value_error", "Amount", "InvoiceDetail", ex.getLocalizedMessage()));
		} 		
	}
	public void setAmount(java.math.BigDecimal newAmount) {
		// for it is in value object
		// para que aparezca en los value objects
	} 	
	/**
	 * 
	 * 
	 */
	public boolean isFree() { 		
		try {			
			org.openxava.test.calculators.InvoiceDetailIsFreeCalculator freeCalculator= (org.openxava.test.calculators.InvoiceDetailIsFreeCalculator)
				getMetaModel().getMetaProperty("free").getMetaCalculator().createCalculator(); 
				freeCalculator.setModel(this); 
			return ((Boolean) freeCalculator.calculate()).booleanValue();
		}
		catch (NullPointerException ex) {
			// Usually for multilevel property access with null references
			return false; 			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(XavaResources.getString("generator.calculate_value_error", "Free", "InvoiceDetail", ex.getLocalizedMessage()));
		} 		
	}
	public void setFree(boolean newFree) {
		// for it is in value object
		// para que aparezca en los value objects
	} 
	private String oid;
	public String getOid() {
		return oid;
	}
	public void setOid(String newOid) {
		this.oid = newOid;
	} 
	private static org.openxava.converters.IConverter remarksConverter;
	private org.openxava.converters.IConverter getRemarksConverter() {
		if (remarksConverter == null) {
			try {
				remarksConverter = (org.openxava.converters.IConverter) 
					getMetaModel().getMapping().getConverter("remarks");
			}
			catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(XavaResources.getString("generator.create_converter_error", "remarks"));
			}
			
		}	
		return remarksConverter;
	} 
	private java.lang.String remarks;
	private java.lang.String get_Remarks() {
		return remarks;
	}
	private void set_Remarks(java.lang.String newRemarks) {
		this.remarks = newRemarks;
	} 	
	
	/**
	 * 
	 * 
	 */
	public java.lang.String getRemarks() {
		try {
			return (java.lang.String) getRemarksConverter().toJava(get_Remarks());
		}
		catch (org.openxava.converters.ConversionException ex) {
			ex.printStackTrace();
			throw new RuntimeException(XavaResources.getString("generator.conversion_error", "Remarks", "InvoiceDetail", "java.lang.String"));
		}
	}
	
	/**
	 * 
	 */
	public void setRemarks(java.lang.String newRemarks) {
		try { 
			set_Remarks((java.lang.String) getRemarksConverter().toDB(newRemarks));
		}
		catch (org.openxava.converters.ConversionException ex) {
			ex.printStackTrace();
			throw new RuntimeException(XavaResources.getString("generator.conversion_error", "Remarks", "InvoiceDetail", "java.lang.String"));
		}		
	} 
	private static org.openxava.converters.Date3Converter deliveryDateConverter;
	private org.openxava.converters.Date3Converter getDeliveryDateConverter() {
		if (deliveryDateConverter == null) {
			try {
				deliveryDateConverter = (org.openxava.converters.Date3Converter) 
					getMetaModel().getMapping().getMultipleConverter("deliveryDate");
			}
			catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(XavaResources.getString("generator.create_converter_error", "deliveryDate"));
			}
			
		}	
		return deliveryDateConverter;
	} 
	private int deliveryDate_day;
	private int getDeliveryDate_day() {
		return deliveryDate_day;
	}
	private void setDeliveryDate_day(int newValue) {
		this.deliveryDate_day = newValue;
	} 
	private int deliveryDate_month;
	private int getDeliveryDate_month() {
		return deliveryDate_month;
	}
	private void setDeliveryDate_month(int newValue) {
		this.deliveryDate_month = newValue;
	} 
	private int deliveryDate_year;
	private int getDeliveryDate_year() {
		return deliveryDate_year;
	}
	private void setDeliveryDate_year(int newValue) {
		this.deliveryDate_year = newValue;
	} 
	/**
	 * 
	 * 
	 */
	public java.util.Date getDeliveryDate() {
		try { 
			getDeliveryDateConverter().setDay(getDeliveryDate_day()); 
			getDeliveryDateConverter().setMonth(getDeliveryDate_month()); 
			getDeliveryDateConverter().setYear(getDeliveryDate_year()); 
			return (java.util.Date) getDeliveryDateConverter().toJava();
		}
		catch (org.openxava.converters.ConversionException ex) {
			ex.printStackTrace();
			throw new RuntimeException(XavaResources.getString("generator.conversion_error", "DeliveryDate", "InvoiceDetail", "java.util.Date"));
		}
	}

	/**
	 * 
	 */
	public void setDeliveryDate(java.util.Date newDeliveryDate) {
		try { 
			getDeliveryDateConverter().toDB(newDeliveryDate); 
			setDeliveryDate_day(getDeliveryDateConverter().getDay()); 
			setDeliveryDate_month(getDeliveryDateConverter().getMonth()); 
			setDeliveryDate_year(getDeliveryDateConverter().getYear()); 			
		}
		catch (org.openxava.converters.ConversionException ex) {
			ex.printStackTrace();
			throw new RuntimeException(XavaResources.getString("generator.conversion_db_error", "DeliveryDate", "InvoiceDetail"));
		}		
	} 
	private static org.openxava.converters.IConverter unitPriceConverter;
	private org.openxava.converters.IConverter getUnitPriceConverter() {
		if (unitPriceConverter == null) {
			try {
				unitPriceConverter = (org.openxava.converters.IConverter) 
					getMetaModel().getMapping().getConverter("unitPrice");
			}
			catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(XavaResources.getString("generator.create_converter_error", "unitPrice"));
			}
			
		}	
		return unitPriceConverter;
	} 
	private java.math.BigDecimal unitPrice;
	private java.math.BigDecimal get_UnitPrice() {
		return unitPrice;
	}
	private void set_UnitPrice(java.math.BigDecimal newUnitPrice) {
		this.unitPrice = newUnitPrice;
	} 	
	
	/**
	 * 
	 * 
	 */
	public java.math.BigDecimal getUnitPrice() {
		try {
			return (java.math.BigDecimal) getUnitPriceConverter().toJava(get_UnitPrice());
		}
		catch (org.openxava.converters.ConversionException ex) {
			ex.printStackTrace();
			throw new RuntimeException(XavaResources.getString("generator.conversion_error", "UnitPrice", "InvoiceDetail", "java.math.BigDecimal"));
		}
	}
	
	/**
	 * 
	 */
	public void setUnitPrice(java.math.BigDecimal newUnitPrice) {
		try { 
			set_UnitPrice((java.math.BigDecimal) getUnitPriceConverter().toDB(newUnitPrice));
		}
		catch (org.openxava.converters.ConversionException ex) {
			ex.printStackTrace();
			throw new RuntimeException(XavaResources.getString("generator.conversion_error", "UnitPrice", "InvoiceDetail", "java.math.BigDecimal"));
		}		
	} 
	private static org.openxava.converters.IConverter quantityConverter;
	private org.openxava.converters.IConverter getQuantityConverter() {
		if (quantityConverter == null) {
			try {
				quantityConverter = (org.openxava.converters.IConverter) 
					getMetaModel().getMapping().getConverter("quantity");
			}
			catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(XavaResources.getString("generator.create_converter_error", "quantity"));
			}
			
		}	
		return quantityConverter;
	} 
	private java.lang.Integer quantity;
	private java.lang.Integer get_Quantity() {
		return quantity;
	}
	private void set_Quantity(java.lang.Integer newQuantity) {
		this.quantity = newQuantity;
	} 	
	
	/**
	 * 
	 * 
	 */
	public int getQuantity() {
		try {
			return ((Integer) getQuantityConverter().toJava(get_Quantity())).intValue();
		}
		catch (org.openxava.converters.ConversionException ex) {
			ex.printStackTrace();
			throw new RuntimeException(XavaResources.getString("generator.conversion_error", "Quantity", "InvoiceDetail", "int"));
		}
	}
	
	/**
	 * 
	 */
	public void setQuantity(int newQuantity) {
		try { 
			set_Quantity((java.lang.Integer) getQuantityConverter().toDB(new Integer(newQuantity)));
		}
		catch (org.openxava.converters.ConversionException ex) {
			ex.printStackTrace();
			throw new RuntimeException(XavaResources.getString("generator.conversion_error", "Quantity", "InvoiceDetail", "int"));
		}		
	} 
	private static org.openxava.converters.IConverter serviceTypeConverter;
	private org.openxava.converters.IConverter getServiceTypeConverter() {
		if (serviceTypeConverter == null) {
			try {
				serviceTypeConverter = (org.openxava.converters.IConverter) 
					getMetaModel().getMapping().getConverter("serviceType");
			}
			catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(XavaResources.getString("generator.create_converter_error", "serviceType"));
			}
			
		}	
		return serviceTypeConverter;
	} 
	private java.lang.Integer serviceType;
	private java.lang.Integer get_ServiceType() {
		return serviceType;
	}
	private void set_ServiceType(java.lang.Integer newServiceType) {
		this.serviceType = newServiceType;
	} 	
	
	/**
	 * 
	 * 
	 */
	public int getServiceType() {
		try {
			return ((Integer) getServiceTypeConverter().toJava(get_ServiceType())).intValue();
		}
		catch (org.openxava.converters.ConversionException ex) {
			ex.printStackTrace();
			throw new RuntimeException(XavaResources.getString("generator.conversion_error", "ServiceType", "InvoiceDetail", "int"));
		}
	}
	
	/**
	 * 
	 */
	public void setServiceType(int newServiceType) {
		try { 
			set_ServiceType((java.lang.Integer) getServiceTypeConverter().toDB(new Integer(newServiceType)));
		}
		catch (org.openxava.converters.ConversionException ex) {
			ex.printStackTrace();
			throw new RuntimeException(XavaResources.getString("generator.conversion_error", "ServiceType", "InvoiceDetail", "int"));
		}		
	} 

	// References/Referencias 

	private org.openxava.test.model.IProduct product; 	
	public org.openxava.test.model.IProduct getProduct() {
		if (product != null) {
			// Because not-found='ignore' annul lazy initialization, we simulate it
			try {
				product.toString();
			}
			catch (Exception ex) {
				return null;
			}
		}	
		return product;
	}
	public void setProduct(org.openxava.test.model.IProduct newProduct) {
		if (newProduct != null && !(newProduct instanceof org.openxava.test.model.Product)) {
			throw new IllegalArgumentException(XavaResources.getString("ejb_to_pojo_illegal")); 
		}
		this.product = newProduct; 
	} 

	private org.openxava.test.model.IInvoice invoice; 	
	public org.openxava.test.model.IInvoice getInvoice() {
		if (invoice != null) {
			// Because not-found='ignore' annul lazy initialization, we simulate it
			try {
				invoice.toString();
			}
			catch (Exception ex) {
				return null;
			}
		}	
		return invoice;
	}
	public void setInvoice(org.openxava.test.model.IInvoice newInvoice) {
		if (newInvoice != null && !(newInvoice instanceof org.openxava.test.model.Invoice)) {
			throw new IllegalArgumentException(XavaResources.getString("ejb_to_pojo_illegal")); 
		}
		this.invoice = newInvoice; 
	} 

	private org.openxava.test.model.ISeller soldBy; 	
	public org.openxava.test.model.ISeller getSoldBy() {
		if (soldBy != null) {
			// Because not-found='ignore' annul lazy initialization, we simulate it
			try {
				soldBy.toString();
			}
			catch (Exception ex) {
				return null;
			}
		}	
		return soldBy;
	}
	public void setSoldBy(org.openxava.test.model.ISeller newSeller) {
		if (newSeller != null && !(newSeller instanceof org.openxava.test.model.Seller)) {
			throw new IllegalArgumentException(XavaResources.getString("ejb_to_pojo_illegal")); 
		}
		this.soldBy = newSeller; 
	} 

	// Colecciones/Collections 

	// Methods/Metodos 	

	// User defined finders/Buscadores definidos por el usuario 


	private static MetaModel metaModel;
	public MetaModel getMetaModel() throws XavaException {
		if (metaModel == null) { 
			metaModel = MetaComponent.get("Invoice").getMetaAggregate("InvoiceDetail"); 	
		}
		return metaModel;
	}
	
	public String toString() {		
		try {
			return getMetaModel().toString(this);
		}
		catch (XavaException ex) {
			System.err.println(XavaResources.getString("toString_warning", "InvoiceDetail"));
			return super.toString();
		}		
	}

	public boolean equals(Object other) {		
		if (other == null) return false;
		return toString().equals(other.toString());
	}
	
	public int hashCode() {		
		return toString().hashCode();
	}
	
}