package com.anaoliveiravictoriamartins.animaladoption.DatabaseManager;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.anaoliveiravictoriamartins.animaladoption.Domain.Entity.Animal;
import com.anaoliveiravictoriamartins.animaladoption.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FundacaoPrinDatabase extends SQLiteOpenHelper {

    private Context context;
    public FundacaoPrinDatabase(Context context){
        super(context, "FundacaoPrin", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS Animals (\n" +
                    "    [Id]          INTEGER      PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "    [Name]        VARCHAR(100) NOT NULL,\n" +
                    "    [Race]        VARCHAR(50)  NOT NULL,\n" +
                    "    [Weight]      REAL         NOT NULL,\n" +
                    "    [Age]         INTEGER      NOT NULL,\n" +
                    "    [Personality] VARCHAR(100) NOT NULL,\n" +
                    "    [UrlImage]    VARCHAR(250) NOT NULL,\n" +
                    "    [Type]        VARCHAR(50)  NOT NULL\n" +
                    ")"
        );

        if (isTableEmpty(db)) {
            insertDefaultAnimals(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private boolean isTableEmpty(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Animals", null);

        cursor.moveToFirst();

        int count = cursor.getInt(0);

        cursor.close();

        return count == 0;
    }

    private void insertDefaultAnimals(SQLiteDatabase db) {

        Bitmap prinImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.princesa);
        Bitmap samyImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.samy);
        Bitmap lilicaImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.lilica);

        String pathPrin = saveBitmapToInternalStorage(prinImg, "princesa.png");
        String pathSamy = saveBitmapToInternalStorage(samyImg, "samy.png");
        String pathLilica = saveBitmapToInternalStorage(lilicaImg, "lilica.png");

        List<Animal> defaultAnimals = Arrays.asList(
                new Animal("Princesa", "Sem raça definida", 3, 10, "A princesa, carinhosamente conhecida como prin, é muito docil, carente, dorminhoca e sapeca", pathPrin, "Gato"),
                new Animal("Samy Salame", "Sem raça definida", 8, 5, "Boba, carinhosa, ciúmenta, dócil", pathSamy, "Cachorro"),
                new Animal("Lilica Repilica", "Sem raça definida", 9, 5, "Boba, carente, dengosa, brincalhona, desajeitada", pathLilica, "Cachorro")
        );

        for (Animal animal : defaultAnimals) {

            ContentValues values = new ContentValues();
            values.put("Name", animal.Name);
            values.put("Race", animal.Race);
            values.put("Weight", animal.Weight);
            values.put("Age", animal.Age);
            values.put("Personality", animal.Personality);
            values.put("UrlImage", animal.UrlImage);
            values.put("Type", animal.Type);

            db.insert("Animals", null, values);
        }
    }


    public String saveBitmapToInternalStorage(Bitmap bitmap, String filename) {

        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return new File(context.getFilesDir(), filename).getAbsolutePath();
    }
}
