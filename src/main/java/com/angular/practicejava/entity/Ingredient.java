package com.angular.practicejava.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer amount;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    @JsonBackReference
    private Recipe recipe;

    public Ingredient(Integer id, String name, Integer amount, Recipe recipe) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.recipe = recipe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient() {
    }
}
