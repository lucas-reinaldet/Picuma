package faculdade.br.picuma.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import faculdade.br.picuma.fragment.FragmentAgendamentoConfirmados;
import faculdade.br.picuma.fragment.FragmentAgendamentoRecusados;
import faculdade.br.picuma.fragment.FragmentAgendamentoServicosRealizados;
import faculdade.br.picuma.fragment.FragmentAgendamentoSolicitacoes;

/**
 * Created by lucas on 18/04/2018.
 */

public class AdapterGerenciadorFragmentAgendamentos extends FragmentStatePagerAdapter {

    private String[] tiuloTab;

    public AdapterGerenciadorFragmentAgendamentos(FragmentManager fm, String[] tituloTab) {
        super(fm);
        this.tiuloTab = tituloTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentAgendamentoSolicitacoes();
            case 1:
                return new FragmentAgendamentoConfirmados();
            case 2:
                return new FragmentAgendamentoRecusados();
            case 3:
                return new FragmentAgendamentoServicosRealizados();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.tiuloTab.length;
    }
}
