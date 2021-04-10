package faculdade.br.picuma.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import faculdade.br.picuma.fragment.FragmentVisualizarClassificacaoEmpresa;
import faculdade.br.picuma.fragment.FragmentVisualizarGaleriaEmpresa;
import faculdade.br.picuma.fragment.FragmentVisualizarInformacoesEmpresa;
import faculdade.br.picuma.fragment.FragmentVisualizarServicosEmpresa;

/**
 * Created by lucas on 18/04/2018.
 */

public class AdapterGerenciadorFragmentVisualizarPerfilEmpresa extends FragmentStatePagerAdapter {

    private String[] tiuloTab;

    public AdapterGerenciadorFragmentVisualizarPerfilEmpresa(FragmentManager fm, String[] tituloTab) {
        super(fm);
        this.tiuloTab = tituloTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentVisualizarInformacoesEmpresa();
            case 1:
                return new FragmentVisualizarServicosEmpresa();
            case 2:
                return new FragmentVisualizarClassificacaoEmpresa();
            case 3:
                return new FragmentVisualizarGaleriaEmpresa();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.tiuloTab.length;
    }

}
