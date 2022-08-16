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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author icboas
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", 
            query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findById", 
            query = "SELECT p FROM Product p WHERE p.id = :product_id"),
    @NamedQuery(name = "Product.findByName", 
            query = "SELECT p FROM Product p WHERE p.name = :name"),
    @NamedQuery(name = "Product.findByDescription", 
            query = "SELECT p FROM Product p WHERE p.description = :description"),
    @NamedQuery(name = "Product.findByPrice", 
            query = "SELECT p FROM Product p WHERE p.price = :price")
})
public class Product implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    
    @Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "in_stock", columnDefinition = "boolean default true")
    private boolean in_stock;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "product_name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "product_description")
    private String description;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private double price;
    
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private VendorAccount vendor;
       
    public Product(){
    }
    
    public Product(Long id){
        this.id = id;
    }
    
    public Product(Long id, boolean in_stock, String name, String description, double price){
        this.id = id;
        this.in_stock = in_stock;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public VendorAccount getVendor() {
        return vendor;
    }

    public void setVendor(VendorAccount vendor) {
        this.vendor = vendor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isIn_stock() {
        return in_stock;
    }
    public String stringStock(){
        if(in_stock)
            return "IN STOCK";
        return "OUT OF STOCK";
    } 
    public void setIn_stock(boolean in_stock) {
        this.in_stock = in_stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
        final Product other = (Product) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
