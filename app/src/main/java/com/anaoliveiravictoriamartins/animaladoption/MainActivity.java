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

        List<Animal> animals = repository.getAll();
        animalAdapter = new AnimalAdapter(animals);

        recyclerViewAnimals.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnimals.setAdapter(animalAdapter);
    }

    private void createConnection(){
        try {
            this.deleteDatabase("FundacaoPrin");

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

    //Cometário aula vic
}