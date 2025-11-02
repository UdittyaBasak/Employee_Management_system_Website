package com.ems.controller;

import com.ems.model.Department;
import com.ems.model.Employee;
import com.ems.service.DepartmentService;
import com.ems.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DashboardController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private DepartmentService departmentService;
    
    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        // Check if user is logged in
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        
        long totalEmployees = employeeService.getTotalEmployees();
        long totalDepartments = departmentService.getTotalDepartments();
        List<Employee> allEmployees = employeeService.getAllEmployees();
        
        // Calculate employees by department for chart
        Map<String, Long> employeesByDept = allEmployees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        
        // Get department names for chart labels
        List<Department> departments = departmentService.getAllDepartments();
        Map<String, Object> chartData = new HashMap<>();
        chartData.put("labels", departments.stream().map(Department::getDeptName).collect(Collectors.toList()));
        chartData.put("data", departments.stream()
                .map(dept -> employeesByDept.getOrDefault(dept.getDeptName(), 0L))
                .collect(Collectors.toList()));
        
        model.addAttribute("totalEmployees", totalEmployees);
        model.addAttribute("totalDepartments", totalDepartments);
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("chartData", chartData);
        model.addAttribute("employeesByDept", employeesByDept);
        
        return "dashboard";
    }
}

