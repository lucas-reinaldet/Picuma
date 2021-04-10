package faculdade.br.picuma.control;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import faculdade.br.picuma.model.Login;
import faculdade.br.picuma.util.Constantes;
import faculdade.br.picuma.util.Util;
import faculdade.br.picuma.webService.VerificaLogin;

/**
 * Created by lucas on 17/04/2018.
 */

public class LoginControl {

    private static LoginControl instance;
    private Util util;

    private LoginControl() {
    }

    public static LoginControl getInstance() {

        if (instance == null) {

            instance = new LoginControl();
        }
        return instance;
    }

    private Util getUtil() {

        if (this.util == null) {

            this.util = Util.getInstance();
        }
        return this.util;
    }

    public boolean realizarLogin(Login login, Context context, boolean loginGoogle) {

        String jsonDados = new Gson().toJson(login, Login.class);
        VerificaLogin verificaLogin = new VerificaLogin();
        Login dados = null;
        try {
            dados = verificaLogin.execute(jsonDados).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (dados != null) {
            if (dados.getCliente() != null && dados.getCliente().getIdCliente() > 0) {
                ClienteControl clienteControl = ClienteControl.getInstance();
                clienteControl.dadosParaApresentacao(context, dados);
                return true;
            } else if (dados.getEmpresa() != null && dados.getEmpresa().getIdEmpresa() > 0) {
                EmpresaControl empresaControl = EmpresaControl.getInstance();
                empresaControl.dadosParaApresentacao(context, dados);
                return true;
            }
        } else if (loginGoogle) {
            return false;
        } else {
            Toast.makeText(context, Constantes.M_AT_USUARIO_SENHA_INVALIDO, Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
