package br.com.sga.sga;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText txtNome = (EditText) findViewById(R.id.txtNome);
                EditText txtCpf = (EditText) findViewById(R.id.txtCpf);
                EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
                EditText txtSenha = (EditText) findViewById(R.id.txtSenha);

                Ion.with(getBaseContext()).load("http://172.22.29.224/sgrh/WebService/inserir.php")
                        .setBodyParameter("nome", txtNome.getText().toString())
                        .setBodyParameter("cpf", txtCpf.getText().toString())
                        .setBodyParameter("emailCadastro", txtEmail.getText().toString())
                        .setBodyParameter("senhaCadastro", txtSenha.getText().toString())
                        .asJsonObject().setCallback(new FutureCallback<JsonObject>(){
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("retorno").getAsString().equals("YES")) {
                            Toast.makeText(getBaseContext(), "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getBaseContext(), CadEnderecoActivity.class);
                            intent.putExtra("codigo", result.get("idCadastro").getAsString());
                            finish();
                            startActivity(intent);
                        }
                    }

                });

            }
        });
    }

    private class HttpRequest extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String retorno = null;
            try {
                String urlHttp = params[0];
                URL url = new URL(urlHttp);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                InputStreamReader ips = new InputStreamReader(http.getInputStream());
                BufferedReader bfr = new BufferedReader(ips);
                retorno = bfr.readLine();

            } catch (Exception e) {

            }

            return retorno;
        }

    }

}
