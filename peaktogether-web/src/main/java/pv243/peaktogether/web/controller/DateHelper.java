package pv243.peaktogether.web.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 24.6.13
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "date")
@RequestScoped
public class DateHelper {

    private SimpleDateFormat format = new SimpleDateFormat("dd MMMM HH:mm", Locale.ENGLISH);
    public String format(Date date) {
        if(date==null)
            return "";
        return format.format(date);
    }
}
