package com.nagp.consumermicroservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagp.consumermicroservice.entity.Consumer;
import com.nagp.consumermicroservice.repo.ConsumerRepository;

@Service
public class ConsumerService 
{
	@Autowired
	ConsumerRepository consumerRepository;

	public List<Consumer> findAll() {
		return consumerRepository.findAll();
	}
	
	public Consumer findByName(String name) {
		return consumerRepository.findByUserName(name);
	}

	public Consumer save(Consumer consumer) {
		return consumerRepository.save(consumer);
	}

	public Optional<Consumer> findById(int id) {
		return consumerRepository.findById(id);
	}

	public Consumer deleteById(int id) {
		return consumerRepository.deleteById(id);
	}
}
