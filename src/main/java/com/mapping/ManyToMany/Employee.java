package com.mapping.ManyToMany;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String first, last;
    
    @JsonIgnore
    @ManyToMany(cascade=CascadeType.PERSIST, mappedBy="employee")
    Set<Project> project;

    public Employee() {
        super();
    }

    public Employee(int id, String first, String last, Set<Project> project) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.project = project;
    }
}
