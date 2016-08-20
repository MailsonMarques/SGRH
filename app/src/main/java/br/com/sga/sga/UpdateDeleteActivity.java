package br.com.sga.sga;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class UpdateDeleteActivity extends AppCompatActivity {

    String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        Intent intent = getIntent();

        EditText txtEndereco = (EditText) findViewById(R.id.txtEndereco);
        EditText txtNumero = (EditText) findViewById(R.id.txtNumero);
        EditText txtBairro = (EditText) findViewById(R.id.txtBairro);
        EditText txtCidade = (EditText) findViewById(R.id.txtCidade);
        EditText txtTipo = (EditText) findViewById(R.id.txtTipo);

        codigo = intent.getStringExtra("cod");
        txtEndereco.setText(intent.getStringExtra("endCad"));
        txtNumero.setText(intent.getStringExtra("endNum"));
        txtBairro.setText(intent.getStringExtra("endBai"));
        txtCidade.setText(intent.getStringExtra("endCid"));
        txtTipo.setText(intent.getStringExtra("endTip"));


    }
}
