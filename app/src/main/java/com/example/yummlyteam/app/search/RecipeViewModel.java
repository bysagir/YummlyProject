package com.example.yummlyteam.app.search;



import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.yummlyteam.app.model.RecipeSearchList;
import com.example.yummlyteam.app.repository.RecipeRepository;

public class RecipeViewModel extends ViewModel {

    private static final Integer ITEM_PER_PAGE = 18;

    private MutableLiveData<RecipeSearchList> recipeResponse;
    private RecipeRepository recipeRepository;
    private MutableLiveData<Integer> currentSearchPage = new MutableLiveData<>();
    private MutableLiveData<String> query = new MutableLiveData<>();

    public MutableLiveData<String> getSearchQuery() {
        return query;
    }

    public void setSearchQuery(String query) {
        this.query.setValue(query);
    }

    private MutableLiveData<Integer> getCurrentSearchPage() {
        return currentSearchPage;
    }

    private void setCurrentSearchPage(int page) {
        currentSearchPage.setValue(page);
    }

    public RecipeViewModel() {

        if (recipeRepository != null) {
            return;
        }
        recipeRepository = recipeRepository.getInstance();
    }

    public MutableLiveData<RecipeSearchList> fetchRecipeSearchList() {
        recipeResponse = recipeRepository.fetchRecipeSearchList(query, getCurrentSearchPage().getValue() == null ? 0 : getCurrentSearchPage().getValue());
        return recipeResponse;
    }

    public void nextSearchPage() {
        int newPageNumber = currentSearchPage.getValue() == null ? 0 : currentSearchPage.getValue() + ITEM_PER_PAGE;
        setCurrentSearchPage(newPageNumber);
    }

    private void preSearchPage() {
        if (currentSearchPage.getValue() == null) return;

        int newPageNumber = currentSearchPage.getValue() == 0 ? 0 : currentSearchPage.getValue() - ITEM_PER_PAGE;
        setCurrentSearchPage(newPageNumber);
    }
}

