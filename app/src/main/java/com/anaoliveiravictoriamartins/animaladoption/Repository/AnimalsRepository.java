package com.anaoliveiravictoriamartins.animaladoption.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.anaoliveiravictoriamartins.animaladoption.DatabaseManager.FundacaoPrinDatabase;
import com.anaoliveiravictoriamartins.animaladoption.Domain.Entity.Animal;

import java.util.ArrayList;
import java.util.List;

public class AnimalsRepository {
    private static AnimalsRepository instance;
    private final SQLiteDatabase connection;

    private AnimalsRepository(SQLiteDatabase connection) {
        this.connection = connection;
    }

    public static synchronized AnimalsRepository getInstance(Context context) {

        if (instance == null) {
            FundacaoPrinDatabase fundacaoPrinDataBase = new FundacaoPrinDatabase(context);
            SQLiteDatabase connection = fundacaoPrinDataBase.getWritableDatabase();

            instance = new AnimalsRepository(connection);
        }

        return instance;
    }

    public void insert(Animal animal){
        ContentValues values = new ContentValues();

        values.put("Name", animal.Name);
        values.put("Race", animal.Race);
        values.put("Weight", animal.Weight);
        values.put("Age", animal.Age);
        values.put("Personality", animal.Personality);
        values.put("UrlImage", animal.UrlImage);
        values.put("Type", animal.Type);
        values.put("IsAdopted", animal.IsAdopted);

        connection.insertOrThrow("Animals", null, values);
    }
    public void delete(int identity){
        connection.delete("Animals","Id = ?", new String[]{String.valueOf(identity)});
    }

    public void update(Animal animal){
        ContentValues values = new ContentValues();

        values.put("Name", animal.Name);
        values.put("Race", animal.Race);
        values.put("Weight", animal.Weight);
        values.put("Age", animal.Age);
        values.put("Personality", animal.Personality);
        values.put("UrlImage", animal.UrlImage);
        values.put("Type", animal.Type);
        values.put("IsAdopted", animal.IsAdopted);

        connection.update("Animals", values, "Id = ?", new String[]{String.valueOf(animal.Id)});
    }

    public Animal getById(int identity){

        Cursor result = connection.rawQuery("SELECT * FROM Animals WHERE Id = ?", new String[]{String.valueOf(identity)});
        Animal animal = null;

        if(result.getCount() > 0){
            result.moveToFirst();

            animal = new Animal(
                result.getString(result.getColumnIndexOrThrow("Name")),
                result.getString(result.getColumnIndexOrThrow("Race")),
                result.getDouble(result.getColumnIndexOrThrow("Weight")),
                result.getInt(result.getColumnIndexOrThrow("Age")),
                result.getString(result.getColumnIndexOrThrow("Personality")),
                result.getString(result.getColumnIndexOrThrow("UrlImage")),
                result.getString(result.getColumnIndexOrThrow("Type")),
       result.getInt(result.getColumnIndexOrThrow("IsAdopted")) == 1
            );
            animal.Id = result.getInt(result.getColumnIndexOrThrow("Id"));
        }

        result.close();
        return animal;
    }

    public List<Animal> getAll(String filter) {
        Cursor result;
        List<Animal> animals = new ArrayList<Animal>();

        if(!filter.equals("Todos"))
            result = connection.rawQuery("SELECT * FROM Animals WHERE Type = ? AND IsAdopted = ?", new String[]{String.valueOf(filter), String.valueOf(0)});
        else
            result = connection.rawQuery("SELECT * FROM Animals WHERE IsAdopted = ?", new String[]{String.valueOf(0)});

        if(result.getCount() > 0){

            while (result.moveToNext()) {

                Animal animal = new Animal(
                    result.getString(result.getColumnIndexOrThrow("Name")),
                    result.getString(result.getColumnIndexOrThrow("Race")),
                    result.getDouble(result.getColumnIndexOrThrow("Weight")),
                    result.getInt(result.getColumnIndexOrThrow("Age")),
                    result.getString(result.getColumnIndexOrThrow("Personality")),
                    result.getString(result.getColumnIndexOrThrow("UrlImage")),
                    result.getString(result.getColumnIndexOrThrow("Type")),
           result.getInt(result.getColumnIndexOrThrow("IsAdopted")) == 1
                );
                animal.Id = result.getInt(result.getColumnIndexOrThrow("Id"));

                animals.add(animal);
            }
        }

        result.close();
        return animals;
    }
}
