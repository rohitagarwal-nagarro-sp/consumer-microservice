package com.nagp.consumermicroservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagp.consumermicroservice.entity.Consumer;
import com.nagp.consumermicroservice.entity.ServiceMaster;
import com.nagp.consumermicroservice.service.ConsumerService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController	
@RequestMapping("/consumer")
public class ConsumerController {
	
	@Autowired
	ConsumerService consumerService;
	
	@Autowired
	AdminController adminController;
	
	@GetMapping("/getAllUser")
	public ResponseEntity<List<Consumer>> getAllUser() 
	{
		List<Consumer> list=consumerService.findAll();
		return new ResponseEntity<List<Consumer>>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/getUserByName")
	public ResponseEntity<Consumer> getUserByName(@RequestParam("name") String name)
	{
		return new ResponseEntity<Consumer>(consumerService.findByName(name), new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/addUser")
	public ResponseEntity<Consumer> addUser(@RequestBody Consumer user)
	{
		Consumer consumer = consumerService.save(user);
		if(consumer == null) {
			return new ResponseEntity<Consumer>(consumer,new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Consumer>(consumer,new HttpHeaders(), HttpStatus.OK);
	}
	
	@PutMapping("/editUser")
	public ResponseEntity<Consumer> editUser(@RequestBody Consumer newUser, @RequestParam("id") int id)
	{
		Consumer consumer = consumerService.findById(id).orElse(null);
		if(consumer == null) {
			return new ResponseEntity<Consumer>(consumerService.save(newUser),new HttpHeaders(),HttpStatus.OK);
		}
		else {
			newUser.setUserId(id);
			return new ResponseEntity<Consumer>(consumerService.save(newUser),new HttpHeaders(),HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/deleteUser")
	public ResponseEntity<Consumer> deleteUser(@RequestParam("id")  int id){
		return new ResponseEntity<Consumer>(consumerService.deleteById(id),new HttpHeaders(),HttpStatus.OK);
	}
	
	//Calling admin controller
	@HystrixCommand(fallbackMethod = "fallbackErrorMethod")
	@GetMapping("/dashboard")
	public ResponseEntity<List<ServiceMaster>> getAllServicesFromAdmin() 
	{
		List<ServiceMaster> list = adminController.findAll();
		return new ResponseEntity<List<ServiceMaster>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	public ResponseEntity<List<ServiceMaster>> fallbackErrorMethod() 
	{
		System.out.println("Error occurred while getting Services from Admin Microservice");
		ServiceMaster serviceMaster = new ServiceMaster();
		serviceMaster.setServiceName("Service is down");
		ArrayList<ServiceMaster> list = new ArrayList<ServiceMaster>();
		list.add(serviceMaster);
		return new ResponseEntity<List<ServiceMaster>>(list, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}
