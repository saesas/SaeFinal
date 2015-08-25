/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author SAE2
 */
public class JpaUtil {
    
    private static final EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("SAE01PU");
        } catch (Throwable t) {
            System.out.println("Error al iniciar el EntityManagerFactory " + t);
            t.printStackTrace();
            throw new ExceptionInInitializerError();
        }
    }

    public static EntityManagerFactory getEmf() {
        return emf;
    }    
}
