package faculdade.br.picuma.webService;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import faculdade.br.picuma.model.Servico;
import faculdade.br.picuma.util.Constantes;

public class SolicitarListaServico extends AsyncTask<Void, Void, List<Servico>> {
    @Override
    protected List<Servico> doInBackground(Void... v) {

        String endereco = Constantes.URL_CONEXAO_WEB_SERVICE + Constantes.PATH_SERVICOS_OFERTADOS + Constantes.PATH_LISTAR_SERVICO;

        URL url = null;
        List<Servico> listaServico = null;
        try {
            url = new URL(endereco);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Method", "POST");
            con.setConnectTimeout(1000);
            OutputStream os = con.getOutputStream();
            os.close();


            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                line = sb.toString();
                br.close();

                if (line != null) {
                    Type dados = new TypeToken<List<Servico>>() {
                    }.getType();

                    listaServico = new Gson().fromJson(line.replace("\"", "'"), dados);

                    return listaServico;
                } else {
                    return null;
                }

            }else{
                System.err.println(con.getResponseCode() + "   " + con.getResponseMessage());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
