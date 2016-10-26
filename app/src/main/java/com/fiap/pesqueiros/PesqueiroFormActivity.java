package com.fiap.pesqueiros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.fiap.pesqueiros.bean.Pesqueiro;
import com.fiap.pesqueiros.dao.PesqueiroDAO;
import com.fiap.pesqueiros.util.PersistentHelper;

public class PesqueiroFormActivity extends AppCompatActivity {
    private EditText edtId ;
    private PersistentHelper helper;

    //Instancia o helper
    //Recebe um pesqueiro serializado do listPesqueiros para alteração
    //Quando vem do btnNovopesqueiro não recebe parâmetros, portanto, cria um novo registro
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesqueiro_form);

        helper = new PersistentHelper(this);
        edtId = (EditText) findViewById(R.id.edtId);

        Intent intent = getIntent();
        //Recebe o Pesqueiro da PesqueiroListActivity
        Pesqueiro pesqueiro = (Pesqueiro) intent.getSerializableExtra("pesqueiro");
        //Se ele for diferente de nulo, popula os Edits para alteração
        if(pesqueiro != null) {
            helper.preencheEdts(pesqueiro);
        }
    }

    //Cria o Menu para o botão gravar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Insere ou atualiza um pesqueiro na base utilizando um MenuItem
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                PesqueiroDAO pesqueiroDAO = new PesqueiroDAO(this);
                Pesqueiro pesqueiro = new Pesqueiro();

                //Valida se os campos estão preenchidos
                if(helper.validaCampos(this)){
                    //Pega o ID do Edit edtId
                    String strId = edtId.getText().toString().trim();
                    pesqueiro = helper.pegaPesqueiro();
                    //Se o ID for vazio
                    if(TextUtils.isEmpty(strId)) {
                        pesqueiroDAO.inserir(pesqueiro);
                    }else{
                        pesqueiroDAO.atualizar(pesqueiro);
                        pesqueiroDAO.close();
                    }
                    Toast.makeText(PesqueiroFormActivity.this, "Pesqueiro " + pesqueiro.getNome() + " salvo!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
