/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.Product;
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
public class ProductFacade {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    
    public static Product createProduct(String name, String description, double price){
        EntityManager em = emf.createEntityManager();
        Product p = new Product(name, description, price);
        try {
           em.getTransaction().begin();
           em.persist(p);
           em.getTransaction().commit();
       } finally {
           em.close();
       } 
       return p;
    }
    
    public static Product findProductById(int productID) throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        Product p = em.find(Product.class, productID);
        if (p == null) {
            throw new EntityNotFoundException("Can't find Product for ID = " + productID);
        }
        return p;
    }
    
    public static List<Product> getAllProducts(){
        EntityManager em = emf.createEntityManager();
        TypedQuery query = em.createQuery("Select p from Product p", Product.class);
        List<Product> products = query.getResultList();
        em.close();         
        return products;
    }
    
    
}
