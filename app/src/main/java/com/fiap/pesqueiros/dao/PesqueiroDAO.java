package com.fiap.pesqueiros.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fiap.pesqueiros.bean.Pesqueiro;

import java.util.ArrayList;
import java.util.List;

public class PesqueiroDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Pesqueiro.db";
    private static final int DATABASE_VERSION = 1;

    public PesqueiroDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql= "CREATE TABLE TBL_PESQUEIRO (" +
                    "ID INTEGER PRIMARY KEY, " +
                    "QT_TANQUES INTEGER, " +
                    "NM_PESQUEIRO TEXT," +
                    "DS_SITE TEXT," +
                    "DS_ENDERECO TEXT," +
                    "DS_TELEFONE TEXT," +
                    "NM_IS_PESQUE_E_PAGUE INTEGER," +
                    "NM_IS_PESCA_ESPORTIVA INTEGER," +
                    "VL_RATING REAL )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS TBL_PESQUEIRO";
        db.execSQL(sql);
        onCreate(db);
    }

    //Método que insere o Pesqueiro na Base
    public void inserir(Pesqueiro pesqueiro) {
        SQLiteDatabase db = getWritableDatabase();
        //Classe que encapsula os valores que serao passados como parâmetros
        ContentValues cv = pegaDados(pesqueiro);
        // INSERT INTO TBL_PESQUEIRO (param, param, ...) VALUES (...,...)
        //null forma de tratamento de valores nulos
        db.insert("TBL_PESQUEIRO", null, cv);
        db.close();
    }

    //Método que atualiza o Pesqueiro na Base
    public void atualizar(Pesqueiro pesqueiro) {
        SQLiteDatabase db = getWritableDatabase();
        String strId = String.valueOf(pesqueiro.getId());
        //Classe que encapsula os valores que serao passados como paramtros
        ContentValues cv = pegaDados(pesqueiro);
        String[] id = {strId};
        // UPDATE TBL_ SET NM_=?, VL_=? WHERE ID=?
        //P3 Where P4 Parametro
        db.update("TBL_PESQUEIRO", cv, "ID=?", id);
        db.close();
    }

    //Método que exclui o Pesqueiro na Base
    public void excluir(String strId) {
        SQLiteDatabase db = getWritableDatabase();
        String[] id = {strId};
        // DELETE FROM TBL_PESQUEIRO WHERE ID=?
        //P2 Where P3 Parametro
        db.delete("TBL_PESQUEIRO", "ID=?", id);
        db.close();
    }

    //Método que carrega o listPesqueiros
    public List<Pesqueiro> buscaPesqueiros() {
        SQLiteDatabase db = getWritableDatabase();
        List<Pesqueiro> pesqueiros = new ArrayList<>();
        //Select * from TBL_PESQUEIRO
        Cursor cursor = db.query("TBL_PESQUEIRO", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            Pesqueiro pesqueiro = new Pesqueiro();
            pesqueiro.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            pesqueiro.setNome(cursor.getString(cursor.getColumnIndex("NM_PESQUEIRO")));
            pesqueiro.setTelefone(cursor.getString(cursor.getColumnIndex("DS_TELEFONE")));
            pesqueiro.setEndereco(cursor.getString(cursor.getColumnIndex("DS_ENDERECO")));
            pesqueiro.setRating(cursor.getFloat(cursor.getColumnIndex("VL_RATING")));
            pesqueiro.setPescaEsportiva( cursor.getInt(cursor.getColumnIndex("NM_IS_PESQUE_E_PAGUE")) == 1 ? true : false );
            pesqueiro.setPesqueEpaque(cursor.getInt(cursor.getColumnIndex("NM_IS_PESCA_ESPORTIVA"))  == 1 ? true : false);
            pesqueiro.setWebSite(cursor.getString(cursor.getColumnIndex("DS_SITE")));
            pesqueiro.setQuantidadeTanques(cursor.getInt(cursor.getColumnIndex("QT_TANQUES")));
            pesqueiros.add(pesqueiro);
        }
        db.close();
        return pesqueiros;
    }

    private ContentValues pegaDados(Pesqueiro pesqueiro) {
        ContentValues cv = new ContentValues();
        cv.put("NM_PESQUEIRO", pesqueiro.getNome());
        cv.put("DS_TELEFONE", pesqueiro.getTelefone());
        cv.put("DS_ENDERECO", pesqueiro.getEndereco());
        cv.put("VL_RATING", pesqueiro.getRating());
        cv.put("NM_IS_PESQUE_E_PAGUE", pesqueiro.isPesqueEpaque());
        cv.put("NM_IS_PESCA_ESPORTIVA", pesqueiro.isPescaEsportiva());
        cv.put("DS_SITE", pesqueiro.getWebSite());
        cv.put("QT_TANQUES", pesqueiro.getQuantidadeTanques());
        return cv;
    }
}
