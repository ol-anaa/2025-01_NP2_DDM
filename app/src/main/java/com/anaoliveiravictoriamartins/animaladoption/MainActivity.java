package com.anaoliveiravictoriamartins.animaladoption;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anaoliveiravictoriamartins.animaladoption.DatabaseManager.FundacaoPrinDatabase;
import com.anaoliveiravictoriamartins.animaladoption.Domain.Entity.Animal;
import com.anaoliveiravictoriamartins.animaladoption.Repository.AnimalsRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewAnimals;
    private AnimalAdapter animalAdapter;
    private LinearLayout linearLayout;
    private SQLiteDatabase connection;
    private FundacaoPrinDatabase fundacaoPrinDataBase;
    private AnimalsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.headerLayout);
        recyclerViewAnimals = findViewById(R.id.recyclerViewAnimals);

        initialCustomization();
        createConnection();
        //initialData();

        List<Animal> animals = repository.getAll();
        animalAdapter = new AnimalAdapter(animals);

        recyclerViewAnimals.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnimals.setAdapter(animalAdapter);
    }

    private void createConnection(){
        try {
            fundacaoPrinDataBase = new FundacaoPrinDatabase(this);
            connection = fundacaoPrinDataBase.getWritableDatabase();
            repository = new AnimalsRepository(connection);
        }
        catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Error");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }
    }

    private void initialCustomization(){
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        getWindow().setStatusBarColor(Color.parseColor("#17706e"));
    }

    private void initialData(){
        Animal animal = new Animal("Princesa", "Sem raça definida", 3,  10, "A princesa, carinhosamente conhecida como prin, é muito docil, carente, dorminhoca e sapeca", "img", "Gato");
        Animal animal1 = new Animal("Samy Salame", "Sem raça definida", 8, 5, "Boba, carinhosa, ciúmenta, dócil", "img", "Cachorro");
        Animal animal2 = new Animal("Lilica Repilica", "Sem raça definida", 9, 5, "Boba, carente, dengosa, brincalhona, desajeitada", "img", "Cachorro");

        repository.insert(animal);
        repository.insert(animal1);
        repository.insert(animal2);
    }
}