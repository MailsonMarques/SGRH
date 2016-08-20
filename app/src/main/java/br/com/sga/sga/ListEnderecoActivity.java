package br.com.sga.sga;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class ListEnderecoActivity extends AppCompatActivity {


    private ArrayAdapter<JsonObject> clientesAd;
    String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_endereco);

    }

    @Override
    protected void onResume() {

        Intent intent = getIntent();
        codigo = intent.getStringExtra("codigo");

        super.onResume();
        clientesAd = new ArrayAdapter<JsonObject>(this, 0) {
            public View getView(int position, View view, ViewGroup viewGroup) {
                if (view == null) {
                    view = getLayoutInflater().inflate(R.layout.model_enderecos, null);
                }

                JsonObject obj = getItem(position);

                TextView txtNum = (TextView) view.findViewById(R.id.txtNum);
                txtNum.setText(obj.get("enderecoNumero").getAsString());

                TextView txtCidade = (TextView) view.findViewById(R.id.txtCidade);
                txtCidade.setText(obj.get("enderecoCidade").getAsString());

                TextView txtEndereco = (TextView) view.findViewById(R.id.txtEndereco);
                txtEndereco.setText(obj.get("enderecoCadastro").getAsString());

                return view;
            }
        };

        Ion.with(getBaseContext()).load("http://172.22.15.108/sgrh/WebService/listar.php")
                .setBodyParameter("idEnd", String.valueOf(codigo))
                .asJsonArray().setCallback(new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result_json) {

                for (int i = 0; i < result_json.size(); i++) {
                    JsonObject jsonObject = result_json.get(i).getAsJsonObject();
                    clientesAd.add(jsonObject);
                }

                ListView ltwClientes = (ListView) findViewById(R.id.ltwEnderecos);
                ltwClientes.setAdapter(clientesAd);

                ltwClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        JsonObject obj = (JsonObject) parent.getItemAtPosition(position);

                        Intent intent = new Intent(getBaseContext(), HomeActivity.class);

                        intent.putExtra("codigo", codigo);



                        startActivity(intent);
                    }
                });
            }
        });

        ((Button)findViewById(R.id.btnCadNovo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), CadEnderecoActivity.class);
                intent.putExtra("codigo", String.valueOf(codigo));
                startActivity(intent);
            }
        });

        ((Button)findViewById(R.id.btnCadEdit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EditarCadastroActivity.class);
                intent.putExtra("codigo", String.valueOf(codigo));
                startActivity(intent);
            }
        });

    }
}
