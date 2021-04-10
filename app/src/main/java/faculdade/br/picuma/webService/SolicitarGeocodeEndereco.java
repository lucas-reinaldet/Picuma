package faculdade.br.picuma.webService;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import faculdade.br.picuma.model.Endereco;
import faculdade.br.picuma.util.Constantes;

public class SolicitarGeocodeEndereco extends AsyncTask<String, Void, Endereco> {
    @Override
    protected Endereco doInBackground(String... strings) {

        HttpURLConnection urlConnection = null;
        BufferedReader leitor = null;
        Endereco endereco = null;

        try {

            URL url = new URL(Constantes.URL_BUSCA_GEOCODE_ENDERECO + Constantes.FORMATO_BUSCA_JSON + Constantes.URL_BUSCA_GEOCODE_ENDERECO_PARANS_ADDRESS + strings[0].replace(" ", "+") + Constantes.URL_BUSCA_GEOCODE_ENDERECO_PARANS_KEY);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            urlConnection.setConnectTimeout(1000);
            InputStream entrada = urlConnection.getInputStream();

            InputStreamReader transformador = new InputStreamReader(entrada);
            leitor = new BufferedReader(transformador);
            String dados = "";
            StringBuffer buffer = new StringBuffer();

            while ((dados = leitor.readLine()) != null) {
                buffer.append(dados);
            }

            String resposta = buffer.toString();

            JSONObject json = new JSONObject(resposta);

            String lat = ((JSONArray) json.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
            String lng = ((JSONArray) json.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();

            endereco = new Endereco();

            endereco.setLatitude(Double.valueOf(lat));
            endereco.setLongitute(Double.valueOf(lng));

        } catch (Exception e) {

            e.printStackTrace();

        }

        return endereco;
    }
}
