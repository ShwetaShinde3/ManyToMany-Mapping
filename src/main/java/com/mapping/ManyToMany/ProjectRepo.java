package com.mapping.ManyToMany;

import org.springframework.data.jpa.repository.JpaRepository;
public interface ProjectRepo extends JpaRepository<Project,Integer>
{
}
