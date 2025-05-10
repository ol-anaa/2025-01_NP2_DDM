package com.anaoliveiravictoriamartins.animaladoption;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anaoliveiravictoriamartins.animaladoption.Domain.Entity.Animal;

import java.util.List;
import java.util.function.Consumer;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private List<Animal> animalList;
    private Consumer<Animal> onItemClick;

    public AnimalAdapter(List<Animal> animalList, Consumer<Animal> onItemClick) {
        this.animalList = animalList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.animal_item, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animalList.get(position);

        holder.animalName.setText(animal.Name);
        holder.animalAge.setText(animal.Age > 1 ? animal.Age + " anos" : animal.Age + " ano");
        holder.animalRace.setText(animal.Race);

        Bitmap img = BitmapFactory.decodeFile(animal.UrlImage);
        holder.animalImg.setImageBitmap(img);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClick != null) {
                onItemClick.accept(animal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    static class AnimalViewHolder extends RecyclerView.ViewHolder {
        ImageView animalImg;
        TextView animalName;
        TextView animalRace;
        TextView animalAge;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            animalImg = itemView.findViewById(R.id.imgAnimal);
            animalName = itemView.findViewById(R.id.animalName);
            animalRace = itemView.findViewById(R.id.animalRace);
            animalAge = itemView.findViewById(R.id.animalAge);
        }
    }
}

