package com.mapping.ManyToMany;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/project")
public class ProjectController
{
@Autowired
ProjectRepo pr;
@Autowired
EmployeeRepo er;
@GetMapping

public List<Project> getAll() {
return pr.findAll();
}
@GetMapping("/{id}")
public Project getId(@PathVariable int id)
{
return pr.findById(id).orElse(null);
}
@PostMapping("")
public Project saveAll(@RequestBody Project p)
{
return pr.save(p);
}
@PutMapping("/{id}")
public Project putId(@PathVariable int id, @RequestBody Project p)
{
Project pe = pr.findById(id).orElseThrow();
pe.setStart(p.getStart());
pe.setEnd(p.getEnd());
pe.setEmployee(p.getEmployee());
pe.setName(p.getName());
return pr.save(pe);
}

//adding employee present in employee table to project present in project table
@PutMapping("/{id}/{eid}")
public Project addExistingEmployeeToProject (@PathVariable int id, @PathVariable int eid)
{
Project p = pr.findById(id).orElseThrow();
Employee e = er.findById(eid).orElseThrow();
e.getProject().add(p);  
p.getEmployee().add(e);  
er.save(e);  
pr.save(p);  
return p;
}
@DeleteMapping("/{id}")
public void deleteId(@PathVariable int id)
{
	Project p = pr.findById(id).orElseThrow();
	for(Employee e:p.getEmployee()) {
		e.getProject().remove(p);
	}
	for(Employee e:p.getEmployee()) {
		er.save(e);
	}
pr.delete(p);
}
@GetMapping("/page/{pageNo}/{pageSize}")
public List<Project> getPaginated(@PathVariable int pageNo, @PathVariable int
pageSize)
{
Pageable pageable = PageRequest.of(pageNo, pageSize);
Page<Project> pagedResult = pr.findAll(pageable);
return pagedResult.hasContent() ? pagedResult.getContent() : new
ArrayList<Project>();
}
@GetMapping("/{field}/{direction}")
public List<Project> getAllSorted(@PathVariable String field, @PathVariable
String direction)
{
Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ?
Sort.Direction.DESC : Sort.Direction.ASC;
return pr.findAll(Sort.by(sortDirection, field));
}
}
