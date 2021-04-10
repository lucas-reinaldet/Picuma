package faculdade.br.picuma.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaFotoGaleriaEmpresa;
import faculdade.br.picuma.control.EmpresaControl;
import faculdade.br.picuma.model.FotoGaleria;
import faculdade.br.picuma.util.Constantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListaFotoGaleriaPerfilEmpresa extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private GridView gridViewFotoGaleriaEmpresa;
    private AdapterListaFotoGaleriaEmpresa adapter;
    private FragmentManager fragmentManager;

    private EmpresaControl empresaControl;

    public EmpresaControl getEmpresaControl() {
        if (empresaControl == null) {
            empresaControl = EmpresaControl.getInstance();
        }
        return empresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_foto_galeria_empresa, container, false);
        gridViewFotoGaleriaEmpresa = v.findViewById(R.id.gridViewFotoGaleriaEmpresa);

        if (getEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria() != null && getEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria().size() > 0) {
            getEmpresaControl().setListadoFotoGaleria(true);
            adapter = new AdapterListaFotoGaleriaEmpresa(getContext(), getEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria());
            gridViewFotoGaleriaEmpresa.setAdapter(adapter);
            gridViewFotoGaleriaEmpresa.setOnItemClickListener(this);
            gridViewFotoGaleriaEmpresa.setOnItemLongClickListener(this);
        } else {
            Toast.makeText(getContext(), Constantes.M_AT_SEM_FOTO_GALERIA, Toast.LENGTH_SHORT).show();
            getEmpresaControl().setListadoFotoGaleria(false);
            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_perfil_empresa_galeria, new FragmentPerfilEmpresaListaGaleria(), "galeria");
            fragmentTransaction.commit();
        }
        return v;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<byte[]> listaBytes = new ArrayList<>();

        listaBytes.add(getEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria().get(position).getFotoAntesGaleria());
        listaBytes.add(getEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria().get(position).getFotoDepoisGaleria());

        for (FotoGaleria fotoGaleria : getEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria()) {
            if (getEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria().get(position).getIdFotoGaleria() != fotoGaleria.getIdFotoGaleria()) {
                listaBytes.add(fotoGaleria.getFotoAntesGaleria());
                listaBytes.add(fotoGaleria.getFotoDepoisGaleria());
            }
        }

        if (listaBytes != null && listaBytes.size() > 0) {
            getEmpresaControl().setListaByteFotos(listaBytes);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_perfil_empresa_galeria, new FragmentVisualizadorFotoGaleriaPerfilEmpresa(), "apresentar_fotos");
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
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
                    FotoGaleria fotoGaleria = getEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria().get(position);

                    if (getEmpresaControl().removerFotoGaleria(fotoGaleria)) {
                        if (getEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria().size() == 1) {
                            getEmpresaControl().getEmpresa().getListaGaleria().remove(getEmpresaControl().getParamParaListagemDeFotoEmpresa());
                            fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container_perfil_empresa_galeria, new FragmentPerfilEmpresaListaGaleria(), "galeria");
                            fragmentTransaction.commit();
                        } else {
                            getEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria().remove(position);
                            adapter.setListaFotoGaleria(getEmpresaControl().getParamParaListagemDeFotoEmpresa().getListaFotoGaleria());
                        }
                    } else {
                        Toast.makeText(getContext(), Constantes.M_FALHA_AO_EXCLUIR_FOTO_EMPRESA, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
        return true;
    }
}

