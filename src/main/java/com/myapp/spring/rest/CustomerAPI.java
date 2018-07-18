package com.myapp.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.spring.customer.Customer;
import com.myapp.spring.customer.CustomerRepository;
import com.myapp.spring.order.OrderService;

@RestController
public class CustomerAPI {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/api/customers",method=RequestMethod.POST)
	public ResponseEntity<String> addCustomer(@RequestBody Customer customer){
		
		String msg=orderService.addNewCustomer(customer);
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
		
	}

}
