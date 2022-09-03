package com.lacuisine.recipe.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@Table(name = "recipe")
public class RecipeEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recipeId")
  private Long recipeId;

  @Column(name = "name")
  private String name;

  @Column(name = "vegetarian")
  private Boolean vegetarian;

  @Column(name = "servingCapacity")
  private int servingCapacity;

  // @OneToMany(mappedBy = "recipeEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @OneToMany(mappedBy = "recipeEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Ingredients> ingredients;

  @Column(name = "recipeInstruction")
  private String recipeInstruction;

  public void setIngredients(List<Ingredients> ingredients) {
    ingredients.forEach(ingredient -> ingredient.setRecipeEntity(this));
    this.ingredients = ingredients;
  }

}
