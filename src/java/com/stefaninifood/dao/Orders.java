package com.stefaninifood.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
            query = "SELECT o FROM Orders o WHERE o.id = :order_id")
})
public class Orders implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    private ClientAccount client;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;
    
    @Column(name = "order_data")
    private Date data;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(name = "total_price")
    private double total;
    
    public Orders(){
    }

    public Orders(Long id, ClientAccount client, Product product, Date data, String paymentMethod, double shippingTax) {
        this.id = id;
        this.client = client;
        this.product = product;
        this.data = data;
        this.paymentMethod = paymentMethod;
        this.total = shippingTax;
    }
        
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
