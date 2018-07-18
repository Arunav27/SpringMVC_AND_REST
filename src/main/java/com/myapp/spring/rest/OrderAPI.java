package com.myapp.spring.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.spring.order.Order;
import com.myapp.spring.order.OrderItem;
import com.myapp.spring.order.OrderService;

@RestController
public class OrderAPI {
	
	@Autowired
	private OrderService orderService;
	
	//http://localhost:8080/orders
	@RequestMapping(value="/api/orders",method=RequestMethod.GET)
	public List<Order> getAllOrders(){
		
		return orderService.getOrderDetails();
	}
	//http://localhost:8080/orders/1
	
	@RequestMapping(value="/api/orders/{id}",method=RequestMethod.GET)
public List<OrderItem> getOrderById(@PathVariable("id")long id){
		
		return orderService.getOrderItems(id);
	}
	
	@RequestMapping(value="/api/orders",method=RequestMethod.POST)
	public ResponseEntity<String> placeNewOrder(@RequestBody Order order){
		orderService.placeNewOrder(order);
		return new ResponseEntity<>("Order Created Succesfully", HttpStatus.CREATED);
		
	}

}
