package com.stefaninifood.controller;

import com.stefaninifood.bean.VendorFacade;
import com.stefaninifood.controller.util.Slug;
import com.stefaninifood.dao.ClientAccount;
import com.stefaninifood.dao.VendorAccount;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import java.io.IOException;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author icboas
 */
@Named
@SessionScoped
public class VendorController implements Serializable {
    @EJB
    private VendorFacade ejbFacade;
    private List<VendorAccount> items = null;
    private VendorAccount selected = new VendorAccount();
    
    public VendorFacade getFacade() {
        return ejbFacade;
    }

    public void setFacade(VendorFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<VendorAccount> getItems() {
        if(items == null){
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<VendorAccount> items) {
        this.items = items;
    }

    public VendorAccount getSelected() {
        return selected;
    }

    public void setSelected(VendorAccount selected) {
        this.selected = selected;
    }
    
    protected void initializeEmbeddableKey() {
    }
    protected void setEmbeddableKeys() {
    }
    public VendorAccount prepareCreate(){
        selected = new VendorAccount();
        initializeEmbeddableKey();
        return selected;
    }
    public VendorAccount prepareCreate(VendorAccount va){
        selected = va;
        initializeEmbeddableKey();
        return selected;
    }
    public void create() {
        selected.setSlug(Slug.toSlug(selected.getStoreName()));
        if(!validaEmail() && !validaStoreName())
            return;
        persist(PersistAction.CREATE, "Conta criada com sucesso!");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    public void update() {
        persist(PersistAction.UPDATE, "Conta atualizada com sucesso!");
    }
    public void destroy() {
        persist(PersistAction.DELETE, "");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, "");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "");
            }
        }
    }

    public VendorAccount getAccount(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<VendorAccount> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<VendorAccount> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    private boolean validaEmail() {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        if(!pattern.matcher(selected.getEmail()).matches()){
            JsfUtil.addErrorMessage("Digite seu email em um formato valido!!");
            return false;
        }
        if(items == null)
            return true;
        for(VendorAccount v : items){
            if(v.getEmail().equals(selected.getEmail())){
                JsfUtil.addErrorMessage("Email ja cadastrado!!");
                return false;
            }
        }
        return true;
    }
    private boolean validaStoreName(){
        if(items == null)
            return true;
        for(VendorAccount v : items){
            if(v.getSlug().equals(selected.getSlug())){
                JsfUtil.addErrorMessage("Ja existe um restaurante com esse nome no stefood!");
                return false;
            }
        }
        return true;
    }

    public void redirectToStore(VendorAccount store, ClientAccount client) throws IOException{
        String link = "http://localhost:8082/StefaniniFood/" + Slug.toSlug(store.getStoreName()) + "/" + client.getEmail().hashCode();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(link);
    }
}
