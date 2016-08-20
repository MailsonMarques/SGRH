package br.com.sga.sga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class MainActivity extends AppCompatActivity {

    EditText txtEmailLogin;
    EditText txtSenhaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEmailLogin = (EditText) findViewById(R.id.txtEmail);
        txtSenhaLogin = (EditText) findViewById(R.id.txtSenha);

        txtEmailLogin.setText(getIntent().getStringExtra("email"));
        txtSenhaLogin.setText(getIntent().getStringExtra("senha"));

        AndroidUtils cd = new AndroidUtils(this);

        if(cd.isConnectingToInternet()){

            assert ((Button)findViewById(R.id.btnLogin)) != null;
            ((Button)findViewById(R.id.btnLogin)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Ion.with(getBaseContext()).load("http://172.22.15.108/sgrh/WebService/login.php")
                            .setBodyParameter("email", txtEmailLogin.getText().toString())
                            .setBodyParameter("senha", txtSenhaLogin.getText().toString())
                            .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            if (result.get("retorno").getAsString().equals("YES")) {

                                Toast.makeText(getBaseContext(), "Bem Vindo!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getBaseContext(), ListEnderecoActivity.class);
                                intent.putExtra("codigo", result.get("idCadastro").getAsString());

                                finish();
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplication(), R.string.error_user_senha, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            });

            ((Button)findViewById(R.id.btnCadastro)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(), CadastroActivity.class));
                }
            });
        }else {
            Toast.makeText(this, R.string.error_conexao_indisponivel, Toast.LENGTH_LONG).show();

            ((Button)findViewById(R.id.btnLogin)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();

                    startActivity(getIntent());
                }
            });

            ((Button)findViewById(R.id.btnCadastro)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(getIntent());
                }
            });

        }



    }




}
