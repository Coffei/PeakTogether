package pv243.peaktogether.util;

import java.util.Locale;

import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@ManagedBean
@RequestScoped
public class LocaleUtils {
	
	 @Produces
	    @Named("currentLocale")
	    public Locale locale() {
	    	
	    	return FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();	
	    }

}
