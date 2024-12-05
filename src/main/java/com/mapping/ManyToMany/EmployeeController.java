package com.mapping.ManyToMany;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController
{
@Autowired
ProjectRepo pr;
@Autowired
EmployeeRepo er;

@GetMapping
public List<Employee> getAll()
{
return er.findAll();
}
@GetMapping("/{id}")
public Employee getId(@PathVariable int id)
{
return er.findById(id).orElse(null);
}
@PostMapping("")
public Employee saveAll(@RequestBody Employee p)
{
return er.save(p);

}
@PutMapping("/{id}")
public Employee putId(@PathVariable int id, @RequestBody Employee p)
{
Employee pe = er.findById(id).orElseThrow();
pe.setFirst(p.getFirst());
pe.setLast(p.getLast());
pe.setProject(p.getProject());
return er.save(pe);
}
@PutMapping("/{eid}/{id}")
public Employee addExistingProjectToEmployee(@PathVariable int id, @PathVariable int eid) {
 
    Project p = pr.findById(id).orElseThrow();
  Employee e = er.findById(eid).orElseThrow() ;
   e.getProject().add(p);  
    p.getEmployee().add(e);  
 er.save(e);  
  pr.save(p);  
    return e;
}

@DeleteMapping("/{id}")
public void deleteEmployee(@PathVariable int id) {
   Employee e = er.findById(id).orElseThrow();
   for (Project project : e.getProject()) {
        project.getEmployee().remove(e); 
    }
    for (Project project : e.getProject()) {
        pr.save(project);  
    }
    er.delete(e);
}

@GetMapping("/page/{pageNo}/{pageSize}")
public List<Employee> getPaginated(@PathVariable int pageNo, @PathVariable
int pageSize)
{
Pageable pageable = PageRequest.of(pageNo, pageSize);
Page<Employee> pagedResult = er.findAll(pageable);
return pagedResult.hasContent() ? pagedResult.getContent() : new
ArrayList<Employee>();
}

@GetMapping("/{field}/{direction}")
public List<Employee> getAllSorted(@PathVariable String field,
@PathVariable String direction)
{
Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ?
Sort.Direction.DESC : Sort.Direction.ASC;
return er.findAll(Sort.by(sortDirection, field));
}

@GetMapping("/{pageNo}/{pageSize}/{field}/{dir}")

public List<Employee> getPaginatedAndSorted(@PathVariable int pageNo,
@PathVariable int pageSize,

@PathVariable String field, @PathVariable String

dir)
{
Sort sort = dir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
Sort.by(field).ascending() :

Sort.by(field).descending();
Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
Page<Employee> pagedResult = er.findAll(pageable);
return pagedResult.hasContent() ? pagedResult.getContent() : new
ArrayList<Employee>();
}
}