package com.example.minesweeper;

import java.util.Comparator;

public class Scores {
    //lasse Scores qui sert au stockage des scores
    private String nom;
    private Integer temps;

    public Scores(String nom,Integer temps) {
        this.nom = nom;
        this.temps = temps;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getTemps() {
        return temps;
    }

    public void setTemps(Integer temps) {
        this.temps = temps;
    }

    @Override
    public String toString() {
        return "Scores{" +
                "nom='" + nom + '\'' +
                ", temps=" + temps +
                '}';
    }
}
class ScoresTempsComparator implements Comparator<Scores>
{
    public int compare(Scores left, Scores right) {
        return left.getTemps().compareTo(right.getTemps());
    }
}
