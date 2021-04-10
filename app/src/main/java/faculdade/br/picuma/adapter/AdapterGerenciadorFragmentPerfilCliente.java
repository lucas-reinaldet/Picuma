package faculdade.br.picuma.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import faculdade.br.picuma.fragment.FragmentPerfilClienteCategorias;
import faculdade.br.picuma.fragment.FragmentPerfilClienteListaEmpresas;
import faculdade.br.picuma.fragment.FragmentPerfilClienteMapa;

/**
 * Created by lucas on 18/04/2018.
 */

public class AdapterGerenciadorFragmentPerfilCliente extends FragmentStatePagerAdapter {

    private String[] tiuloTab;

    public AdapterGerenciadorFragmentPerfilCliente(FragmentManager fm, String[] tituloTab) {
        super(fm);
        this.tiuloTab = tituloTab;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return new FragmentPerfilClienteCategorias();
            case 1:
                return new FragmentPerfilClienteMapa();
            case 2:
                return new FragmentPerfilClienteListaEmpresas();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.tiuloTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return this.tiuloTab[position];
    }


}
