package com.anaoliveiravictoriamartins.animaladoption;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

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
        loadDataSpinner();
        loadAnimals("Todos");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnimalForm.class);
                startActivity(intent);
            }
        });

        filter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                if(!selectedItem.equals("Todos"))
                    selectedItem = selectedItem.substring(0, selectedItem.length() - 1).toLowerCase();

                loadAnimals(selectedItem);
            }
        });
    }

    private void createConnection() {

        try {
            this.deleteDatabase("FundacaoPrin");
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

        TextView emptyView = findViewById(R.id.empty_view);
        RecyclerView recyclerViewAnimals = findViewById(R.id.recyclerViewAnimals);

        if (animals.isEmpty()) {
            recyclerViewAnimals.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyView.setText("\uD83D\uDD0D Nenhum animal encontrado.");
        }
        else {
            recyclerViewAnimals.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

            AnimalAdapter animalAdapter = new AnimalAdapter(animals, animal -> {
                Intent intent = new Intent(MainActivity.this, AnimalDetails.class);
                intent.putExtra("animal_id", animal.Id);
                startActivity(intent);
            });

            recyclerViewAnimals.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewAnimals.setAdapter(animalAdapter);
        }
    }
    private void loadDataSpinner(){
        String[] tiposDeAnimais = { "Todos", "Cachorros", "Gatos", "Coelhos", "Pássaros", "Répteis" };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            tiposDeAnimais
        );

        filter.setAdapter(adapter);
        filter.setThreshold(1);
        filter.setText(tiposDeAnimais[0], false);
    }
    private void initialCustomization(){
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        getWindow().setStatusBarColor(Color.parseColor("#17706e"));
    }
}