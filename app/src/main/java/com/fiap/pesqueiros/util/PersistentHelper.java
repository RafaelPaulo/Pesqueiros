package com.fiap.pesqueiros.util;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;

import com.fiap.pesqueiros.PesqueiroFormActivity;
import com.fiap.pesqueiros.R;
import com.fiap.pesqueiros.bean.Pesqueiro;

/**
 * Created by rm75094 on 21/10/2016.
 */
public class PersistentHelper {

    private Pesqueiro pesqueiro;
    private final EditText edtPesqueiro, edtId, edtWebSite, edtTelefone, edtEndereco, edtQtdTanques;
    private final RatingBar rbNota;
    private final CheckBox cbPPague, cbPEsportiva;

    public PersistentHelper(PesqueiroFormActivity activity) {
        edtPesqueiro = (EditText) activity.findViewById(R.id.edtPesqueiro);
        edtId = (EditText) activity.findViewById(R.id.edtId);
        edtWebSite = (EditText) activity.findViewById(R.id.edtSite);
        edtTelefone = (EditText) activity.findViewById(R.id.edtTel);
        edtEndereco = (EditText) activity.findViewById(R.id.edtEnd);
        rbNota =  (RatingBar) activity.findViewById(R.id.ratingBar);
        cbPEsportiva = (CheckBox) activity.findViewById(R.id.cbPEsportiva);
        cbPPague = (CheckBox) activity.findViewById(R.id.cbPPague);
        edtQtdTanques = (EditText) activity.findViewById(R.id.edtQdtTanques);
        pesqueiro = new Pesqueiro();
    }

    //Método que pega os valores dos EditTexts e retorna um objeto Pesqueiro
    public Pesqueiro pegaPesqueiro() {
        if(edtId.getText().length()>0)
            pesqueiro.setId(Integer.valueOf(edtId.getText().toString()));

        pesqueiro.setNome(edtPesqueiro.getText().toString());
        pesqueiro.setWebSite(edtWebSite.getText().toString());
        pesqueiro.setTelefone(edtTelefone.getText().toString());
        pesqueiro.setEndereco(edtEndereco.getText().toString());
        pesqueiro.setRating(rbNota.getRating());
        pesqueiro.setQuantidadeTanques(Integer.parseInt(edtQtdTanques.getText().toString()));
        pesqueiro.setPesqueEpaque(cbPPague.isChecked());
        pesqueiro.setPescaEsportiva(cbPEsportiva.isChecked());
        return pesqueiro;
    }

    //Métodos que preenche o Edits com os valores de um objeto Pesqueiro
    public void preencheEdts(Pesqueiro pesqueiro){
        if(pesqueiro != null){
            try {
                edtPesqueiro.setText(pesqueiro.getNome());
                edtId.setText(Integer.toString(pesqueiro.getId()));
                edtId.setVisibility(View.INVISIBLE);
                edtWebSite.setText(pesqueiro.getWebSite());
                edtTelefone.setText(pesqueiro.getTelefone());
                edtEndereco.setText(pesqueiro.getEndereco());
                rbNota.setRating(pesqueiro.getRating());
                cbPEsportiva.setChecked(pesqueiro.isPescaEsportiva());
                cbPPague.setChecked(pesqueiro.isPesqueEpaque());
                edtQtdTanques.setText(Integer.toString(pesqueiro.getQuantidadeTanques()));
                this.pesqueiro = pesqueiro;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    //Método que valida se os editTexts estão preenchidos
    public boolean validaCampos(PesqueiroFormActivity activity) {
        String pesqueiro = edtPesqueiro.getText().toString().trim();
        String tanques = edtQtdTanques.getText().toString().trim();

        if(pesqueiro.isEmpty()){
            edtPesqueiro.setError(activity.getString(R.string.required_field_message));
        }

        if(tanques.isEmpty()){
            edtQtdTanques.setError(activity.getString(R.string.required_field_message));
        }

        return (!TextUtils.isEmpty(pesqueiro) && !TextUtils.isEmpty(tanques));
    }

}
