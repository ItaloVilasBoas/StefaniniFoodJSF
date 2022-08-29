package com.stefaninifood.controller;

import com.stefaninifood.bean.ClientFacade;
import com.stefaninifood.dao.ClientAccount;
import com.stefaninifood.dao.Orders;

import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import java.io.IOException;

import java.io.Serializable;
import java.util.List;
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
public class ClientController implements Serializable {
    @EJB
    private ClientFacade ejbFacade;
    private List<ClientAccount> items = null;
    private ClientAccount selected = new ClientAccount();
    
    public ClientFacade getFacade() {
        return ejbFacade;
    }

    public void setFacade(ClientFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<ClientAccount> getItems() {
        if(items == null){
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<ClientAccount> items) {
        this.items = items;
    }

    public ClientAccount getSelected() {
        return selected;
    }

    public void setSelected(ClientAccount selected) {
        this.selected = selected;
    }
    
    protected void initializeEmbeddableKey() {
    }
    protected void setEmbeddableKeys() {
    }
    
    public ClientAccount prepareCreate(){
        selected = new ClientAccount();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        if(!validaEmail())
            return;
        persist(PersistAction.CREATE, "Conta criada com sucesso!");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        prepareCreate();
    }
    public void updateCart(Orders order) {
        selected.getShoppingCart().add(order);
        persist(PersistAction.UPDATE, "Carrinho atualizado!");
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

    public ClientAccount getAccount(java.lang.Long id) {
        return getFacade().find(id);
    }
    public List<ClientAccount> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ClientAccount> getItemsAvailableSelectOne() {
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
        for(ClientAccount c : items){
            if(c.getEmail().equals(selected.getEmail())){
                JsfUtil.addErrorMessage("Email ja cadastrado!!");
                return false;
            }
        }
        return true;
    }
    public void redirectToCart() throws IOException{
        String link = "http://localhost:8082/StefaniniFood/cart/" + selected.getEmail().hashCode();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(link);
    }
}
