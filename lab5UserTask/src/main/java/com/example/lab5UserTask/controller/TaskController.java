package com.example.lab5UserTask.controller;

import com.example.lab5UserTask.model.Task;
import com.example.lab5UserTask.model.User;
import com.example.lab5UserTask.service.TaskService;
import com.example.lab5UserTask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @GetMapping
    public String getTasks(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        System.out.println("Logged in user: " + username);
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("User ID: " + user.getId());
        model.addAttribute("tasks", taskService.getTasksByUserId(user.getId()));
        return "tasks";
    }



    @GetMapping("/add")
    public String addTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";
    }

    @PostMapping("/add")
    public String saveTask(@ModelAttribute Task task) {
        taskService.saveTask(task);
        return "redirect:/tasks";
    }
}
