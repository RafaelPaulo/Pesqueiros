package com.fiap.pesqueiros;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fiap.pesqueiros.bean.Pesqueiro;
import com.fiap.pesqueiros.dao.PesqueiroDAO;

import java.util.ArrayList;
import java.util.List;

public class PesqueirosListActivity extends AppCompatActivity {

    private ListView listPesqueiros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesqueiros_list);

        listPesqueiros = (ListView) findViewById(R.id.listPesqueiros);
        //registerForContextMenu para exibir o menu no ListView listPesqueiros
        registerForContextMenu(listPesqueiros);
        //setOnItemClickListener para chamar o FormularioActivity passando o pesqueiro selecionado
        listPesqueiros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pesqueiro pesqueiro = (Pesqueiro) listPesqueiros.getItemAtPosition(position);

                Intent intentChamaFormulario = new Intent(PesqueirosListActivity.this, PesqueiroFormActivity.class);
                intentChamaFormulario.putExtra("pesqueiro", pesqueiro);
                startActivity(intentChamaFormulario);
            }
        });
    }


    //Menu de Opções
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        //Utiliza o AdapterView para pegar o objeto sobre o qual o menu foi acionado
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        //Pega através da posição do objeto, o pesqueiro correspondente no listPesqueiros
        final Pesqueiro pesqueiro = (Pesqueiro) listPesqueiros.getItemAtPosition(info.position);
        //Adiciona um item no menu
        MenuItem site = menu.add("Abrir Site do " + pesqueiro.getNome());
        //Cria um Intente para chamada de um app
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        //Busca a URL do pesqueiro e adiciona http://, se necessário
        String url = pesqueiro.getWebSite();
        if(!url.startsWith("http://")){
            url = "http://" + url;
        }
        //Seta o parâmetro a ser passado para o outro aplicativo
        //ao receber http:// o SO entende que deve abrir um browser
        intentSite.setData(Uri.parse(url));
        //configura o munu site para disparar o intent ao ser selecionado
        site.setIntent(intentSite);


        MenuItem fone = menu.add("Ligar para o " + pesqueiro.getNome());
        Intent intentFone = new Intent(Intent.ACTION_VIEW);
        //Uri tel: abre o app de telefone
        intentFone.setData(Uri.parse("tel:" + Uri.encode(pesqueiro.getTelefone())));
        fone.setIntent(intentFone);

        MenuItem mapa = menu.add("Localizar " + pesqueiro.getNome() + " no Mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        //Uri geo: abre o app de mapas
        intentMapa.setData(Uri.parse("geo:0,0?q=" + Uri.encode(pesqueiro.getEndereco())));
        mapa.setIntent(intentMapa);

        MenuItem sms = menu.add("Enviar dica sobre o " + pesqueiro.getNome());
        sms.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intentDica = new Intent(Intent.ACTION_VIEW);
                String dica = pesqueiro.getNome() + " " + pesqueiro.getTelefone() + " " + pesqueiro.getWebSite();
                //Uri sms: abre o app de mensagens
                intentDica.setData(Uri.parse("sms:"));
                //putExtra envia a String dica como parâmetro para o intentDica
                intentDica.putExtra("sms_body", dica);
                startActivity(intentDica);
                return false;
            }
        });

        MenuItem deletar = menu.add("Excluir Pesqueiro");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                PesqueiroDAO pesqueiroDAO = new PesqueiroDAO(PesqueirosListActivity.this);
                String strId = Integer.toString(pesqueiro.getId());
                pesqueiroDAO.excluir(strId);
                pesqueiroDAO.close();
                carregaLista();
                return false;
            }
        });
    }

    //Método que carrega o listPesqueiro a partir do DAO.buscaPesqueiros
    private void carregaLista() {
        List<Pesqueiro> pesqueiros = new ArrayList<>();
        PesqueiroDAO pesqueiroDAO = new PesqueiroDAO(this);
        pesqueiros = pesqueiroDAO.buscaPesqueiros();
        pesqueiroDAO.close();

        //Popula o listPesqueiros
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,pesqueiros);
        listPesqueiros.setAdapter(adapter);
    }

    //Método que chama o FormularioActivity sem parâmetro, para criar um novo
    public void novoPesqueiro(View v){
        Intent intentChamaFormulario = new Intent(PesqueirosListActivity.this, PesqueiroFormActivity.class);
        startActivity(intentChamaFormulario);
    }

    //recarrega o list
    @Override
    protected void onResume() {
        super.onResume();
        //Carrega a lista sempre que onResume a chamado
        carregaLista();
    }
}
