package com.stefaninifood.controller;

import com.stefaninifood.dao.Account;
import com.stefaninifood.dao.ClientAccount;
import com.stefaninifood.dao.VendorAccount;
import java.io.Serializable;
import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;

/**
 *
 * @author icboas
 */
class TiposConta{
    static final boolean VENDOR = true;
    static final boolean CLIENT = false;
}

@Named
@RequestScoped
public class AccountController implements Serializable {
    private boolean typeAccount;//false para Client e true para Vendor
    
    public boolean isTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(boolean typeAccount) {
        this.typeAccount = typeAccount;
    }
    
    public String login(Account ac) {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        if(typeAccount == TiposConta.VENDOR){
            VendorController vController = (VendorController) elContext.getELResolver().getValue(elContext, null, "vendorController");
            for(VendorAccount v : vController.getItems()){
                if(v.getEmail().equals(ac.getEmail())){
                    if(v.getPassword().equals(ac.getPassword())){
                        vController.setSelected(v);
                        return "/homeVendedor";
                    }
                    return "";
                }
            }
        }else{
            ClientController cController = (ClientController) elContext.getELResolver().getValue(elContext, null, "clientController");
            for(ClientAccount c : cController.getItems()){
                if(c.getEmail().equals(ac.getEmail())){
                    if(c.getPassword().equals(ac.getPassword())){
                        return "/homeClient";
                    }
                    return "";
                }
            }
        }
        return "";
    }
}
