package com.luv2code.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	//autowire the Customer Service
	@Autowired
	private CustomerService customerService;
	
	//add mapping for GET/customers
	@GetMapping("/customers")
	public List<Customer> getCustomers()
	{
		List<Customer> theCustomers = customerService.getCustomers();
		return theCustomers;
	}
	
	//add mapping for GET/customers/{customerId}
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId)
	{
		Customer theCustomer = customerService.getCustomer(customerId);
		
		if(theCustomer==null)
		{
			throw new CustomerNotFoundException("Customer not found "+customerId);
			
		}
		
		return theCustomer;
	}
	
	
	//add mapping for POST/customers - add new Customer
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer)
	{
		//Setting id=0 ...then the DAO will insert new Customer in the Database
		theCustomer.setId(0);
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	//add mapping for PUT/customers - update an existing customer
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer)
	{
		customerService.saveCustomer(theCustomer);
		return theCustomer;
	}
	
	//add mapping for DELETE/customers/{customerId} - delete a customer
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId)
	{	
		Customer tempCustomer = customerService.getCustomer(customerId);
		
		//throw exception if the Customer is null
		if(tempCustomer==null)
		{
			throw new CustomerNotFoundException("Customer Not Found "+customerId);
		}
		
		customerService.deleteCustomer(customerId);
		
		return "Deleted Customer id - "+customerId;
	}
}
