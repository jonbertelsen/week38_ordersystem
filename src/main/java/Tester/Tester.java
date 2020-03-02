/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tester;

import entities.Customer;
import entities.OrderLine;
import entities.Orders;
import entities.Product;
import facade.CustomerFacade;
import facade.OrderFacade;
import facade.OrderLineFacade;
import facade.ProductFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

/**
 *
 * @author jobe
 */
public class Tester {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    
    public static void main(String[] args) {
        
        EntityManager em = emf.createEntityManager();
        
        // Delete content in all tables
        em.getTransaction().begin();
            em.createNamedQuery("OrderLine.deleteAllRows").executeUpdate();
            em.createNamedQuery("Orders.deleteAllRows").executeUpdate();
            em.createNamedQuery("Customer.deleteAllRows").executeUpdate();
            em.createNamedQuery("Product.deleteAllRows").executeUpdate();
        em.getTransaction().commit();
     
        // Create a Customer
        System.out.println("Create a customer ************");
        Customer cust = CustomerFacade.createCustomer("Jon", "jobe@cpbusiness.dk");
        
        // Find a customer
        System.out.println("Find a customer ************");
        try {
            cust = CustomerFacade.findCustomerById(cust.getId());
            System.out.println("Kunde med id = " + cust.getId() + " er fundet");
        } catch (EntityNotFoundException e){
            System.out.println("Kunde med id = " + cust.getId() + " findes ikke");
        }
        try {
            cust = CustomerFacade.findCustomerById(1212);
            System.out.println("Kunde med id = 1212 er fundet");
        } catch (EntityNotFoundException e){
            System.out.println("Kunde med id = 1212 findes ikke");
        }
       
        // Get all customers
        System.out.println("Get all customers ************");
        List<Customer> customers = CustomerFacade.getAllCustomers();  
        if (customers.size() > 0){
            for(Customer c: customers){
                System.out.printf("(%d,%s,%s)", c.getId(), c.getName(), c.getEmail());
            }
        } else {
            System.out.println("Listen af kunder er tom");
        }
        
        // Create a Product
        System.out.println("Create a product ************");
        Product p1 = ProductFacade.createProduct("Inov8 Trailtalon 250", "Fed trailsko",1195.00);
        Product p2 = ProductFacade.createProduct("Vivobarefoot SG", "Flad trailsko",895.00);
        
        // Find a product
        System.out.println("Find a product ************");
        try {
            ProductFacade.findProductById(p1.getId());
            System.out.println("Product with id = " + p1.getId() + " is found");
        } catch (EntityNotFoundException e){
            System.out.println("Product with id = " + p1.getId() + " is NOT found");
        }
        try {
            ProductFacade.findProductById(1212);
            System.out.println("Product with id = 12 is found");
        } catch (EntityNotFoundException e){
            System.out.println("Product with id = 12 is NOT found");
        }
        
        // Get all products
        System.out.println("Get all products ************");
        List<Product> products = ProductFacade.getAllProducts(); 
        
        for(Product p: products){
            System.out.printf("(%d, %s, %s, %.2f)\n", p.getId(), p.getName(), p.getDescription(), p.getPrice());
        }
        
        // Create an order
        System.out.println("Add an order ************");
        Orders o1 = OrderFacade.createOrder(new Orders());
        Orders o2 = OrderFacade.createOrder(new Orders());
        Customer c1 = CustomerFacade.createCustomer("Alfons Aaberg", "a@cpbusiness.dk");
        Customer c2 = CustomerFacade.createCustomer("Jørgen Jønke", "jj@ha.dk");
        CustomerFacade.addOrder(c1, o1);
        CustomerFacade.addOrder(c2, o2);
        System.out.printf("(%d,%s,%s, orderid: %d)\n", 
                c1.getId(), c1.getName(), c1.getEmail(), 
                c1.getOrders().get(0).getId());
         System.out.printf("(%d,%s,%s, orderid: %d)\n", 
                c2.getId(), c2.getName(), c2.getEmail(), 
                c2.getOrders().get(0).getId());
        
        // Create an orderline
        System.out.println("Add an orderline ************");
        OrderLine ol1 = OrderLineFacade.createOrderLine(p1,1);
        OrderLine ol2 = OrderLineFacade.createOrderLine(p2,2);
        OrderLine ol3 = OrderLineFacade.createOrderLine(p2,3);
        OrderFacade.addOrderLine(o1, ol1);
        OrderFacade.addOrderLine(o1, ol2);
        OrderFacade.addOrderLine(o2, ol3);
        
        // Find all orders for customer c1
        System.out.println("Find all orders, orderlines and total sum of orders ************");
        List<Customer> allCustomers = CustomerFacade.getAllCustomers();
        for (Customer custObj: allCustomers){
            List<Orders> allOrders = CustomerFacade.getAllOrdersByCustomerID(custObj.getId());
            for (Orders orderObj: allOrders){
                System.out.printf("(OrderID: %d, Customer: %s)\n",orderObj.getId(), orderObj.getCustomer().getName());
                
                for (OrderLine olObj: orderObj.getOrderlines()){
                    System.out.printf("--- (Quantity: %d pcs of %s\n", 
                            olObj.getQuantity(), olObj.getProduct().getName());
                }
                System.out.printf("Total order price: %.2f\n", OrderFacade.getOrderSum(orderObj.getId()) );
            }   
        }
    }
    
}
