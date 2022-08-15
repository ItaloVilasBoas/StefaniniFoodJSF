package com.stefaninifood.bean;

import javax.ejb.Stateless;
import com.stefaninifood.dao.Product;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 *
 * @author icboas
 */
@Stateless
public class ProductFacade extends AbstractFacade{
    @PersistenceContext(unitName = "StefaniniFoodPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public ProductFacade(){
        super(Product.class);
    }
}
