/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stefaninifood.controller;

import com.stefaninifood.bean.ProductFacade;
import com.stefaninifood.dao.Product;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author icboas
 */
@Named
@ApplicationScoped
public class ImageController {
    @EJB
    private ProductFacade ejbFacade; 
    
    public byte[] getImage(Long productId) {
        return ((Product)ejbFacade.find(productId)).getImage();
    }
}
