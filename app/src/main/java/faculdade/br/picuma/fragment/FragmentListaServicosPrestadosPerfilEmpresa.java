package faculdade.br.picuma.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaServicoPrestado;
import faculdade.br.picuma.control.EmpresaControl;
import faculdade.br.picuma.model.Funcionario;
import faculdade.br.picuma.model.ServicoPrestado;
import faculdade.br.picuma.util.Constantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListaServicosPrestadosPerfilEmpresa extends Fragment implements AdapterView.OnItemClickListener {

    private FragmentManager fragmentManager;
    private ListView lvListaServicosPrestados;
    private ArrayList<ServicoPrestado> listaServicoPrestado;
    private AdapterListaServicoPrestado adapter;
    private EmpresaControl empresaControl;

    public EmpresaControl getEmpresaControl() {
        if (empresaControl == null) {
            empresaControl = EmpresaControl.getInstance();
        }
        return empresaControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_lista_servicos_prestados, container, false);

        if (getEmpresaControl().getEmpresa().getListaServicoPrestado() != null && getEmpresaControl().getEmpresa().getListaServicoPrestado() != null) {
            listaServicoPrestado = new ArrayList<>();
            for (ServicoPrestado servicoPrestado : getEmpresaControl().getEmpresa().getListaServicoPrestado()) {
                if (servicoPrestado.getServico().getGrupoServico().getIdGrupoServico() == getEmpresaControl().getParamParaListagemDeServicosPrestados().getIdGrupoServico()) {
                    listaServicoPrestado.add(servicoPrestado);
                }
            }

            if (listaServicoPrestado != null && listaServicoPrestado.size() > 0) {
                getEmpresaControl().setListadoServicoPrestado(true);
                fragmentManager = getFragmentManager();
                lvListaServicosPrestados = v.findViewById(R.id.lvListaServicosPrestados);
                adapter = new AdapterListaServicoPrestado(getActivity());
                lvListaServicosPrestados.setAdapter(adapter);
                updateList(listaServicoPrestado);
                lvListaServicosPrestados.setOnItemClickListener(this);
            } else {
                Toast.makeText(v.getContext(), Constantes.M_AT_SEM_SERVICO_PRESTADO_PARA_ESSE_GRUPO, Toast.LENGTH_LONG).show();
                getEmpresaControl().setListadoServicoPrestado(false);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_perfil_empresa_servicos, new FragmentListaGrupoServicoPrestadoPerfilEmpresa(), "area_atuacao");
                fragmentTransaction.commit();
            }
        }
        return v;
    }

    private void updateList(List<ServicoPrestado> dados) {
        adapter.setListaServicoPrestado(dados);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<Funcionario> listaFuncionarioServico = new ArrayList<>();
        for (Funcionario funcionario : getEmpresaControl().getEmpresa().getListaFuncionarios()) {
            for (Integer idFuncionario : listaServicoPrestado.get(position).getListaIdFuncionario()) {
                if (funcionario.getIdFuncionario() == idFuncionario) {
                    listaFuncionarioServico.add(funcionario);
                }
            }
        }
        if (listaFuncionarioServico.size() > 0) {
            getEmpresaControl().setParamDeListagemFuncionarioServicoPrestado(listaServicoPrestado.get(position));
            getEmpresaControl().setParamParaListagemDeProfissional(listaFuncionarioServico);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_perfil_empresa_servicos, new FragmentListaProfissionaisPorServicoPrestadoPerfilEmpresa(), "Empresas");
            fragmentTransaction.commit();
        } else {
            Toast.makeText(getContext(), Constantes.M_AT_SEM_PROFISSIONAL_PARA_SERVICO_PRESTADO, Toast.LENGTH_SHORT).show();
        }
    }
}
