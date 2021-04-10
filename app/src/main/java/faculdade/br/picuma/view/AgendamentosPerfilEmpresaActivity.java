package faculdade.br.picuma.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterGerenciadorFragmentAgendamentos;
import faculdade.br.picuma.util.Constantes;

public class AgendamentosPerfilEmpresaActivity extends AppCompatActivity {

    private TabLayout tabLayoutAgendamentos;
    private ViewPager viewPagerAgendamentos;
    private int[] iconTab= {
            R.drawable.ic_calendar_question,
            R.drawable.ic_calendar_clock,
            R.drawable.ic_calendar_remove,
            R.drawable.ic_calendar_check
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamentos_perfil_empresa);
        setTitle(Constantes.NOME_ACTIVITY_AGENDAMENTO);
        tabLayoutAgendamentos = findViewById(R.id.tabLayoutAgendamentos);
        viewPagerAgendamentos = findViewById(R.id.viewPagerAgendamentos);
        viewPagerAgendamentos.setAdapter(new AdapterGerenciadorFragmentAgendamentos(getSupportFragmentManager(), getResources().getStringArray(R.array.title_tab_agendamentos)));
        tabLayoutAgendamentos.setupWithViewPager(viewPagerAgendamentos);

        for (int i = 0; i < tabLayoutAgendamentos.getTabCount(); i++) {
            tabLayoutAgendamentos.getTabAt(i).setIcon(iconTab[i]);
        }
    }
}
