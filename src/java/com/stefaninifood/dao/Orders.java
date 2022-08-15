package com.stefaninifood.dao;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author icboas
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", 
            query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findById", 
            query = "SELECT o FROM Orders o WHERE o.id = :order_id"),
    @NamedQuery(name = "Orders.findByPrice", 
            query = "SELECT o FROM Orders o WHERE o.price = :price")
})
public class Orders implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    private double price;
    
    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    private ClientAccount client;

    @ManyToOne
    private Product product;

    public Orders(){
        
    }
    public Orders(Long id, double price, ClientAccount client, Product product) {
        this.id = id;
        this.price = price;
        this.client = client;
        this.product = product;
    }
    public Orders(double price, ClientAccount client, Product product) {
            this.price = price;
            this.client = client;
            this.product = product;
        }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ClientAccount getClient() {
        return client;
    }

    public void setClient(ClientAccount client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Orders other = (Orders) obj;
        return Objects.equals(this.id, other.id);
    }
     
    
}
