package com.lacuisine.recipe.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lacuisine.recipe.constants.ApplicationConstants;
import com.lacuisine.recipe.entity.RecipeEntity;
import com.lacuisine.recipe.exceptions.RecipeException;
import com.lacuisine.recipe.model.Ingredients;
import com.lacuisine.recipe.model.PostRecipe;
import com.lacuisine.recipe.model.Recipe;
import com.lacuisine.recipe.model.RecipeSearchCriteria;
import com.lacuisine.recipe.model.UpdateRecipe;
import com.lacuisine.recipe.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeServiceImp implements RecipeService {

  @Autowired
  private RecipeRepository recipeRepository;

  /**
   * 
   * @param recipe
   */
  @Override
  public void processAddRecipe(PostRecipe recipe) {
    log.debug("processAddRecipe(PostRecipe recipe) : ", recipe.toString());
    ObjectMapper mapper = new ObjectMapper();
    RecipeEntity recipeEntity = mapper.convertValue(recipe, RecipeEntity.class);
    recipeRepository.save(recipeEntity);
  }


  /**
   * 
   * @param recipeId
   */
  @Override
  public void processDeleteRecipe(long recipeId) {
    log.debug("processDeleteRecipe(long recipeId) : ", recipeId);
    RecipeEntity recipeEntityFound = recipeRepository.findById(recipeId)
        .orElseThrow(() -> new RecipeException(HttpStatus.NOT_FOUND,
            ApplicationConstants.ERROR_MESG_RECIPE_DOES_NOT_EXIST,
            ApplicationConstants.ERROR_MESG_CODE_RECIPE_DOES_NOT_EXIST));

    recipeRepository.deleteById(recipeEntityFound.getRecipeId());

  }


  /**
   * 
   * @param recipeId
   * @param updateRecipe
   */
  @Override
  public void processUpdateRecipe(Long recipeId, UpdateRecipe postRecipe) {
    log.debug("processUpdateRecipe(Long recipeId, UpdateRecipe postRecipe) : ", recipeId,
        postRecipe.toString());
    RecipeEntity recipeEntityFound = recipeRepository.findById(recipeId)
        .orElseThrow(() -> new RecipeException(HttpStatus.NOT_FOUND,
            ApplicationConstants.ERROR_MESG_RECIPE_DOES_NOT_EXIST,
            ApplicationConstants.ERROR_MESG_CODE_RECIPE_DOES_NOT_EXIST));

    ObjectMapper mapper = new ObjectMapper();
    RecipeEntity recipeEntity = mapper.convertValue(postRecipe, RecipeEntity.class);
    recipeEntity.setRecipeId(recipeEntityFound.getRecipeId());
    recipeRepository.save(recipeEntity);
  }


  /**
   * 
   * @param recipeId
   * @return
   */
  @Override
  public Recipe processGetRecipe(long recipeId) {
    log.debug("processGetRecipe(long recipeId) : " + recipeId);

    RecipeEntity recipeEntity = recipeRepository.findById(recipeId)
        .orElseThrow(() -> new RecipeException(HttpStatus.NOT_FOUND,
            ApplicationConstants.ERROR_MESG_RECIPE_DOES_NOT_EXIST,
            ApplicationConstants.ERROR_MESG_CODE_RECIPE_DOES_NOT_EXIST));

    return convertEntityToDto(recipeEntity);
  }



  /**
   * 
   * @param recipeEntity
   * @return
   */
  private Recipe convertEntityToDto(RecipeEntity recipeEntity) {
    log.debug("convertEntityToDto(RecipeEntity recipeEntity) : ", recipeEntity.toString());
    Recipe recipe = null;
    List<com.lacuisine.recipe.entity.Ingredients> ingredients = null;
    List<Ingredients> ingredientList = new ArrayList<>();
    recipe = new Recipe();
    recipeEntity.getRecipeId();
    recipe.setName(recipeEntity.getName());
    recipe.setRecipeId(recipeEntity.getRecipeId());
    recipe.setRecipeInstruction(recipeEntity.getRecipeInstruction());
    recipe.setServingCapacity(recipeEntity.getServingCapacity());
    recipe.setVegetarian(recipeEntity.getVegetarian());
    ingredients = recipeEntity.getIngredients();
    if (!ObjectUtils.isEmpty(ingredients)) {
      ingredients.forEach(ingredient -> {
        Ingredients ingredientModel = new Ingredients();
        ingredientModel.setIngredient(ingredient.getIngredient());
        ingredientModel.setId(ingredient.getId());
        ingredientList.add(ingredientModel);
      });
    }
    recipe.setIngredients(ingredientList);
    return recipe;
  }


  /**
   * 
   * @param recipeSearchCriteria
   * @return
   */
  @Override
  public List<Recipe> processSearchRecipe(RecipeSearchCriteria recipeSearchCriteria) {
    log.debug("processSearchRecipe(RecipeSearchCriteria recipeSearchCriteria) : ",
        recipeSearchCriteria.toString());
    List<RecipeEntity> recipeEntityLst =
        recipeRepository.findByRecipeSerachCriteria(recipeSearchCriteria);
    List<Recipe> recipeList = new ArrayList<>();

    for (RecipeEntity recipeEntity : recipeEntityLst) {
      Recipe recipe = convertEntityToDto(recipeEntity);
      recipeList.add(recipe);
    }

    return recipeList;
  }


  /**
   * 
   * @return
   */
  @Override
  public List<Recipe> processGetAllRecipeInformation() {
    log.debug("processGetAllRecipeInformation() : ");
    List<Recipe> recipeList = new ArrayList<>();
    try {
      List<RecipeEntity> recipeEntityLst = recipeRepository.findAll();
      for (RecipeEntity recipeEntity : recipeEntityLst) {
        Recipe recipe = convertEntityToDto(recipeEntity);
        recipeList.add(recipe);

      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }



    return recipeList;

  }

}
