package com.anaoliveiravictoriamartins.animaladoption;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anaoliveiravictoriamartins.animaladoption.Domain.Entity.Animal;
import com.anaoliveiravictoriamartins.animaladoption.Repository.AnimalsRepository;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AnimalForm extends AppCompatActivity {
    private TextView name;
    private TextView race;
    private TextView age;
    private TextView weight;
    private TextInputLayout type;
    private TextView personality;
    private ImageView image;
    private Uri imageUri;
    ActivityResultLauncher<Intent> imagePickerLauncher;
    private Button uploadImg;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_form);

        /*
        name = findViewById(R.id.);
        race = findViewById(R.id.);
        age = findViewById(R.id.);
        weight = findViewById(R.id.);
        type = findViewById(R.id.);
        personality = findViewById(R.id.);
        image = findViewById(R.id.);
        uploadImg = findViewById(R.id.);
        save = findViewById(R.id.);
        */

        initialCustomization();
        /*
        setupImagePicker();

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                imagePickerLauncher.launch(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //manda mensagem de erro
                if (imageUri == null)
                    return;

                String imagePath = saveImageToInternalStorage();

                if(imagePath.isBlank() || imagePath.isEmpty())
                    return;

                Animal animal = new Animal(
                     name.toString(),
                     race.toString(),
                     Double.parseDouble(weight.toString()),
                     Integer.parseInt(age.toString()),
                     personality.toString(),
                     imagePath,
                     type.toString().toLowerCase(),
                     false
                );

                AnimalsRepository repository = AnimalsRepository.getInstance(AnimalForm.this);
                repository.insert(animal);

                Intent intent = new Intent(AnimalForm.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

         */
    }

    private void setupImagePicker() {
        imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    image.setImageURI(imageUri);
                }
            }
        );
    }
    private String saveImageToInternalStorage(){

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(AnimalForm.this.getContentResolver(), imageUri);

            String fileName = "IMG_" + System.currentTimeMillis() + ".jpg";

            File directory = new File(getFilesDir(), "animal_images");

            if (!directory.exists())
                directory.mkdirs();

            File file = new File(directory, fileName);
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            return file.getAbsolutePath();

        } catch (IOException e) {
            Toast.makeText(this, "⚠\uFE0F Erro ao tentar processar sua imagem, tente novamente mais tarde. (" + e.toString() + ")", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    private void initialCustomization(){
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        getWindow().setStatusBarColor(Color.parseColor("#17706e"));
    }
}
