package com.demo.test;

import com.demo.config.Neo4jConfig;
import com.demo.domain.Actor;
import com.demo.domain.Movie;
import com.demo.domain.Role;
import com.demo.repositories.ActorRepository;
import com.demo.repositories.MovieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Neo4jConfig.class})
public class MovieTest {

    private static Logger logger = LoggerFactory.getLogger(MovieTest.class);

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ActorRepository actorRepository;

    @Before
    public void initData(){
        movieRepository.deleteAll();
        actorRepository.deleteAll();

        Movie matrix1 = new Movie();
        matrix1.setTitle("The Matrix");
        matrix1.setYear("1999-03-31");

        Movie matrix2 = new Movie();
        matrix2.setTitle("The Matrix Reloaded");
        matrix2.setYear("2003-05-07");

        Movie matrix3 = new Movie();
        matrix3.setTitle("The Matrix Revolutions");
        matrix3.setYear("2003-10-27");

        Actor keanu = new Actor();
        keanu.setName("Keanu Reeves");

        Actor laurence = new Actor();
        laurence.setName("Laurence Fishburne");

        Actor carrieanne = new Actor();
        carrieanne.setName("Carrie-Anne Moss");

        matrix1.addRole(keanu,  "Neo");
        matrix1.addRole(laurence, "Morpheus");
        matrix1.addRole(carrieanne,  "Trinity");
        movieRepository.save(matrix1);
        Assert.notNull(matrix1.getId());

        matrix2.addRole(keanu, "Neo");
        matrix2.addRole(laurence, "Morpheus");
        matrix2.addRole(carrieanne,  "Trinity");
        movieRepository.save(matrix2);
        Assert.notNull(matrix2.getId());

        matrix3.addRole(keanu, "Neo");
        matrix3.addRole(laurence, "Morpheus");
        matrix3.addRole(carrieanne, "Trinity");
        movieRepository.save(matrix3);
        Assert.notNull(matrix3.getId());
    }

    @Test
    public void get(){
        Movie movie = movieRepository.findByTitle("The Matrix");
        Assert.notNull(movie);
        logger.info("===movie=== movie:{}, {}",movie.getTitle(), movie.getYear());
        for(Role role : movie.getRoles()){
            logger.info("====== actor:{}, role:{}", role.getActor().getName(), role.getRole());
        }
    }
}
