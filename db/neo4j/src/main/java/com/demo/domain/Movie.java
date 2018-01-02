package com.demo.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.*;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator=JSOGGenerator.class)
@NodeEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "roles")
public class Movie {
    @GraphId
    Long id;
    String title;
    String year;

    @Relationship(type="ACTS_IN", direction = Relationship.DIRECTION)
    List<Role> roles = new ArrayList<>();

    public Role addRole(Actor actor, String name){
        Role role = new Role(actor,this,name);
        this.roles.add(role);
        return role;
    }
}
