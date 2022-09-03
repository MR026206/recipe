package com.lacuisine.recipe.service;

import java.util.List;
import com.lacuisine.recipe.model.PostRecipe;
import com.lacuisine.recipe.model.Recipe;
import com.lacuisine.recipe.model.RecipeSearchCriteria;
import com.lacuisine.recipe.model.UpdateRecipe;

public interface RecipeService {

  void processAddRecipe(PostRecipe recipe);

  void processDeleteRecipe(long recipeId);

  void processUpdateRecipe(Long recipeId, UpdateRecipe postRecipe);

  Recipe processGetRecipe(long recipeId);

  List<Recipe> processSearchRecipe(RecipeSearchCriteria recipeSearchCriteria);

  List<Recipe> processGetAllRecipeInformation();


}
