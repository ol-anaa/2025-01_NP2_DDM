package com.anaoliveiravictoriamartins.animaladoption;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anaoliveiravictoriamartins.animaladoption.Domain.Entity.Animal;
import com.bumptech.glide.Glide;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private List<Animal> animalList;

    public AnimalAdapter(List<Animal> animalList) {
        this.animalList = animalList;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_animal, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animalList.get(position);

        holder.animalName.setText(animal.Name);
        holder.animalAge.setText(String.valueOf(animal.Age));
        holder.animalRace.setText(animal.Race);

        Glide.with(holder.itemView.getContext())
                .load(animal.UrlImage)
                .into(holder.animalImg);
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

