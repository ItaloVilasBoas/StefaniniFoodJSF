package com.stefaninifood.controller;

import com.stefaninifood.bean.ProductFacade;
import com.stefaninifood.dao.Product;
import controller.util.JsfUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author icboas
 */
@Named
@SessionScoped
public class ProductController implements Serializable{
    @EJB
    private ProductFacade ejbFacade;
    private List<Product> items = null;
    private Product selected = new Product();

    public ProductFacade getFacade() {
        return ejbFacade;
    }

    public void setFacade(ProductFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public Product getSelected() {
        return selected;
    }

    public void setSelected(Product selected) {
        this.selected = selected;
    }
    protected void initializeEmbeddableKey() {
    }
    protected void setEmbeddableKeys() {
    }
    public Product prepareCreate(){
        selected = new Product();
        return selected;
    }
    public void addInList(){
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        VendorController vController = (VendorController) elContext.getELResolver().getValue(elContext, null, "vendorController");
        selected.setVendor(vController.getSelected());
        selected.setIn_stock(true);
        vController.getSelected().getProducts().add(selected);
        vController.update();
    }
    public void removeFromList(){
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        VendorController vController = (VendorController) elContext.getELResolver().getValue(elContext, null, "vendorController");
        vController.getSelected().getProducts().remove(selected);
        vController.update();
    }
    
    public void handleFileUpload(FileUploadEvent event){
        selected.setImage(null);
        try{
            UploadedFile uf = event.getFile();
            selected.setImage(uf.getContents());
            JsfUtil.addSuccessMessage("Imagem carregada!");
        }catch(Exception e){
        }
    }
    
    public void create() {
        addInList();
        persist(JsfUtil.PersistAction.CREATE, "Conta criada com sucesso!");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        prepareCreate();
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, "Produto Atualizado com sucesso!");
    }
    
    public void destroy(Product p) {
        selected = p;
        removeFromList();
        persist(JsfUtil.PersistAction.DELETE, "Produto Excluido com sucesso!");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    public void destroy() {
        removeFromList();
        persist(JsfUtil.PersistAction.DELETE, "Produto Excluido com sucesso!");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
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
                    JsfUtil.addErrorMessage(ex, "PersistenceErrorOccured");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "PersistenceErrorOccured");
            }
        }
    }

    public Product getProduct(java.lang.Long id) {
        return (Product) getFacade().find(id);
    }

    public List<Product> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Product> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    public boolean validaDuplicata(){
        if(selected.getVendor().getProducts() == null)
            return true;
        for(Product p : selected.getVendor().getProducts()){
            if(p.getName().equals(selected.getName()) && p.getDescription().equals(selected.getDescription())){
                JsfUtil.addErrorMessage("Sua loja já possui esse produto!");
                return false;
            }
        }
        return true;
    }
}
