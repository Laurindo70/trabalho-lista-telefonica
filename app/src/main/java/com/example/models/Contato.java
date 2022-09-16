package com.example.models;

import org.jetbrains.annotations.Contract;

public class Contato {

    private String nome;
    private String numero;
    private String data_nascimento;

    public Contato(String nome, String numero, String data_nascimento){
        this.nome = nome;
        this.numero = numero;
        this.data_nascimento = data_nascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }
}
