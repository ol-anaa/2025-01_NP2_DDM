package com.anaoliveiravictoriamartins.animaladoption.Domain.Entity;

public class Animal {
    public int Id;
    public String Name;
    public String Race;
    public double Weight;
    public int Age;
    public String Personality;
    public String UrlImage;
    public String Type;

    public Animal(String name, String race, double weight, int age, String personality, String urlImage, String type){
        this.Name = name;
        this.Race = race;
        this.Weight = weight;
        this.Age = age;
        this.Personality = personality;
        this.UrlImage = urlImage;
        this.Type = type;
    }
}
