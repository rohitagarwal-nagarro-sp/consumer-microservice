package com.nagp.consumermicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagp.consumermicroservice.entity.Consumer;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Integer>
{
	Consumer findByUserName(String name);
	Consumer deleteById(int id);
}
