package br.com.sga.sga;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class UpdateDeleteActivity extends AppCompatActivity {

    String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        Intent intent = getIntent();

        final EditText txtEndereco = (EditText) findViewById(R.id.txtEndereco);
        final EditText txtNumero = (EditText) findViewById(R.id.txtNumero);
        final EditText txtBairro = (EditText) findViewById(R.id.txtBairro);
        final EditText txtCidade = (EditText) findViewById(R.id.txtCidade);
        final EditText txtTipo = (EditText) findViewById(R.id.txtTipo);

        codigo = intent.getStringExtra("codigo");
        txtEndereco.setText(intent.getStringExtra("endCad"));
        txtNumero.setText(intent.getStringExtra("endNum"));
        txtBairro.setText(intent.getStringExtra("endBai"));
        txtCidade.setText(intent.getStringExtra("endCid"));
        txtTipo.setText(intent.getStringExtra("endTip"));

        Button btnAtulizar = (Button) findViewById(R.id.btnAtualizar);
        btnAtulizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ion.with(getBaseContext()).load("http://172.22.29.224/sgrh/WebService/atualizar.php")
                        .setBodyParameter("codigo", codigo)
                        .setBodyParameter("endCad", txtEndereco.getText().toString())
                        .setBodyParameter("endNum", txtNumero.getText().toString())
                        .setBodyParameter("endBai", txtBairro.getText().toString())
                        .setBodyParameter("endCid", txtCidade.getText().toString())
                        .setBodyParameter("endTip", txtTipo.getText().toString())
                        .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(result.get("retorno").getAsString().equals("YES")){
                            Toast.makeText(getBaseContext(), R.string.atualiza_cad, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });

        Button btnDeletar = (Button) findViewById(R.id.btnDeletar);
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ion.with(getBaseContext()).load("http://172.22.29.224/sgrh/WebService/deletar.php")
                        .setBodyParameter("codigo", codigo)
                        .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(result.get("retorno").getAsString().equals("YES")){
                            Toast.makeText(getBaseContext(), R.string.delete_cad, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });
    }
}
