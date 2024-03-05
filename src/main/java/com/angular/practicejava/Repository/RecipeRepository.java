package com.angular.practicejava.Repository;

import com.angular.practicejava.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe,Integer> {
}
