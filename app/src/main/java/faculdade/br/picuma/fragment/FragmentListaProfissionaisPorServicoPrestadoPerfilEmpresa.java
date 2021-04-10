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
import android.widget.ListView;

import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaFuncionario;
import faculdade.br.picuma.control.EmpresaControl;
import faculdade.br.picuma.model.Funcionario;

public class FragmentListaProfissionaisPorServicoPrestadoPerfilEmpresa extends Fragment implements AdapterView.OnItemClickListener {

    private FragmentManager fragmentManager;
    private ListView lvListaProfissionaisPorServicosPrestados;
    private AdapterListaFuncionario adapter;
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

        View v = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_lista_profissionais_por_servico_prestado, container, false);
        getEmpresaControl().setListadoProfissionaisPorServico(true);
        fragmentManager = getFragmentManager();
        lvListaProfissionaisPorServicosPrestados = v.findViewById(R.id.lvListaProfissionaisPorServicosPrestados);
        adapter = new AdapterListaFuncionario(getContext());
        lvListaProfissionaisPorServicosPrestados.setAdapter(adapter);
        updateList(getEmpresaControl().getParamParaListagemDeProfissional());
        lvListaProfissionaisPorServicosPrestados.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final CharSequence[] opcoes = {
                "Desassociar",
                "Cancelar"
        };
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Escolha uma opção");

        builder.setItems(opcoes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (opcoes[which].equals("Desassociar")) {
                    if (getEmpresaControl().desassociarFuncionarioServico(getEmpresaControl().getParamDeListagemFuncionarioServicoPrestado(), getEmpresaControl().getParamParaListagemDeProfissional().get(position))) {
                        for (int i = 0; i < getEmpresaControl().getParamDeListagemFuncionarioServicoPrestado().getListaIdFuncionario().size(); i++) {
                            if (getEmpresaControl().getParamDeListagemFuncionarioServicoPrestado().getListaIdFuncionario().get(i) == getEmpresaControl().getParamParaListagemDeProfissional().get(position).getIdFuncionario()) {
                                getEmpresaControl().getParamDeListagemFuncionarioServicoPrestado().getListaIdFuncionario().remove(i);
                                break;
                            }
                        }
                        getEmpresaControl().getParamParaListagemDeProfissional().remove(position);
                        if (getEmpresaControl().getParamParaListagemDeProfissional().size() == 0) {
                            getEmpresaControl().setListadoProfissionaisPorServico(false);
                            fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container_perfil_empresa_servicos, new FragmentListaServicosPrestadosPerfilEmpresa(), "servicos_prestados");
                            fragmentTransaction.commit();
                        } else {
                            updateList(getEmpresaControl().getParamParaListagemDeProfissional());
                        }
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void updateList(List<Funcionario> dados) {
        adapter.setListaFuncionario(dados);
    }
}
