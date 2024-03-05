package com.angular.practicejava.controller;

import com.angular.practicejava.Repository.IngredientRepository;
import com.angular.practicejava.Repository.PostDataRepository;
import com.angular.practicejava.Repository.RecipeRepository;
import com.angular.practicejava.dto.AuthRequest;
import com.angular.practicejava.dto.AuthResponse;
import com.angular.practicejava.entity.PostData;
import com.angular.practicejava.entity.Recipe;
import com.angular.practicejava.security.JwtHelper;
import com.google.gson.Gson;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AngularController {

    @Autowired
    PostDataRepository repository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AngularController.class);

    @GetMapping("/get")
    public List<PostData> getContent() {
        return repository.findAll();
    }

    @PostMapping("/post")
    public String storeContent(@RequestBody PostData postdata) {
        repository.save(postdata);
        return "Stored Successful";
    }

    @DeleteMapping("/delete")
    public String deleteContent() {
        repository.deleteAll();
        return "Deletion Successful";
    }

    @PutMapping("/saveRecipes")
    public ResponseEntity<String> saveRecipes(@RequestBody List<Recipe> recipes) {
//        System.out.println(new Gson().toJson(recipes));
        List<Recipe> filteredRecipeList = recipes.stream().filter(recipe -> recipe.getId()==null).toList();
        recipeRepository.saveAll(filteredRecipeList);
        return ResponseEntity.ok("Recipes Saved Successfully");
    }

    @GetMapping("/getRecipes")
    public ResponseEntity<List<Recipe>> getAllRecipes(){
        return ResponseEntity.ok(recipeRepository.findAll());
    }

    @GetMapping("/hello")
    public String helloWorld(){
        return "hello World!";
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        doAuthenticate(authRequest.getEmail(),authRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        String token = this.helper.generateToken(userDetails);

        AuthResponse response = AuthResponse.builder().jwtToken(token).username(userDetails.getUsername()).build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

}
