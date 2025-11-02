package com.ems.service;

import com.ems.model.Department;
import com.ems.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }
    
    public Department saveDepartment(Department department) {
        // Check if department name already exists
        Optional<Department> existingDept = departmentRepository.findByDeptName(department.getDeptName());
        if (existingDept.isPresent() && !existingDept.get().getDeptId().equals(department.getDeptId())) {
            throw new IllegalArgumentException("Department name already exists");
        }
        return departmentRepository.save(department);
    }
    
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
    
    public long getTotalDepartments() {
        return departmentRepository.count();
    }
    
    public Optional<Department> findByDeptName(String deptName) {
        return departmentRepository.findByDeptName(deptName);
    }
}

