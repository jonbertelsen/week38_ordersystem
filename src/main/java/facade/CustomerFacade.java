/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.Customer;
import entities.Orders;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author jobe
 */
public class CustomerFacade {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    
    public static Customer createCustomer(String name, String email){
        EntityManager em = emf.createEntityManager();
        Customer c = new Customer(name, email);
        try {
           em.getTransaction().begin();
           em.persist(c);
           em.getTransaction().commit();
       } finally {
           em.close();
       } 
       return c;
    }
    
    public static Customer findCustomerById(int customerID) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        Customer c = em.find(Customer.class, customerID);
        if (c == null) {
            throw new EntityNotFoundException("Can't find Customer for ID = " + customerID);
        }
        return c;
    }
    
    public static List<Customer> getAllCustomers(){
        EntityManager em = emf.createEntityManager();
        TypedQuery query = em.createQuery("Select c from Customer c", Customer.class);
        List<Customer> customers = query.getResultList();
        em.close();         
        return customers;
    }
    
   public static Customer addOrder(Customer c, Orders o){
        EntityManager em = emf.createEntityManager();
        c.addOrder(o);
        try {
           em.getTransaction().begin();
           em.merge(c);
           em.getTransaction().commit();
       } finally {
           em.close();
       } 
       return c;
    }
   
   public static List<Orders> getAllOrdersByCustomerID(int customerID) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery query = em.createQuery("SELECT o FROM Orders o JOIN o.customer c WHERE o.customer.id = :id", Orders.class);
        query.setParameter("id", customerID);
        
        List<Orders> orders = query.getResultList();
        if (orders == null) {
            throw new EntityNotFoundException("Can't find any orders for CustomerID = " + customerID);
        } else {
        return orders;
        }
   }
}
