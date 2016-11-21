package br.edu.ifspsaocarlos.agenda.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.ifspsaocarlos.agenda.model.Contato;
import br.edu.ifspsaocarlos.agenda.util.PrimaryKeyFactory;
import io.realm.Realm;
import io.realm.RealmResults;

import java.util.ArrayList;
import java.util.List;


public class ContatoDAO {
    public ContatoDAO() {
    }

    public RealmResults<Contato> buscaContato(String nome) {
        RealmResults<Contato> contatos;
        Realm realm = Realm.getDefaultInstance();
        if (nome != null) {
            contatos = realm.where(Contato.class).contains("nome", nome).findAll();
        } else {
            contatos = realm.where(Contato.class).findAll();
        }
        return contatos;
    }

    public void atualizaContato(final Contato c) {
        Realm realm = Realm.getDefaultInstance();
        final Contato contato = realm.where(Contato.class).equalTo("id", c.getId()).findFirst();
        if (contato != null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    contato.setNome(c.getNome());
                    contato.setFone(c.getFone());
                    contato.setEmail(c.getEmail());
                }
            });
        }
    }


    public void insereContato(final Contato c) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                c.setId(PrimaryKeyFactory.getInstance().nextKey(c.getClass()));
                Contato contato = realm.copyToRealm(c);
            }
        });
    }

    public void apagaContato(Contato c) {
        Realm realm = Realm.getDefaultInstance();
        final Contato contato = realm.where(Contato.class).equalTo("id", c.getId()).findFirst();
        realm.executeTransaction(new Realm.Transaction(){

            @Override
            public void execute(Realm realm) {
                if (contato != null) {
                    contato.deleteFromRealm();
                }
            }
        });
    }

    public Contato buscaContato(long id) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Contato.class).equalTo("id", id).findFirst();
    }

    public void atualizaContato(long id, final String name, final String fone, final String email) {
        Realm realm = Realm.getDefaultInstance();
        final Contato contato = realm.where(Contato.class).equalTo("id", id).findFirst();
        if (contato != null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    contato.setNome(name);
                    contato.setFone(fone);
                    contato.setEmail(email);
                }
            });
        }
    }
}
