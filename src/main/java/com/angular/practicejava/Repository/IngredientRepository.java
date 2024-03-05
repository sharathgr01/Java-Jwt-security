package com.angular.practicejava.Repository;

import com.angular.practicejava.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient,Integer> {
}
