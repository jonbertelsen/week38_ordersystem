package entities;

import entities.Customer;
import entities.OrderLine;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-03-02T14:24:18")
@StaticMetamodel(Orders.class)
public class Orders_ { 

    public static volatile ListAttribute<Orders, OrderLine> orderlines;
    public static volatile SingularAttribute<Orders, Integer> id;
    public static volatile SingularAttribute<Orders, Date> orderDate;
    public static volatile SingularAttribute<Orders, Customer> customer;

}