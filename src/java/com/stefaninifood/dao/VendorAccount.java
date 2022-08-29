package com.stefaninifood.dao;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @NamedQuery(name = "VendorAccount.findAll", 
            query = "SELECT v FROM VendorAccount v"),
    @NamedQuery(name = "VendorAccount.findByEmail", 
            query = "SELECT v FROM VendorAccount v WHERE v.email = :email")
})
public class VendorAccount extends Account{
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "store_name")
    private String storeName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "vendor_name")
    private String vendorName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "store_segment")
    private String segment;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "store_address")
    private String address;
    
    @OneToMany(mappedBy = "vendor")
    private List<Product> products;
    
    @Column(nullable = false)
    private String slug;
    
    @Column(name="shipping_tax", nullable = false)
    private Double shippingTax;
    
    public VendorAccount() {
    }

    public VendorAccount(String email, String password) {
        super(email, password);
    }

    public String getStoreName() {
        return storeName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Double getShippingTax() {
        return shippingTax;
    }

    public void setShippingTax(Double shippingTax) {
        this.shippingTax = shippingTax;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    
    
}
