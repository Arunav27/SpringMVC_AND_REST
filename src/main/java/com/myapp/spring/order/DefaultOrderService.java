package com.myapp.spring.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.myapp.spring.catalog.CatalogItem;
import com.myapp.spring.catalog.CatalogItemEntity;
import com.myapp.spring.customer.Customer;
import com.myapp.spring.customer.CustomerEntity;
import com.myapp.spring.customer.CustomerRepository;



@Transactional
@Service
public class DefaultOrderService implements OrderService {
   private static final Logger log = LoggerFactory
         .getLogger(DefaultOrderService.class);

  @Autowired
   private OrderRepository orderRepository;

   @Autowired
   private OrderItemRepository orderItemRepository;
   
   @Autowired
   private CustomerRepository customerRepository;
   
   public String placeNewOrder(Order order) {
	   CustomerEntity customerEntity =new CustomerEntity();
	   customerEntity.setEmail(order.getCustomer().getEmail());
	   customerEntity.setFirstName(order.getCustomer().getFirstName());
	   customerEntity.setLastName(order.getCustomer().getLastName());
	   customerEntity.setId(order.getCustomer().getId());
	   OrderEntity orderEntity=new OrderEntity(0, customerEntity,order.getOrderNumber(),order.getTimeOrderPlaced(),order.getLastUpdate(),order.getStatus()); 
	   orderRepository.save(orderEntity);
	   
	   return "Order Placed Successfully";
	   
   }

   
   public String addNewCustomer(Customer customer) {
	   CustomerEntity customerEntity=new CustomerEntity();
	   customerEntity.setEmail(customer.getEmail());
	   customerEntity.setFirstName(customer.getFirstName());
	   customerEntity.setLastName(customer.getLastName());
	   
	  customerRepository.save(customerEntity);
	  return "Customer Added";
   }
   
   @Override
   public List<Order> getOrderDetails() {
      List<Order> orders = new ArrayList<Order>();

      try {
         populateOrderDetails(orders, orderRepository.findAll());
      } catch (Exception e) {
         log.error(
               "An error occurred while retrieving all orders: "
                     + e.getMessage(), e);
      }

      return orders;
   }

   
   @Override
   public List<Order> getOrderDetails(OrderStatus orderStatus, int fetchSize) {
      List<Order> orders = new ArrayList<Order>();

      try {
         populateOrderDetails(orders, orderRepository.findByStatus(
               orderStatus.getCode(), new PageRequest(0, fetchSize)));
      } catch (Exception e) {
         log.error("An error occurred while getting orders by order status: "
               + e.getMessage(), e);
      }

      return orders;
   }

   @Transactional(rollbackOn = Exception.class)
   @Override
   public void processOrderStatusUpdate(List<Order> orders,
         OrderStatus orderStatus) throws Exception {
      List<Long> orderIds = new ArrayList<Long>();
      for (Order order : orders) {
         orderIds.add(order.getId());
      }
      System.out.println("Size "+orderIds);
      orderRepository.updateStatus(orderStatus.getCode(),
            new Date(System.currentTimeMillis()), orderIds);
      orderItemRepository.updateStatus(orderStatus.getCode(),
            new Date(System.currentTimeMillis()), orderIds);
      for (Order order : orders) {
         order.setStatus(orderStatus.getCode());
      }
   }

   @Override
   public List<OrderItem> getOrderItems(long id) {
      List<OrderItem> orderItems = new ArrayList<OrderItem>();

      try {
         List<OrderItemEntity> orderItemEntities = orderItemRepository
               .findByOrderId(id);
         populateOrderItems(orderItems, orderItemEntities);
      } catch (Exception e) {
         log.error(
               "An error occurred while retrieving order items for the order id |"
                     + id + "|: " + e.getMessage(), e);
      }
      return orderItems;
   }

   /**
    * Populate the list of orders based on order entity details.
    * 
    * @param orders
    * @param orderEntities
    */
   private void populateOrderDetails(List<Order> orders,
         Iterable<OrderEntity> orderEntities) {
      for (Iterator<OrderEntity> iterator = orderEntities.iterator(); iterator
            .hasNext();) {
         OrderEntity entity = iterator.next();
         CustomerEntity customerEntity = entity.getCustomer();
         System.out.println("***** "+customerEntity.getId());
         Customer customer = new Customer(customerEntity.getId(),
               customerEntity.getFirstName(), customerEntity.getLastName(),
               customerEntity.getEmail());
         orders.add(new Order(entity.getId(), customer,
               entity.getOrderNumber(), entity.getTimeOrderPlaced(), entity
                     .getLastUpdate(), entity.getStatus()));
      }
   }

   private void populateOrderItems(List<OrderItem> orderItems,
         Iterable<OrderItemEntity> orderItemEntities) {
      for (Iterator<OrderItemEntity> iterator = orderItemEntities.iterator(); iterator
            .hasNext();) {
         OrderItemEntity entity = iterator.next();
         CatalogItemEntity catalogItemEntity = entity.getCatalogItem();
         CatalogItem catalogItem = new CatalogItem(catalogItemEntity.getId(),
               catalogItemEntity.getItemNumber(),
               catalogItemEntity.getItemName(), catalogItemEntity.getItemType());
         orderItems.add(new OrderItem(entity.getId(), catalogItem, entity
               .getStatus(), entity.getPrice(), entity.getLastUpdate(), entity
               .getQuantity()));
      }
   }


@Override
public void processOrderFulfillment() {
	// TODO Auto-generated method stub
	
}

}
