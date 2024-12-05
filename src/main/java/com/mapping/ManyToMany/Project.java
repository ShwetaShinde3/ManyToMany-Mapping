package com.mapping.ManyToMany;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name, start, end;
    
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name="project_emp",
    joinColumns=@JoinColumn(name="project_id"))
    Set<Employee> employee;

    public Project() {
        super();
    }

    public Project(int id, String name, String start, String end, Set<Employee> employee) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.employee = employee;
    }
}
