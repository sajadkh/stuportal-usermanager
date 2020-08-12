package com.stuportal.usermanager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Role {
    @Id
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Resource> resources = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> users;

    public Role(){}

    public Role(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void addResource(Resource resource){
        resources.add(resource);
        resource.getRoles().add(this);
    }

    public void deleteResource(Resource resource){
        resources.remove(resource);
        resource.getRoles().remove(this);
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", resources=" + resources.stream().map(Resource::getName).collect(Collectors.toList()) +
                '}';
    }
}
