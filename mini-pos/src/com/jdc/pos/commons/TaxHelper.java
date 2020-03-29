package com.jdc.pos.commons;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class TaxHelper {
	
    private DoubleProperty subTotalProperty = new SimpleDoubleProperty();
    private DoubleProperty taxProperty = new SimpleDoubleProperty();
    private DoubleProperty totalProperty = new SimpleDoubleProperty();
    private DoubleProperty taxRateProperty = new SimpleDoubleProperty();
    
    public TaxHelper() {
    	taxProperty.bind(subTotalProperty.divide(100).multiply(taxRateProperty));
    	totalProperty.bind(subTotalProperty.add(taxProperty));
	}
    
    public DoubleProperty subTotal() {
    	return subTotalProperty;
    }
    
    public DoubleProperty tax() {
    	return taxProperty;
    }
    
    public DoubleProperty total() {
    	return totalProperty;
    }
    
    public DoubleProperty taxRate() {
    	return taxRateProperty;
    }

}
