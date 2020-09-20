package com.example.yummlyteam.app.repository;


import androidx.lifecycle.MutableLiveData;

import com.example.yummlyteam.app.api.ApiClient;
import com.example.yummlyteam.app.api.ApiInterface;
import com.example.yummlyteam.app.model.RecipeSearchList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {

    private static RecipeRepository recipeRepository;
    private final ApiInterface apiService;

    private static final String ACCESS_KEY = " "; //removed the API KEY
    private static final String APP_ID = "62407325";
    private static final Integer ITEM_PER_PAGE = 18;

    private MutableLiveData<RecipeSearchList> searchList = new MutableLiveData<>();


    public static RecipeRepository getInstance() {
        if (recipeRepository == null) {
            recipeRepository = new RecipeRepository();
        }
        return recipeRepository;
    }

    public RecipeRepository() {

        apiService = ApiClient.getClient().create(ApiInterface.class);

    }

    public MutableLiveData<RecipeSearchList> fetchRecipeSearchList(MutableLiveData<String> query, int currentSearchPage) {
        if (query.getValue() == null || query.getValue().isEmpty()) {
            return searchList;
        }

        Call<RecipeSearchList> call = apiService.getRecipeList(APP_ID, ACCESS_KEY, query.getValue(), ITEM_PER_PAGE
                , currentSearchPage);

        call.enqueue(new Callback<RecipeSearchList>() {
            @Override
            public void onResponse(Call<RecipeSearchList> call, Response<RecipeSearchList> response) {
                int statusCode = response.code();
                if (response.isSuccessful()) {
                    searchList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchList> call, Throwable t) {
            }
        });
        return searchList;
    }

}