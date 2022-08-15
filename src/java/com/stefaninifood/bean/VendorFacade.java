package com.stefaninifood.bean;

import javax.ejb.Stateless;
import com.stefaninifood.dao.VendorAccount;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author icboas
 */
@Stateless
public class VendorFacade extends AbstractFacade<VendorAccount>{
    
    @PersistenceContext(unitName = "StefaniniFoodPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public VendorFacade(){
        super(VendorAccount.class);
    }
}
