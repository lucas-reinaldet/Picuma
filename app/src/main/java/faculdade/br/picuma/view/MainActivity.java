package faculdade.br.picuma.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import faculdade.br.picuma.R;
import faculdade.br.picuma.control.LoginControl;
import faculdade.br.picuma.model.Login;
import faculdade.br.picuma.util.Constantes;
import faculdade.br.picuma.util.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GoogleSignInClient mGoogleSignInClient;
    private final static String TAG = "SignInCliente";
    private Button btCadastrarUuario;
    private Button btLogar;
    private SignInButton signInButton;
    private TextView etSenhaLogin;
    private TextView etUsuarioLogin;
    private Login login;
    private LoginControl loginControl;

    private GoogleSignInAccount account;

    private LoginControl getLoginControl() {

        if (this.loginControl == null) {
            this.loginControl = LoginControl.getInstance();
        }
        return this.loginControl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScrollView scrollView = new ScrollView(this);
        FrameLayout.LayoutParams layoutParamsFrameLayout = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layoutParamsFrameLayout);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParamsLinearLayout);
        scrollView.addView(linearLayout);

        signInButton = findViewById(R.id.sign_in_button);
        btCadastrarUuario = findViewById(R.id.btCadastrarUuario);
        btLogar = findViewById(R.id.btLogar);
        etSenhaLogin = findViewById(R.id.etSenhaLogin);
        etUsuarioLogin = findViewById(R.id.etUsuarioLogin);

        signInButton.setOnClickListener(this);
        btCadastrarUuario.setOnClickListener(this);
        btLogar.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        updateUI(account);

    }

    private void updateUI(GoogleSignInAccount account) {

        if (account != null) {
        } else {
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btLogar:

                realizarLogin();

                break;

            case R.id.btCadastrarUuario:

                startActivity(new Intent(getApplicationContext(), CadastroActivity.class));

                break;

            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Constantes.RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constantes.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Login login = new Login();

            login.setUsuario(account.getEmail());
            login.setLoginGoogle(account.getId().toString());
            login.setSenha(Util.getHash(account.getId().toString()));

            if (!getLoginControl().realizarLogin(login, this, true)) {
                this.account = account;
                escolherTipoDePerfil();
            } else {
            }
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void realizarLogin() {

        this.login = new Login();

        this.login.setUsuario(etUsuarioLogin.getText().toString());
        this.login.setSenha(Util.getHash(etSenhaLogin.getText().toString()));

        getLoginControl().realizarLogin(this.login, getApplicationContext(), false);
    }

    private void escolherTipoDePerfil() {

        final CharSequence[] opcoes = {
                "Sou um cliente",
                "Sou uma empresa",
                "Cancelar"
        };

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Escolha uma opção");

        builder.setItems(opcoes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (opcoes[which].equals("Sou um cliente")) {

                    cadastrarCliente();

                } else if (opcoes[which].equals("Sou uma empresa")) {

                    cadastrarEmpresa();

                } else {

                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cadastrarEmpresa() {

        Intent intent = new Intent(this, CadastroEmpresaActivity.class);
        intent.putExtra("dados", true);
        intent.putExtra("email", account.getEmail());
        intent.putExtra("id_google", account.getId());
        if (account.getPhotoUrl() != null) {
            intent.putExtra("foto", account.getPhotoUrl().toString());
        }        intent.putExtra("nome", account.getGivenName() + " " + account.getFamilyName());
        startActivity(intent);
    }

    private void cadastrarCliente() {

        Intent intent = new Intent(this, CadastroClienteActivity.class);

        intent.putExtra("dados", true);
        intent.putExtra("email", account.getEmail());
        intent.putExtra("id_google", account.getId());
        if (account.getPhotoUrl() != null) {
            intent.putExtra("foto", account.getPhotoUrl().toString());
        }
        intent.putExtra("nome", account.getGivenName() + " " + account.getFamilyName());

        startActivity(intent);
    }

}