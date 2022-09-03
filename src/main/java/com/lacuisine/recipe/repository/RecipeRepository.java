package com.lacuisine.recipe.repository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.lacuisine.recipe.entity.RecipeEntity;
import com.lacuisine.recipe.model.RecipeSearchCriteria;

@Repository
public interface RecipeRepository
    extends JpaRepository<RecipeEntity, Long>, JpaSpecificationExecutor<RecipeEntity> {

  default List<RecipeEntity> findByRecipeSerachCriteria(RecipeSearchCriteria recipeSearchCriteria) {
    return findAll(new Specification<RecipeEntity>() {
      private static final long serialVersionUID = 1L;


      @Override
      public Predicate toPredicate(Root<RecipeEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(recipeSearchCriteria)) {

          if (!ObjectUtils.isEmpty(recipeSearchCriteria.getVegetarian())) {
            predicates.add(criteriaBuilder.equal(root.get("vegetarian"),
                recipeSearchCriteria.getVegetarian()));
          }

          if (!ObjectUtils.isEmpty(recipeSearchCriteria.getServingCapacity())) {
            predicates.add(criteriaBuilder.equal(root.get("servingCapacity"),
                recipeSearchCriteria.getServingCapacity()));
          }
          if (!ObjectUtils.isEmpty(recipeSearchCriteria.getRecipeInstruction())) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("recipeInstruction"),
                "%" + recipeSearchCriteria.getRecipeInstruction() + "%")));
          }

          if (!ObjectUtils.isEmpty(recipeSearchCriteria.getIngredientIncluded())) {

            predicates.add(criteriaBuilder
                .or(criteriaBuilder.equal(root.joinList("ingredients").get("ingredient"),
                    recipeSearchCriteria.getIngredientIncluded())));
          }

        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    });
  }



}
