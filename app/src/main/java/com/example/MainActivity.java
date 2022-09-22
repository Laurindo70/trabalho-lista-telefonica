package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.banco.ContatoDB;
import com.example.banco.DBHelper;
import com.example.models.Contato;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText nome;
    private EditText numero;
    private EditText data_nascimento;
    List<Contato> dados;
    ListView listagem;
    DBHelper db;
    ContatoDB contatoDB;
    private Contato contato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);

        nome = findViewById(R.id.nome);
        numero = findViewById(R.id.telefone);
        data_nascimento = findViewById(R.id.data_nascimento);
        listagem = findViewById(R.id.listaId);

        dados= new ArrayList();

        ArrayAdapter adapter =
                new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dados);
        listagem.setAdapter(adapter);
        contatoDB = new ContatoDB(db);
        contatoDB.lista(dados);
        acoes();

    }

    public void acoes(){

        listagem.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView,
                                                   View view, int i, long l) {
                        new AlertDialog.Builder(view.getContext())
                                .setMessage("Deseja realmente remover")
                                .setPositiveButton("Confirmar",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface,
                                                                int k) {
                                                contatoDB.remover(dados.get(i).getId());
                                                contatoDB.lista(dados);
                                            }
                                        })
                                .setNegativeButton("cancelar",null)
                                .create().show();
                        return false;
                    }
                });

        listagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                contato=dados.get(i);
                nome.setText(contato.getNome());
                numero.setText(contato.getNumero());
                data_nascimento.setText(contato.getData_nascimento());
                findViewById(R.id.idDesistir).setVisibility(View.VISIBLE);
            }
        });

    }

    public void voltar(View view){
        limpar();
    }

    private void limpar(){
        nome.setText("");
        numero.setText("");
        data_nascimento.setText("");
        findViewById(R.id.idDesistir).setVisibility(View.INVISIBLE);

    }

    public void salvar(View view){
        if(contato == null){
            contato = new Contato();
        }

        contato.setData_nascimento(data_nascimento.getText().toString());
        contato.setNome(nome.getText().toString());
        contato.setNumero(numero.getText().toString());

        contatoDB.inserir(contato);
        contatoDB.lista(dados);

        ((ArrayAdapter) listagem.getAdapter()
        ).notifyDataSetChanged();
        limpar();

        contato = null;

        Toast.makeText(this,"Salvo com sucesso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        limpar();
    }
}