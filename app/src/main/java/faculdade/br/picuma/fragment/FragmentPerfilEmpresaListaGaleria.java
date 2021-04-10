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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaGaleriaEmpresa;
import faculdade.br.picuma.control.EmpresaControl;
import faculdade.br.picuma.model.FotoGaleria;
import faculdade.br.picuma.model.Galeria;
import faculdade.br.picuma.model.GrupoServico;
import faculdade.br.picuma.model.Servico;
import faculdade.br.picuma.util.Constantes;

import static android.os.Environment.getExternalStorageDirectory;

public class FragmentPerfilEmpresaListaGaleria extends Fragment implements AdapterView.OnItemClickListener {

    private GridView gridViewCategoriaGaleriaPerfilEmpresa;
    private AdapterListaGaleriaEmpresa adapter;
    private EmpresaControl empresaControl;
    private ImageButton ibAdicionarFotoPerfilEmpresaGaleria;
    private AlertDialog dialogAlert;
    private Spinner spinnerDialogGrupoServicoGaleria;
    private ArrayList<Servico> listaServicoParaCadastro;
    private Button btDialogCancelarCadastroFotoGaleria;
    private ImageButton ibDialogAdicionarFotoAntesGaleria;
    private ImageButton ibDialogAdicionarFotoDepoisGaleria;
    private Button btDialogCadastroFotoGaleria;
    private String caminhoImagemCamera;
    private File arquivoImagemAntes;
    private int quemSolicitouFoto;
    private File arquivoImagemDepois;
    private GrupoServico grupoServicoParaCadastro;

    public EmpresaControl getEmpresaControl() {
        if (empresaControl == null) {
            empresaControl = EmpresaControl.getInstance();
        }
        return empresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = getEmpresaControl().getViewListaGaleriaEmpresa(inflater, container);

        gridViewCategoriaGaleriaPerfilEmpresa = v.findViewById(R.id.gridViewCategoriaGaleriaPerfilEmpresa);
        getEmpresaControl().setListadoServicoPrestado(false);
        getEmpresaControl().setListadoFotoGaleria(false);
        getEmpresaControl().setApresentandoFotosGaleria(false);
        getEmpresaControl().setListadoProfissionaisPorServico(false);
        adapter = new AdapterListaGaleriaEmpresa(getContext());
        gridViewCategoriaGaleriaPerfilEmpresa.setAdapter(adapter);
        gridViewCategoriaGaleriaPerfilEmpresa.setOnItemClickListener(this);

        if (getEmpresaControl().getEmpresa().getListaGaleria() != null && getEmpresaControl().getEmpresa().getListaGaleria().size() > 0) {
            adapter.setListaGaleria(getEmpresaControl().getEmpresa().getListaGaleria());
        }

        ibAdicionarFotoPerfilEmpresaGaleria = v.findViewById(R.id.ibAdicionarFotoPerfilEmpresaGaleria);

        ibAdicionarFotoPerfilEmpresaGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.dialog_foto_galeria_cadastro, null);

                spinnerDialogGrupoServicoGaleria = mView.findViewById(R.id.spinnerDialogGrupoServicoGaleria);
                ibDialogAdicionarFotoAntesGaleria = mView.findViewById(R.id.ibDialogAdicionarFotoAntesGaleria);
                ibDialogAdicionarFotoDepoisGaleria = mView.findViewById(R.id.ibDialogAdicionarFotoDepoisGaleria);
                btDialogCancelarCadastroFotoGaleria = mView.findViewById(R.id.btDialogCancelarCadastroFotoGaleria);
                btDialogCadastroFotoGaleria = mView.findViewById(R.id.btDialogCadastroFotoGaleria);

                final ArrayAdapter<GrupoServico> adapterSpinnerGrupo = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getEmpresaControl().getListaGrupoServicoPrestado());
                ArrayAdapter<GrupoServico> spinnerAdapter = adapterSpinnerGrupo;
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDialogGrupoServicoGaleria.setAdapter(spinnerAdapter);

                spinnerDialogGrupoServicoGaleria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        grupoServicoParaCadastro = getEmpresaControl().getListaGrupoServicoPrestado().get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                ibDialogAdicionarFotoAntesGaleria.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quemSolicitouFoto = 1;
                        pedirPermissaoDeAcesso();
                    }
                });


                ibDialogAdicionarFotoDepoisGaleria.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quemSolicitouFoto = 2;
                        pedirPermissaoDeAcesso();
                    }
                });
                btDialogCancelarCadastroFotoGaleria.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAlert.dismiss();
                    }
                });

                btDialogCadastroFotoGaleria.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (arquivoImagemAntes != null) {
                            if (arquivoImagemDepois != null) {
                                Galeria galeria = new Galeria();
                                galeria.setGrupoServico(grupoServicoParaCadastro);

                                FotoGaleria fotoGaleria = new FotoGaleria();
                                int tam = (int) arquivoImagemAntes.length();
                                byte[] fileArray = new byte[tam];
                                FileInputStream fis = null;
                                try {
                                    fis = new FileInputStream(arquivoImagemAntes);
                                    fis.read(fileArray, 0, tam);
                                } catch (Exception e) {
                                    System.err.println(e);
                                }
                                fotoGaleria.setFotoAntesGaleria(fileArray);
                                arquivoImagemAntes = null;

                                tam = (int) arquivoImagemDepois.length();
                                byte[] fileArray2 = new byte[tam];
                                FileInputStream fis2 = null;
                                try {
                                    fis2 = new FileInputStream(arquivoImagemDepois);
                                    fis2.read(fileArray2, 0, tam);
                                } catch (Exception e) {
                                    System.err.println(e);
                                }
                                fotoGaleria.setFotoDepoisGaleria(fileArray2);
                                arquivoImagemDepois = null;

                                List<FotoGaleria> listaFotoGaleria = new ArrayList<>();
                                listaFotoGaleria.add(fotoGaleria);
                                galeria.setListaFotoGaleria(listaFotoGaleria);

                                if (getEmpresaControl().cadastrarFotoGaleria(galeria)) {
                                    adapter.setListaGaleria(getEmpresaControl().getEmpresa().getListaGaleria());
                                    dialogAlert.dismiss();
                                } else {
                                    Toast.makeText(getContext(), Constantes.M_FALHA_AO_CADASTRAR_FOTO, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), Constantes.M_AT_FOTO_DEPOIS_VAZIO, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), Constantes.M_AT_FOTO_ANTES_VAZIO, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        FragmentManager fragmentManagerAux = getFragmentManager();
        FragmentTransaction fragmentTransactionAux = fragmentManagerAux.beginTransaction();
        fragmentTransactionAux.replace(R.id.container_perfil_empresa_servicos, new FragmentListaGrupoServicoPrestadoPerfilEmpresa(), "grupos_servicos_prestados");
        fragmentTransactionAux.commit();

        getEmpresaControl().setParamParaListagemDeFotoEmpresa(getEmpresaControl().getEmpresa().getListaGaleria().get(position));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_perfil_empresa_galeria, new FragmentListaFotoGaleriaPerfilEmpresa(), "fotos_galeria");
        fragmentTransaction.commit();
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
            File arquivoImagem = new File(caminhoImagemCamera);
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

        if (quemSolicitouFoto == 1) {
            arquivoImagemAntes = new File(caminhoImagemCamera);
            ibDialogAdicionarFotoAntesGaleria.setImageBitmap(BitmapFactory.decodeFile(caminhoImagemCamera));
            caminhoImagemCamera = "";

        } else if (quemSolicitouFoto == 2) {
            arquivoImagemDepois = new File(caminhoImagemCamera);
            ibDialogAdicionarFotoDepoisGaleria.setImageBitmap(BitmapFactory.decodeFile(caminhoImagemCamera));
            caminhoImagemCamera = "";
        }

    }

    private void receberFotoDaGaleria(Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContext().getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            String foto = cursor.getString(0);

            if (quemSolicitouFoto == 1) {
                arquivoImagemAntes = new File(foto);
                ibDialogAdicionarFotoAntesGaleria.setImageBitmap(BitmapFactory.decodeFile(foto));

            } else if (quemSolicitouFoto == 2) {
                arquivoImagemDepois = new File(foto);
                ibDialogAdicionarFotoDepoisGaleria.setImageBitmap(BitmapFactory.decodeFile(foto));
            }
        }
    }
}
