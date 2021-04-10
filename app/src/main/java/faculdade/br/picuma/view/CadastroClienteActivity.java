package faculdade.br.picuma.view;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaContato;
import faculdade.br.picuma.control.ClienteControl;
import faculdade.br.picuma.dialog.DialogDatePicker;
import faculdade.br.picuma.model.Cliente;
import faculdade.br.picuma.model.Contato;
import faculdade.br.picuma.model.Login;
import faculdade.br.picuma.util.Constantes;
import faculdade.br.picuma.util.Util;

import static android.os.Environment.*;

public class CadastroClienteActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemLongClickListener {

    private DialogDatePicker dialogDatePicker;
    private EditText etNomeClienteCadastroCliente;
    private EditText etCpfCadastroCliente;
    private EditText etUsuarioCadastroCliente;
    private EditText etSenhaCadastroCliente;
    private EditText etDataNascimentoCadastroCliente;
    private EditText etLoginGoogleCadastroCliente;

    private Button btCancelarCadastroCliente;
    private Button btCadastrarCliente;
    private Button btAdicionarImagemCadastroCliente;
    private CircleImageView ivApresentacaoPreviaFotoCadastroCliente;

    private File arquivoImagem;

    private ClienteControl clienteControl;
    private String caminhoImagemCamera;
    private byte[] byteImagem;


    private Spinner spinnerDialogTipoContato;

    private EditText etDialogCadastroContato;
    private Button btDialogCancelarCadastroContato;
    private Button btDialogCadastrarContatoMaisUm;
    private Button btDialogCadastroContato;
    private Button btAdicionarContatoCadastroCliente;

    private ListView lvListaContatoCadastroCliente;

    private AdapterListaContato adapterListaContato;

    private AlertDialog dialogContato;

    private List<Contato> listContato = new ArrayList<>();

    private String tipoContato;
    private int tamListaApresentacao = 0;
    private RadioButton rbFemininoCadastroCliente;
    private RadioButton rbMasculinoCadastroCliente;


    public ClienteControl getClienteControl() {

        return this.clienteControl = ClienteControl.getInstance();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);
        setTitle(Constantes.NOME_ACTIVITY_CADASTRO_CLIENTE);

        NestedScrollView scrollView = new NestedScrollView(this);
        FrameLayout.LayoutParams layoutParamsFrameLayout = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layoutParamsFrameLayout);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParamsLinearLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParamsLinearLayout);
        scrollView.addView(linearLayout);

        this.dialogDatePicker = new DialogDatePicker();

        etNomeClienteCadastroCliente = findViewById(R.id.etNomeClienteCadastroCliente);
        etCpfCadastroCliente = findViewById(R.id.etCpfCadastroCliente);
        etUsuarioCadastroCliente = findViewById(R.id.etUsuarioCadastroCliente);
        etSenhaCadastroCliente = findViewById(R.id.etSenhaCadastroCliente);
        etLoginGoogleCadastroCliente = findViewById(R.id.etLoginGoogleCadastroCliente);
        rbFemininoCadastroCliente = findViewById(R.id.rbFemininoCadastroCliente);
        rbMasculinoCadastroCliente = findViewById(R.id.rbMasculinoCadastroCliente);
        btAdicionarContatoCadastroCliente = findViewById(R.id.btAdicionarContatoCadastroCliente);

        lvListaContatoCadastroCliente = findViewById(R.id.lvListaContatoCadastroCliente);

        adapterListaContato = new AdapterListaContato(this);

        lvListaContatoCadastroCliente.setAdapter(adapterListaContato);

        lvListaContatoCadastroCliente.setOnItemLongClickListener(this);
        btCancelarCadastroCliente = findViewById(R.id.btCancelarCadastroCliente);
        btCadastrarCliente = findViewById(R.id.btCadastrarCliente);
        btAdicionarImagemCadastroCliente = findViewById(R.id.btAdicionarImagemCadastroCliente);
        etDataNascimentoCadastroCliente = findViewById(R.id.etDataNascimentoCadastroCliente);

        btCancelarCadastroCliente.setOnClickListener(this);
        btCadastrarCliente.setOnClickListener(this);
        etDataNascimentoCadastroCliente.setOnClickListener(this);
        btAdicionarImagemCadastroCliente.setOnClickListener(this);
        btAdicionarContatoCadastroCliente.setOnClickListener(this);

        ivApresentacaoPreviaFotoCadastroCliente = findViewById(R.id.ivApresentacaoPreviaFotoCadastroCliente);

        Intent intent = getIntent();

        if (intent.getBooleanExtra("dados", false)) {

            this.setValores(intent);
        }
    }

    private void setValores(Intent intent) {

        etNomeClienteCadastroCliente.setText(intent.getStringExtra("nome"));

        etUsuarioCadastroCliente.setText(intent.getStringExtra("email"));
        etUsuarioCadastroCliente.setVisibility(View.INVISIBLE);
        etUsuarioCadastroCliente.setFocusable(false);
        etUsuarioCadastroCliente.setHeight(0);
        etUsuarioCadastroCliente.setClickable(false);

        TextView x = findViewById(R.id.tvUsuarioCadastroCliente);
        x.setVisibility(View.INVISIBLE);
        x.setTextSize(0);

        etSenhaCadastroCliente.setText(intent.getStringExtra("id_google"));
        etSenhaCadastroCliente.setVisibility(View.INVISIBLE);
        etSenhaCadastroCliente.setFocusable(false);
        etSenhaCadastroCliente.setHeight(0);
        etSenhaCadastroCliente.setClickable(false);

        x = findViewById(R.id.tvSenhaCadastroCliente);
        x.setVisibility(View.INVISIBLE);
        x.setTextSize(0);

        Contato contato = new Contato();

        contato.setContato(intent.getStringExtra("email"));
        contato.setTipoContato("E-mail");
        listContato.add(contato);

        adapterListaContato.setListaContato(listContato);

        etLoginGoogleCadastroCliente.setText(intent.getStringExtra("id_google"));

        final String urlImagem = intent.getStringExtra("foto");

        Glide.with(this).load(urlImagem).into(ivApresentacaoPreviaFotoCadastroCliente);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL urlAdress = new URL(urlImagem);
                    InputStream is = urlAdress.openStream();
                    String filename = Uri.parse(urlAdress.toString()).getLastPathSegment();
                    File outputFile = new File(getApplicationContext().getCacheDir(), filename);
                    OutputStream os = new FileOutputStream(outputFile);
                    byteImagem = new byte[(int) outputFile.length()];

                    int length;

                    while ((length = is.read(byteImagem)) != -1) {
                        os.write(byteImagem, 0, length);

                        is.close();
                        os.close();

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btCancelarCadastroCliente:

                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                break;

            case R.id.btCadastrarCliente:

                this.preparaCadastro();

                break;

            case R.id.etDataNascimentoCadastroCliente:

                this.openDatePicker();

                break;

            case R.id.btAdicionarImagemCadastroCliente:

                this.pedirPermissaoDeAcesso();
                break;

            case R.id.btAdicionarContatoCadastroCliente:

                this.chamarDialogCadastroContato();
                break;

            case R.id.btDialogCadastrarContatoMaisUm:

                setarContatoEAdicionarMaisUm();

                break;

            case R.id.btDialogCadastroContato:

                setarContatoEFecharDialog();

                break;

            case R.id.btDialogCancelarCadastroContato:

                dialogContato.dismiss();

                break;

        }
    }


    private void adicionarImagem() {

        final CharSequence[] opcoes = {
                "Pegar uma imagem",
                "Tirar uma foto",
                "Cancelar"
        };

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Escolha uma opção");

        builder.setItems(opcoes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (opcoes[which].equals("Pegar uma imagem")) {

                    abrirGaleria();

                } else if (opcoes[which].equals("Tirar uma foto")) {

                    abrirCamera();

                } else {

                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void abrirCamera() {

        File arquivo = new File(getExternalStorageDirectory(), Constantes.CAMINHO_DIRETORIO_IMAGEM);
        boolean criada = arquivo.exists();

        if (!criada) {
            criada = arquivo.mkdirs();
        }

        if (criada) {

            Long mills = System.currentTimeMillis() / 1000;
            String nomeArquivo = mills.toString() + ".jpg";

            caminhoImagemCamera = getExternalStorageDirectory() + File.separator + Constantes.CAMINHO_DIRETORIO_IMAGEM + File.separator + nomeArquivo;

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            arquivoImagem = new File(caminhoImagemCamera);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoImagem));

            startActivityForResult(intent, Constantes.COD_REQUEST_CAMERA_CLIENTE);
        }
    }

    private void abrirGaleria() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent, "Selecione"), Constantes.COD_REQUEST_GALERIA_CLIENTE);

    }


    private void openDatePicker() {
        dialogDatePicker.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case Constantes.COD_REQUEST_GALERIA_CLIENTE:

                this.receberFotoDaGaleria(data);

                break;

            case Constantes.COD_REQUEST_CAMERA_CLIENTE:

                this.receberFotoDaCamera();

                break;
        }
    }

    private void receberFotoDaCamera() {

        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{caminhoImagemCamera}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {

                Log.i("Arquivo", "" + caminhoImagemCamera);

            }
        });

        arquivoImagem = new File(caminhoImagemCamera);

        Bitmap bitMap = BitmapFactory.decodeFile(caminhoImagemCamera);

        ivApresentacaoPreviaFotoCadastroCliente.setImageBitmap(bitMap);
    }

    private void receberFotoDaGaleria(Intent data) {
        if (data != null) {

            Uri uri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            String foto = cursor.getString(0);

            arquivoImagem = new File(foto);

            ivApresentacaoPreviaFotoCadastroCliente.setImageBitmap(BitmapFactory.decodeFile(foto));
        }
    }


    private void preparaCadastro() {

        Cliente cliente = new Cliente();
        Login login = new Login();

        String dados = etNomeClienteCadastroCliente.getText().toString();

        if (dados != null && !dados.equals("")) {

            cliente.setNomeCliente(dados);

            dados = etDataNascimentoCadastroCliente.getText().toString();

            if (dados != null && !dados.equals("")) {

                cliente.setNascimentoCliente(Util.getStringDataToCalender(dados));

                dados = etCpfCadastroCliente.getText().toString();

                if (dados != null && !dados.equals("")) {

                    cliente.setCpfCliente(dados);

                    dados = etUsuarioCadastroCliente.getText().toString();

                    if (dados != null && !dados.equals("")) {

                        login.setUsuario(dados);

                        dados = etSenhaCadastroCliente.getText().toString();

                        if (dados != null && !dados.equals("")) {
                            if (listContato.size() > 0) {
                                cliente.setListaContato(listContato);
                                if (arquivoImagem != null) {

                                    int tam = (int) arquivoImagem.length();
                                    byte[] fileArray = new byte[tam];
                                    FileInputStream fis = null;
                                    try {
                                        fis = new FileInputStream(arquivoImagem);
                                        fis.read(fileArray, 0, tam);
                                    } catch (Exception e) {
                                        System.err.println(e);
                                    }

                                    cliente.setFotoCliente(fileArray);
                                } else if (byteImagem != null) {

                                    cliente.setFotoCliente(byteImagem);

                                }

                                if (etLoginGoogleCadastroCliente.getText() != null && !etLoginGoogleCadastroCliente.getText().equals("")) {
                                    login.setLoginGoogle(etLoginGoogleCadastroCliente.getText().toString());
                                }

                                if (rbFemininoCadastroCliente.isChecked()) {
                                    cliente.setGenero(Constantes.FEMININO);
                                } else if (rbMasculinoCadastroCliente.isChecked()) {
                                    cliente.setGenero(Constantes.MASCULINO);
                                }

                                login.setSenha(Util.getHash(dados));
                                login.setCliente(cliente);

                                cadastrarCliente(login);
                            } else {
                                Toast.makeText(getApplicationContext(), Constantes.M_AT_CADASTRO_CONTATO, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), Constantes.M_AT_SENHA, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), Constantes.M_AT_USUARIO, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), Constantes.M_AT_CPF, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), Constantes.M_AT_DATA_NASCIMENTO, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), Constantes.M_AT_NOME, Toast.LENGTH_LONG).show();
        }
    }

    private void cadastrarCliente(Login dados) {

        getClienteControl().cadastrarCliente(getApplicationContext(), dados);
    }


    private void pedirPermissaoDeAcesso() {

        if (Build.VERSION.SDK_INT >= 23) {

            String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, Constantes.COD_SOLICITACAO_PERMISSAO_GALERIA_CAMERA);
            } else {
                adicionarImagem();
            }
        } else {
            adicionarImagem();
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Constantes.COD_SOLICITACAO_PERMISSAO_GALERIA_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    this.adicionarImagem();
                } else {
                    Toast.makeText(this, Constantes.M_INF_PERMISSAO_ACESSO_GALERIA, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void chamarDialogCadastroContato() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_contato_cadastro, null);

        spinnerDialogTipoContato = mView.findViewById(R.id.spinnerDialogTipoContato);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipo_contato, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDialogTipoContato.setAdapter(adapter);
        spinnerDialogTipoContato.setOnItemSelectedListener(this);

        etDialogCadastroContato = mView.findViewById(R.id.etDialogCadastroContato);
        btDialogCancelarCadastroContato = mView.findViewById(R.id.btDialogCancelarCadastroContato);
        btDialogCadastrarContatoMaisUm = mView.findViewById(R.id.btDialogCadastrarContatoMaisUm);
        btDialogCadastroContato = mView.findViewById(R.id.btDialogCadastroContato);

        btDialogCancelarCadastroContato.setOnClickListener(this);
        btDialogCadastrarContatoMaisUm.setOnClickListener(this);
        btDialogCadastroContato.setOnClickListener(this);

        alertDialog.setView(mView);
        dialogContato = alertDialog.create();
        dialogContato.show();

    }

    private void setarContatoEAdicionarMaisUm() {
        if (!etDialogCadastroContato.getText().toString().isEmpty()) {
            Contato objContato = new Contato();
            objContato.setTipoContato(tipoContato);
            objContato.setContato(etDialogCadastroContato.getText().toString());
            listContato.add(objContato);
            adapterListaContato.setListaContato(listContato);
            etDialogCadastroContato.setText("");
        } else {
            Toast.makeText(getApplicationContext(), Constantes.M_CONTATO_VAZIO, Toast.LENGTH_LONG).show();
        }
    }

    private void setarContatoEFecharDialog() {

        if (!etDialogCadastroContato.getText().toString().isEmpty()) {

            Contato objContato = new Contato();

            objContato.setTipoContato(tipoContato);
            objContato.setContato(etDialogCadastroContato.getText().toString());

            listContato.add(objContato);

            adapterListaContato.setListaContato(listContato);

            dialogContato.dismiss();
        } else {
            Toast.makeText(getApplicationContext(), Constantes.M_CONTATO_VAZIO, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tipoContato = parent.getItemAtPosition(position).toString();

        switch (tipoContato) {

            case "Fixo":

                etDialogCadastroContato.setInputType(InputType.TYPE_CLASS_PHONE);

                break;

            case "Celular":

                etDialogCadastroContato.setInputType(InputType.TYPE_CLASS_PHONE);

                break;

            case "E-mail":

                etDialogCadastroContato.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        etDataNascimentoCadastroCliente.setText(String.format(new Locale("pt", "BR"),
                "%02d/%02d/%d", dayOfMonth, month + 1, year));
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final CharSequence[] opcoes = {
                "Excluir",
                "Cancelar"
        };

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Escolha uma opção");

        builder.setItems(opcoes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (opcoes[which].equals("Excluir")) {

                    listContato.remove(position);
                    adapterListaContato.setListaContato(listContato);

                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
        return false;
    }
}
