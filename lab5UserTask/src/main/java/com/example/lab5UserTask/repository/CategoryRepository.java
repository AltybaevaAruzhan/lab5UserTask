package com.example.lab5UserTask.repository;


import com.example.lab5UserTask.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}

