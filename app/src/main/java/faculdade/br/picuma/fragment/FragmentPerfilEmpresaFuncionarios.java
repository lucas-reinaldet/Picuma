package faculdade.br.picuma.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaFuncionario;
import faculdade.br.picuma.control.EmpresaControl;
import faculdade.br.picuma.model.Funcionario;
import faculdade.br.picuma.util.Constantes;

import static android.os.Environment.getExternalStorageDirectory;

public class FragmentPerfilEmpresaFuncionarios extends Fragment implements AdapterView.OnItemLongClickListener {

    private EmpresaControl empresaControl;
    private ListView lvListaFuncionarioPerfilEmpresa;
    private AdapterListaFuncionario adapter;
    private AlertDialog dialogAlert;
    private ImageView ivDialogApresentacaoPreviaFotoFuncionarioCadastroEmpresa;
    private EditText etDialogCadastroNomeFuncionario;
    private Button btDialogCancelarCadastroFuncionario;
    private Button btDialogCadastrarFuncionarioMaisUm;
    private Button btDialogCadastroFuncionario;
    private String caminhoImagemCamera;
    private File arquivoImagem;
    private List<Funcionario> listaDeFuncionarioParaAdicionar;

    public EmpresaControl getEmpresaControl() {
        if (empresaControl == null) {
            empresaControl = EmpresaControl.getInstance();
        }
        return empresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = getEmpresaControl().getViewFuncionariosEmpresa(inflater, container);
        lvListaFuncionarioPerfilEmpresa = v.findViewById(R.id.lvListaFuncionarioPerfilEmpresa);
        adapter = new AdapterListaFuncionario(getActivity());
        lvListaFuncionarioPerfilEmpresa.setAdapter(adapter);
        lvListaFuncionarioPerfilEmpresa.setOnItemLongClickListener(this);
        if ((getEmpresaControl().getEmpresa().getListaFuncionarios() != null) && (getEmpresaControl().getEmpresa().getListaFuncionarios().size() >= 1)) {
            updateList(getEmpresaControl().getEmpresa().getListaFuncionarios());
        }

        ImageButton ibAdicionarFuncionarioPerfilEmpresaFuncionarios = v.findViewById(R.id.ibAdicionarFuncionarioPerfilEmpresaFuncionarios);
        ibAdicionarFuncionarioPerfilEmpresaFuncionarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.dialog_funcionario_cadastro, null);
                listaDeFuncionarioParaAdicionar = new ArrayList<>();
                ivDialogApresentacaoPreviaFotoFuncionarioCadastroEmpresa = mView.findViewById(R.id.ivDialogApresentacaoPreviaFotoFuncionarioCadastroEmpresa);
                etDialogCadastroNomeFuncionario = mView.findViewById(R.id.etDialogCadastroNomeFuncionario);

                btDialogCancelarCadastroFuncionario = mView.findViewById(R.id.btDialogCancelarCadastroFuncionario);
                btDialogCadastrarFuncionarioMaisUm = mView.findViewById(R.id.btDialogCadastrarFuncionarioMaisUm);
                btDialogCadastroFuncionario = mView.findViewById(R.id.btDialogCadastroFuncionario);

                ivDialogApresentacaoPreviaFotoFuncionarioCadastroEmpresa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pedirPermissaoDeAcesso();
                    }
                });

                btDialogCancelarCadastroFuncionario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAlert.dismiss();
                    }
                });

                btDialogCadastrarFuncionarioMaisUm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etDialogCadastroNomeFuncionario.getText().toString() != null && !etDialogCadastroNomeFuncionario.getText().toString().isEmpty()) {
                            Funcionario funcionario = new Funcionario();
                            funcionario.setNomeFuncionario(etDialogCadastroNomeFuncionario.getText().toString());

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
                                funcionario.setFotoFuncionario(fileArray);
                                arquivoImagem = null;
                            }
                            funcionario.setIdEmpresa(getEmpresaControl().getEmpresa().getIdEmpresa());
                            listaDeFuncionarioParaAdicionar.add(funcionario);
                            etDialogCadastroNomeFuncionario.setText("");
                            ivDialogApresentacaoPreviaFotoFuncionarioCadastroEmpresa.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_camera, null));
                        } else {
                            Toast.makeText(getContext(), Constantes.M_AT_NOME_FUNCIONARIO_VAZIO, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btDialogCadastroFuncionario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etDialogCadastroNomeFuncionario.getText().toString() != null && !etDialogCadastroNomeFuncionario.getText().toString().isEmpty()) {
                            Funcionario funcionario = new Funcionario();
                            funcionario.setNomeFuncionario(etDialogCadastroNomeFuncionario.getText().toString());

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
                                funcionario.setFotoFuncionario(fileArray);
                                arquivoImagem = null;
                            }
                            funcionario.setIdEmpresa(getEmpresaControl().getEmpresa().getIdEmpresa());
                            listaDeFuncionarioParaAdicionar.add(funcionario);

                            if (listaDeFuncionarioParaAdicionar.size() > 0) {
                                if (getEmpresaControl().cadastrarFuncionarioEmpresa(listaDeFuncionarioParaAdicionar)) {
                                    adapter.setListaFuncionario(getEmpresaControl().getEmpresa().getListaFuncionarios());
                                } else {
                                    Toast.makeText(getContext(), Constantes.M_FALHA_AO_CADASTRAR_FUNCIONARIO_EMPRESA, Toast.LENGTH_SHORT).show();
                                }
                            }
                            dialogAlert.dismiss();
                        } else {
                            Toast.makeText(getContext(), Constantes.M_AT_NOME_FUNCIONARIO_VAZIO, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.setView(mView);
                dialogAlert = alertDialog.create();
                dialogAlert.show();
            }
        });

        return v;
    }

    private void updateList(List<Funcionario> listaFuncionario) {
        adapter.setListaFuncionario(listaFuncionario);
    }


    private void pedirPermissaoDeAcesso() {

        if (Build.VERSION.SDK_INT >= 23) {

            String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            if (!hasPermissions(getContext(), PERMISSIONS)) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, Constantes.COD_SOLICITACAO_PERMISSAO_GALERIA_CAMERA);
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
                    Toast.makeText(getContext(), Constantes.M_INF_PERMISSAO_ACESSO_GALERIA, Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    private void adicionarImagem() {

        final CharSequence[] opcoes = {
                "Pegar uma imagem",
                "Tirar uma foto",
                "Cancelar"
        };

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

        MediaScannerConnection.scanFile(getContext(), new String[]{caminhoImagemCamera}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {

                Log.i("Arquivo", "" + caminhoImagemCamera);

            }
        });
        arquivoImagem = new File(caminhoImagemCamera);
        ivDialogApresentacaoPreviaFotoFuncionarioCadastroEmpresa.setImageBitmap(BitmapFactory.decodeFile(caminhoImagemCamera));
    }

    private void receberFotoDaGaleria(Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContext().getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            String foto = cursor.getString(0);

            arquivoImagem = new File(foto);
            ivDialogApresentacaoPreviaFotoFuncionarioCadastroEmpresa.setImageBitmap(BitmapFactory.decodeFile(foto));
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final Funcionario funcionario = getEmpresaControl().getEmpresa().getListaFuncionarios().get(position);

        final CharSequence[] opcoes = {
                "Excluir",
                "Cancelar"
        };

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Escolha uma opção");

        builder.setItems(opcoes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (opcoes[which].equals("Excluir")) {
                    if (getEmpresaControl().desativarFuncionarioEmpresa(funcionario)) {
                        getEmpresaControl().getEmpresa().getListaFuncionarios().remove(position);
                        updateList(getEmpresaControl().getEmpresa().getListaFuncionarios());
                    } else {
                        Toast.makeText(getContext(), Constantes.M_FALHA_AO_EXCLUIR_FUNCIONARIO_EMPRESA, Toast.LENGTH_SHORT).show();
                    }

                } else {

                    dialog.dismiss();
                }
            }
        });
        builder.show();

        return false;
    }
}
