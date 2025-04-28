package com.anaoliveiravictoriamartins.animaladoption.DatabaseManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FundacaoPrinDatabase extends SQLiteOpenHelper {

    public FundacaoPrinDatabase(Context context){
        super(context, "FundacaoPrin", null, 1);
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
