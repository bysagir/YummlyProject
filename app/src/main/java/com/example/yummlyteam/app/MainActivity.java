package com.example.yummlyteam.app;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummlyteam.app.adapter.RecipeListAdapter;
import com.example.yummlyteam.app.model.Match;
import com.example.yummlyteam.app.model.RecipeSearchList;
import com.example.yummlyteam.app.search.RecipeViewModel;
import com.example.yummlyteam.yummly_project.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  RecyclerView recyclerView;
  RecipeListAdapter recipeListAdapter;
  RecipeViewModel recipeVievModel;
  SearchView searchView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    recipeVievModel = new ViewModelProvider(MainActivity.this).get(RecipeViewModel.class);

    searchView = findViewById(R.id.recipeSearchBar);
    searchView.setQueryHint("Search Recipe");
    searchView.setIconified(false);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        recipeVievModel.setSearchQuery(query);
        recipeVievModel.fetchRecipeSearchList();
        return true;
      }

      @Override
      public boolean onQueryTextChange(String s) {
        recipeListAdapter.clearList();
        return true;
      }
    });

    recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recipeListAdapter = new RecipeListAdapter(new ArrayList<Match>());
    recyclerView.setAdapter(recipeListAdapter);


    recipeVievModel.fetchRecipeSearchList().observe(this, new Observer<RecipeSearchList>() {
      @Override
      public void onChanged(RecipeSearchList searchList) {
        // Update the UI
        if (searchList == null || searchList.getMatches() == null) { // clear the list
          recipeListAdapter.clearList();
        } else {
          recipeListAdapter.addItems(searchList.getMatches());
        }
      }
    });

    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (!recyclerView.canScrollVertically(1)) {
          recipeVievModel.nextSearchPage();
          recipeVievModel.fetchRecipeSearchList();
        }
      }
    });
  }
}
