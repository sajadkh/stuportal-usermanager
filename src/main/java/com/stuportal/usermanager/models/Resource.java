package com.stuportal.usermanager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Resource {
    @Id
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "resources", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public Resource(){}

    public Resource(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
