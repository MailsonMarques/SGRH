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

public class CadEnderecoActivity extends AppCompatActivity {

    Integer codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        codigo = Integer.parseInt(intent.getStringExtra("codigo"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_endereco);

        Button btnCadastrarEnd = (Button) findViewById(R.id.btnCadastrarEnd);
        btnCadastrarEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    EditText txtEndereco = (EditText) findViewById(R.id.txtEndereco);
                    EditText txtNumero = (EditText) findViewById(R.id.txtNumero);
                    EditText txtBairro = (EditText) findViewById(R.id.txtBairro);
                    EditText txtCidade = (EditText) findViewById(R.id.txtCidade);
                    EditText txtTipo = (EditText) findViewById(R.id.txtTipo);

                    Ion.with(getBaseContext()).load("http://172.22.15.108/sgrh/WebService/inserirEnd.php")
                            .setBodyParameter("idEnd", String.valueOf(codigo))
                            .setBodyParameter("enderecoCadastro", txtEndereco.getText().toString())
                            .setBodyParameter("enderecoNumero", txtNumero.getText().toString())
                            .setBodyParameter("enderecoBairro", txtBairro.getText().toString())
                            .setBodyParameter("enderecoCidade", txtCidade.getText().toString())
                            .setBodyParameter("enderecoTipo", txtTipo.getText().toString())
                            .asJsonObject().setCallback(new FutureCallback<JsonObject>(){
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if(result.get("retorno").getAsString().equals("YES")){
                                Toast.makeText(getBaseContext(), "Cadastro Concluido com sucesso!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getBaseContext(), ListEnderecoActivity.class);
                                intent.putExtra("codigo", String.valueOf(codigo));
                                finish();
                                startActivity(intent);

                            } else{
                                Toast.makeText(getBaseContext(), "Erro ao cadastrar!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

        });
    }
    private class HttpRequest extends AsyncTask<String, Void, String> {

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

        @Override
        protected  void onPostExecute(String result){

            if(result.equals("YES")){
                Toast.makeText(getBaseContext(), "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            } else{
                Toast.makeText(getBaseContext(), "Erro ao cadastrar o cliente!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
