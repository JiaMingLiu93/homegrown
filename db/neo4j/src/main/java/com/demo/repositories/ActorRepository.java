package com.demo.repositories;

import com.demo.domain.Actor;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends GraphRepository<Actor>{
}
