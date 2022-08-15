package com.stefaninifood.bean;

import javax.ejb.Stateless;
import com.stefaninifood.dao.ClientAccount;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author icboas
 */
@Stateless
public class ClientFacade extends AbstractFacade<ClientAccount>{
    
    @PersistenceContext(unitName = "StefaniniFoodPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public ClientFacade(){
        super(ClientAccount.class);
    }
}
