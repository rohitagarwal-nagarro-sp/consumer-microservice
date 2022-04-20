package com.nagp.consumermicroservice.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.nagp.consumermicroservice.entity.ServiceMaster;

@FeignClient(name="admin")
public interface AdminController 
{
	
	@GetMapping("/admin/getAllServices/")
	public List<ServiceMaster> findAll();
}
