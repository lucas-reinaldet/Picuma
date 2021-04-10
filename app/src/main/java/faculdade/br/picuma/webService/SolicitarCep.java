package faculdade.br.picuma.webService;

import android.os.AsyncTask;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import faculdade.br.picuma.model.Endereco;
import faculdade.br.picuma.util.Constantes;

public class SolicitarCep extends AsyncTask<String, Void, Endereco> {
    @Override
    protected Endereco doInBackground(String... strings) {

        HttpURLConnection urlConnection = null;
        BufferedReader leitor = null;
        Endereco endereco = null;

        try {

            URL url = new URL(Constantes.URL_BUSCA_CEP + strings[0] + Constantes.FORMATO_BUSCA_JSON);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(1000);
            urlConnection.connect();

            InputStream entrada = urlConnection.getInputStream();

            InputStreamReader transformador = new InputStreamReader(entrada);

            leitor = new BufferedReader(transformador);

            String dados = "";

            StringBuffer buffer = new StringBuffer();

            while ((dados = leitor.readLine()) != null) {

                buffer.append(dados);

            }

            String resposta = buffer.toString();

            JsonObject json = new JsonParser().parse(resposta).getAsJsonObject();

            endereco = new Endereco();

            endereco.setLogradouro(json.get("logradouro").toString().replace("\"", ""));
            endereco.setBairro(json.get("bairro").toString().replace("\"", ""));
            endereco.setCidade(json.get("localidade").toString().replace("\"", ""));
            endereco.setEstado(json.get("uf").toString().replace("\"", ""));

        } catch (Exception e) {

            e.printStackTrace();

        }

        return endereco;
    }
}
