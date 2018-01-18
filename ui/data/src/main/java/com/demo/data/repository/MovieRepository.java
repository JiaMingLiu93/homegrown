package com.demo.data.repository;

import com.demo.data.domain.Movie;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends GraphRepository<Movie>{
    Movie findByName(@Param("name") String name);
}
