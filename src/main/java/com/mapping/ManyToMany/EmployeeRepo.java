package com.mapping.ManyToMany;

import org.springframework.data.jpa.repository.JpaRepository;
public interface EmployeeRepo extends JpaRepository<Employee,Integer>
{
}
