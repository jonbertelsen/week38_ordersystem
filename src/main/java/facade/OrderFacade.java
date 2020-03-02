/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.OrderLine;
import entities.Orders;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author jobe
 */
public class OrderFacade {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    
    public static Orders createOrder(Orders o){
        EntityManager em = emf.createEntityManager();
        try {
           em.getTransaction().begin();
           em.persist(o);
           em.getTransaction().commit();
       } finally {
           em.close();
       } 
       return o;
    }
    
    public static Orders addOrderLine(Orders o, OrderLine ol){
        EntityManager em = emf.createEntityManager();
        o.addOrderline(ol);
        try {
           em.getTransaction().begin();
           em.merge(o);
           em.getTransaction().commit();
       } finally {
           em.close();
       } 
       return o;
    }
    
    public static double getOrderSum(int orderID) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery query = em.createQuery("SELECT SUM(ol.product.price * ol.quantity) FROM Orders o JOIN o.orderlines ol WHERE o.id = :id", Orders.class);
        query.setParameter("id", orderID);
        double sum = (double) query.getSingleResult();
        return sum;
    }
    
}
