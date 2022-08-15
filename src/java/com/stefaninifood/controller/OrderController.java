package com.stefaninifood.controller;

import com.stefaninifood.bean.OrderFacade;
import com.stefaninifood.dao.Product;
import com.stefaninifood.dao.Orders;

import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.el.ELContext;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author icboas
 */
@Named
@SessionScoped
public class OrderController implements Serializable {
    @EJB
    private OrderFacade ejbFacade;
    private List<Orders> items;
    private Orders selected = new Orders();
    
    public OrderFacade getFacade() {
        return ejbFacade;
    }

    public void setFacade(OrderFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<Orders> getItems() {
        if(items == null){
            items = getFacade().findAll();
        }
        return items;
    }

    public void setItems(List<Orders> items) {
        this.items = items;
    }

    public Orders getSelected() {
        return selected;
    }

    public void setSelected(Orders selected) {
        this.selected = selected;
    }
    
    protected void initializeEmbeddableKey() {
    }
    protected void setEmbeddableKeys() {
    }
    
    public Orders prepareCreate(){
        selected = new Orders();
        return selected;
    }
    public void addOrder(Product product){
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ClientController cController = (ClientController) elContext.getELResolver().getValue(elContext, null, "clientController");
        selected.setPrice(product.getPrice());
        selected.setClient(cController.getSelected());
        selected.setProduct(product);
        cController.updateCart(selected);
    }
    public void removeFromList(){
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ClientController cController = (ClientController) elContext.getELResolver().getValue(elContext, null, "clientController");
        cController.getSelected().getShoppingCart().remove(selected);
        cController.update();
    }
    public void create(Product product) {
        addOrder(product);
        persist(PersistAction.CREATE, "Pedido adicionado ao seu carrinho!");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        prepareCreate();
    }
    public void create() {
        persist(PersistAction.CREATE, "Pedido criado com sucesso!");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    public void update() {
        persist(PersistAction.UPDATE, "Pedido atualizado com sucesso!");
    }
    public void destroy() {
        removeFromList();
        persist(PersistAction.DELETE, "Pedido excluido!");
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
    
    public Orders getOrders(java.lang.Long id) {
        return getFacade().find(id);
    }
    public List<Orders> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Orders> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    } 
}
