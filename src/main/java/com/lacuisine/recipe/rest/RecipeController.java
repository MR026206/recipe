package com.lacuisine.recipe.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.lacuisine.recipe.RecipesApi;
import com.lacuisine.recipe.model.PostRecipe;
import com.lacuisine.recipe.model.Recipe;
import com.lacuisine.recipe.model.RecipeSearchCriteria;
import com.lacuisine.recipe.model.UpdateRecipe;
import com.lacuisine.recipe.service.RecipeServiceImp;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RecipeController implements RecipesApi {

  @Autowired
  private RecipeServiceImp recipeService;

  @Override
  public ResponseEntity<Void> addRecipe(PostRecipe recipe) {
    log.info("addRecipe(PostRecipe recipe) : " + recipe);
    recipeService.processAddRecipe(recipe);
    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }


  @Override
  public ResponseEntity<Void> deleteRecipe(Long recipeId) {
    log.info("deleteRecipe(Long recipeId) : " + recipeId);
    recipeService.processDeleteRecipe(recipeId);
    return ResponseEntity.status(HttpStatus.OK).body(null);

  }


  @Override
  public ResponseEntity<Recipe> getRecipe(Long recipeId) {
    log.info("getRecipe(Long recipeId) : " + recipeId);
    Recipe recipe = recipeService.processGetRecipe(recipeId);
    return ResponseEntity.status(HttpStatus.OK).body(recipe);

  }

  @Override
  public ResponseEntity<List<Recipe>> getAllRecipe() {
    log.info("getAllRecipe(): ");
    List<Recipe> recipeLst = recipeService.processGetAllRecipeInformation();
    return ResponseEntity.status(HttpStatus.OK).body(recipeLst);
  }

  @Override
  public ResponseEntity<Void> updateRecipe(Long recipeId, UpdateRecipe postRecipe) {
    log.info("updateRecipe(Long recipeId, UpdateRecipe postRecipe)  : ");
    recipeService.processUpdateRecipe(recipeId, postRecipe);
    return ResponseEntity.status(HttpStatus.OK).body(null);

  }


  @Override
  public ResponseEntity<List<Recipe>> serachRecipe(RecipeSearchCriteria recipeSearchCriteria) {
    log.info("serachRecipe(RecipeSearchCriteria recipeSearchCriteria)  : ", recipeSearchCriteria);
    List<Recipe> recipeLst = recipeService.processSearchRecipe(recipeSearchCriteria);
    return ResponseEntity.status(HttpStatus.OK).body(recipeLst);

  }


}
