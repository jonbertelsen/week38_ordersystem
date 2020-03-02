/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.OrderLine;
import entities.Product;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author jobe
 */
public class OrderLineFacade {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    
    public static OrderLine createOrderLine(Product p, int quantity){
        EntityManager em = emf.createEntityManager();
        OrderLine ol = new OrderLine(p, quantity);
        try {
           em.getTransaction().begin();
           em.persist(ol);
           em.getTransaction().commit();
       } finally {
           em.close();
       } 
       return ol;
    }
    
    
}
