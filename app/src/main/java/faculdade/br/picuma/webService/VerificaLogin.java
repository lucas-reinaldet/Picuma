package faculdade.br.picuma.webService;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import faculdade.br.picuma.model.Login;
import faculdade.br.picuma.util.Constantes;

/**
 * Created by lucas on 17/04/2018.
 */

public class VerificaLogin extends AsyncTask<String, Void, Login> {
    @Override
    protected Login doInBackground(String... strings) {

        String endereco = Constantes.URL_CONEXAO_WEB_SERVICE + Constantes.PATH_SERVICOS_OFERTADOS + Constantes.PATH_REALIZAR_LOGIN;

        boolean cadastrado = false;

        URL url = null;
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
            os.write(strings[0].getBytes("UTF-8"));
            os.close();

            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                line = sb.toString();
                br.close();
                if (line != null) {
                    return new Gson().fromJson(line, Login.class);
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
