package com.anaoliveiravictoriamartins.animaladoption;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anaoliveiravictoriamartins.animaladoption.DatabaseManager.FundacaoPrinDatabase;
import com.anaoliveiravictoriamartins.animaladoption.Domain.Entity.Animal;
import com.anaoliveiravictoriamartins.animaladoption.Repository.AnimalsRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AnimalsRepository repository;
    private FloatingActionButton fab;
    private AutoCompleteTextView filter;
    private TextInputLayout dropDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.addAnimal);
        filter = findViewById(R.id.filter);
        dropDown = findViewById(R.id.dropDown);
        dropDown.setBoxStrokeColor(ContextCompat.getColor(this, R.color.white));

        initialCustomization();
        createConnection();
        loadAnimals("Todos");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnimalForm.class);
                startActivity(intent);
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAnimals(v.toString());
            }
        });
    }

    private void createConnection() {

        try {
            repository = AnimalsRepository.getInstance(this);
        }
        catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Error");
            dlg.setMessage("Tente novamente mais tarde, se o erro persistir entre em contato. (" + ex.getMessage() + ")");
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }
    }

    private void loadAnimals(String filter) {
        List<Animal> animals = repository.getAll(filter);

        AnimalAdapter animalAdapter = new AnimalAdapter(animals, animal -> {
            Intent intent = new Intent(MainActivity.this, AnimalDetails.class);
            intent.putExtra("animal_id", animal.Id);
            startActivity(intent);
        });

        RecyclerView recyclerViewAnimals  = findViewById(R.id.recyclerViewAnimals);

        recyclerViewAnimals.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnimals.setAdapter(animalAdapter);
    }

    private void initialCustomization(){
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        getWindow().setStatusBarColor(Color.parseColor("#17706e"));
    }
}