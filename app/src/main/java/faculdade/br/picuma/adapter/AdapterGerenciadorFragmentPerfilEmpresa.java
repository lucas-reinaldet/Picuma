package faculdade.br.picuma.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import faculdade.br.picuma.fragment.FragmentPerfilEmpresaClassificacao;
import faculdade.br.picuma.fragment.FragmentPerfilEmpresaFuncionarios;
import faculdade.br.picuma.fragment.FragmentPerfilEmpresaGaleria;
import faculdade.br.picuma.fragment.FragmentPerfilEmpresaInformacoes;
import faculdade.br.picuma.fragment.FragmentPerfilEmpresaServicos;

/**
 * Created by lucas on 18/04/2018.
 */

public class AdapterGerenciadorFragmentPerfilEmpresa extends FragmentStatePagerAdapter {

    private String[] tiuloTab;

    public AdapterGerenciadorFragmentPerfilEmpresa(FragmentManager fm, String[] tituloTab) {
        super(fm);
        this.tiuloTab = tituloTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentPerfilEmpresaInformacoes();
            case 1:
                return new FragmentPerfilEmpresaFuncionarios();
            case 2:
                return new FragmentPerfilEmpresaServicos();
            case 3:
                return new FragmentPerfilEmpresaGaleria();
            case 4:
                return new FragmentPerfilEmpresaClassificacao();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.tiuloTab.length;
    }

}
