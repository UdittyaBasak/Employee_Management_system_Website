package com.ems.controller;

import com.ems.model.Department;
import com.ems.service.DepartmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;
    
    @GetMapping
    public String listDepartments(Model model, HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);
        return "departments";
    }
    
    @PostMapping("/add")
    public String addDepartment(
            @RequestParam String deptName,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        
        if (deptName == null || deptName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Department name is required");
            return "redirect:/departments";
        }
        
        Department department = new Department(deptName.trim());
        
        try {
            departmentService.saveDepartment(department);
            redirectAttributes.addFlashAttribute("success", "Department added successfully");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding department: " + e.getMessage());
        }
        
        return "redirect:/departments";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteDepartment(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/login";
        }
        
        try {
            departmentService.deleteDepartment(id);
            redirectAttributes.addFlashAttribute("success", "Department deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting department: " + e.getMessage());
        }
        
        return "redirect:/departments";
    }
}

