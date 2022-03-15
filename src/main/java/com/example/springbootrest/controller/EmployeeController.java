package com.example.springbootrest.controller;

import com.example.springbootrest.model.Employee;
import com.example.springbootrest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;


    // create get all employees API
    @GetMapping("/employees") // by default the browser works with GET, so we do not need to add a request method to it
    public List<Employee> getAllEmployees()
    {
        return employeeRepository.findAll();
    }

    // create employee
    @PostMapping("/employees") // in REST we can run on the same endpoint multiple CRUD operation, in order to create a user we need to use POST
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    // get employee details by id
    @GetMapping("/employees/{id}") // will return the details base on the ID (path variable) and if find wil return a response 200 OK with the user in the body otherwise a runtime error
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") long id) throws RuntimeException{
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));
        return ResponseEntity.ok().body(employee);
    }

    // update employee by id

    @PutMapping("/employees/{id}") // will return the details on the new user and will update the employee in DB using h2
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") long id, @RequestBody Employee employeeDetails) throws RuntimeException{
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        employeeRepository.save(employee);
        return ResponseEntity.ok().body(employee);
    }

    // delete employee by id
    @DeleteMapping("/employees/{id}") // will delete a employee
    public ResponseEntity<Employee> deleteEmployee(@PathVariable(value = "id") long id) throws RuntimeException{
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));
        employeeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
