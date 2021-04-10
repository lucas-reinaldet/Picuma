package faculdade.br.picuma.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import faculdade.br.picuma.R;
import faculdade.br.picuma.control.EmpresaControl;

public class FragmentPerfilEmpresaServicos extends Fragment {

    private FragmentManager fragmentManager;
    private EmpresaControl empresaControl;

    public EmpresaControl getEmpresaControl() {
        if (empresaControl == null) {
            empresaControl = EmpresaControl.getInstance();
        }
        return empresaControl;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = getEmpresaControl().getViewServicosEmpresa(inflater, container);
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_perfil_empresa_servicos, new FragmentListaGrupoServicoPrestadoPerfilEmpresa(), "galeria");
        fragmentTransaction.commit();
        return v;
    }
}
