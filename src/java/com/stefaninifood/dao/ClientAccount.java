package com.stefaninifood.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author icboas
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientAccount.findAll", 
            query = "SELECT c FROM ClientAccount c"),
    @NamedQuery(name = "ClientAccount.findByEmail", 
            query = "SELECT c FROM ClientAccount c WHERE c.email = :email")
})
public class ClientAccount extends Account{ 
    @OneToMany(mappedBy = "client")
    private List<Orders> shoppingCart = new ArrayList<Orders>();
    
    public ClientAccount(){ 
    }
    
    public ClientAccount(String email, String password){
        super(email, password);
    }

    public ClientAccount(List<Orders> shoppingCart, String email, String password) {
        super(email, password);
        this.shoppingCart = shoppingCart;
    }

    public List<Orders> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<Orders> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
    
}
