package com.example.banco;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import com.example.models.Contato;

public class ContatoDB {

    private DBHelper db;
    private SQLiteDatabase conexao;
    public ContatoDB(DBHelper db){
        this.db=db;
    }

    public void inserir(Contato contato){
        conexao = db.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", contato.getNome());
        valores.put("telefone", contato.getNumero());
        valores.put("data_nascimento", contato.getData_nascimento());


        if(contato.getId() > 0){
            conexao.update("lista_telefonica", valores, "id=?", new String[]{" " + contato.getId() + ""});
        } else {
            conexao.insert("lista_telefonica", null, valores);
        }

        conexao.close();
    }

    public void remover(int id){
        conexao = db.getWritableDatabase();
        conexao.delete("lista_telefonica", "id=?", new String[]{id+""});

        conexao.close();
    }

    public void lista(List contatos){
        contatos.clear();
        conexao = db.getWritableDatabase();
        String names[] = {"id", "nome", "telefone", "data_nascimento"};
        Cursor query = conexao.query("lista_telefonica", names,
                null, null, null,
                null, "nome");
        while (query.moveToNext()){
            Contato contatoConsulta = new Contato(
                    Integer.parseInt(query.getString(0)),
                    query.getString(1),
                    query.getString(2),
                    query.getString(3)
            );
            contatos.add(contatoConsulta);
        }

        conexao.close();
    }

}
