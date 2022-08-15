package com.stefaninifood.bean;

import com.stefaninifood.dao.Orders;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author icboas
 */
@Stateless
public class OrderFacade extends AbstractFacade<Orders>{
    @PersistenceContext(unitName = "StefaniniFoodPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public OrderFacade(){
        super(Orders.class);
    }
}
