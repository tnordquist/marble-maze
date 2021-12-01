package edu.cnm.deepdive.marblemaze.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import edu.cnm.deepdive.marblemaze.NavGraphDirections;
import edu.cnm.deepdive.marblemaze.R;
import edu.cnm.deepdive.marblemaze.databinding.ActivityMainBinding;
import edu.cnm.deepdive.marblemaze.viewmodel.LoginViewModel;

public class MainActivity extends AppCompatActivity {

  private AppBarConfiguration appBarConfiguration;
  private ActivityMainBinding binding;
  private NavController navController;
  private LoginViewModel loginViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    setSupportActionBar(binding.toolbar);

    navController = Navigation.findNavController(this,
        R.id.nav_host_fragment_content_main);
    appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    loginViewModel.getAccount().observe(this, (account) -> {
      if (account != null) {
        // TODO Use account information for personalization
      } else {
        Intent intent = new Intent(this, LoginActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
      }
    });
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(navController, appBarConfiguration)
        || super.onSupportNavigateUp();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();
    boolean handled = true;
    if (id == R.id.settings) {
      navController.navigate(NavGraphDirections.openSettings());
    } else if (id == R.id.sign_out) {
      loginViewModel.signOut();
    } else {
      handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }
}