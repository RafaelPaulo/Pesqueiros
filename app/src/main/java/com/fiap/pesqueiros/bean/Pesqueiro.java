package com.fiap.pesqueiros.bean;

import java.io.Serializable;

/**
 * Created by rm75094 on 20/10/2016.
 */
public class Pesqueiro implements Serializable{

    private int id;
    private int quantidadeTanques;
    private String nome, webSite, telefone, endereco;
    private float rating;
    private boolean isPesqueEpaque, isPescaEsportiva;

    public Pesqueiro() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isPesqueEpaque() {
        return isPesqueEpaque;
    }

    public void setPesqueEpaque(boolean pesqueEpaque) {
        isPesqueEpaque = pesqueEpaque;
    }

    public int getQuantidadeTanques() {
        return quantidadeTanques;
    }

    public void setQuantidadeTanques(int quantidadeTanques) {
        this.quantidadeTanques = quantidadeTanques;
    }

    @Override
    public String toString() {
        StringBuilder rating = new StringBuilder();
        for (int i=1; i<= this.getRating(); i++){
            rating.append("*");
        }
        return this.getNome() + " - Nota: " + rating.toString();
    }

    public boolean isPescaEsportiva() {
        return isPescaEsportiva;
    }

    public void setPescaEsportiva(boolean pescaEsportiva) {
        isPescaEsportiva = pescaEsportiva;
    }
}
