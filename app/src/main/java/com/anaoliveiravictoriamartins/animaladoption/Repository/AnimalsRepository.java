package com.anaoliveiravictoriamartins.animaladoption.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.anaoliveiravictoriamartins.animaladoption.Domain.Entity.Animal;

import java.util.ArrayList;
import java.util.List;

public class AnimalsRepository {
    private SQLiteDatabase connection;

    public AnimalsRepository(SQLiteDatabase connection){
        this.connection = connection;
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

        connection.update("Animals", values, "Id = ?", new String[]{String.valueOf(animal.Id)});
    }

    public Animal get(int identity){

        Cursor result = connection.rawQuery("SELECT * FROM Animals WHERE Id = ?", new String[]{String.valueOf(identity)});
        Animal animal = null;

        if(result.getCount() > 0){

            animal.Id = result.getInt(result.getColumnIndexOrThrow("Id"));

            animal = new Animal(
                result.getString(result.getColumnIndexOrThrow("Name")),
                result.getString(result.getColumnIndexOrThrow("Race")),
                result.getDouble(result.getColumnIndexOrThrow("Weight")),
                result.getInt(result.getColumnIndexOrThrow("Age")),
                result.getString(result.getColumnIndexOrThrow("Personality")),
                result.getString(result.getColumnIndexOrThrow("UrlImage")),
                result.getString(result.getColumnIndexOrThrow("Type"))
            );
        }

        return animal;
    }

    public List<Animal> getAll(){
        List<Animal> animals = new ArrayList<Animal>();

        Cursor result = connection.rawQuery("SELECT * FROM Animals", null);

        if(result.getCount() > 0){

            while (result.moveToNext()) {

                Animal animal = new Animal(
                    result.getString(result.getColumnIndexOrThrow("Name")),
                    result.getString(result.getColumnIndexOrThrow("Race")),
                    result.getDouble(result.getColumnIndexOrThrow("Weight")),
                    result.getInt(result.getColumnIndexOrThrow("Age")),
                    result.getString(result.getColumnIndexOrThrow("Personality")),
                    result.getString(result.getColumnIndexOrThrow("UrlImage")),
                    result.getString(result.getColumnIndexOrThrow("Type"))
                );
                animal.Id = result.getInt(result.getColumnIndexOrThrow("Id"));

                animals.add(animal);
            }
        }

        return animals;
    }

}
