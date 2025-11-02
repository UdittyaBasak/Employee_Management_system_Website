package com.ems.controller;

import com.ems.model.Employee;
import com.ems.service.DepartmentService;
import com.ems.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private DepartmentService departmentService;
    
    @GetMapping
    public String listEmployees(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String department,
            Model model, HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        
        List<Employee> employees;
        if (search != null && !search.trim().isEmpty()) {
            employees = employeeService.searchEmployees(search.trim());
        } else if (department != null && !department.trim().isEmpty()) {
            employees = employeeService.getEmployeesByDepartment(department);
        } else {
            employees = employeeService.getAllEmployees();
        }
        
        model.addAttribute("employees", employees);
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("searchQuery", search);
        model.addAttribute("selectedDepartment", department);
        return "employees";
    }
    
    @GetMapping("/add")
    public String showAddEmployeeForm(Model model, HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employee-form";
    }
    
    @PostMapping("/add")
    public String addEmployee(
            @Valid @ModelAttribute Employee employee,
            BindingResult result,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employee-form";
        }
        
        try {
            employeeService.saveEmployee(employee);
            redirectAttributes.addFlashAttribute("success", "Employee added successfully");
            return "redirect:/employees";
        } catch (Exception e) {
            model.addAttribute("error", "Error adding employee: " + e.getMessage());
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employee-form";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + id));
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employee-form";
    }
    
    @PostMapping("/update/{id}")
    public String updateEmployee(
            @PathVariable Long id,
            @Valid @ModelAttribute Employee employee,
            BindingResult result,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employee-form";
        }
        
        employee.setEmpId(id);
        try {
            employeeService.saveEmployee(employee);
            redirectAttributes.addFlashAttribute("success", "Employee updated successfully");
            return "redirect:/employees";
        } catch (Exception e) {
            model.addAttribute("error", "Error updating employee: " + e.getMessage());
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employee-form";
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deleteEmployee(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        
        try {
            employeeService.deleteEmployee(id);
            redirectAttributes.addFlashAttribute("success", "Employee deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting employee: " + e.getMessage());
        }
        
        return "redirect:/employees";
    }
    
    @GetMapping("/view/{id}")
    public String viewEmployee(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + id));
        model.addAttribute("employee", employee);
        return "employee-details";
    }
    
    @GetMapping("/export")
    public String exportEmployees(HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        
        // This would typically export to CSV/Excel
        // For now, just return success message
        redirectAttributes.addFlashAttribute("success", "Export functionality coming soon!");
        return "redirect:/employees";
    }
}

