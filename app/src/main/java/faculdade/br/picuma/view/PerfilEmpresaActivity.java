package faculdade.br.picuma.view;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.concurrent.ExecutionException;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterGerenciadorFragmentPerfilEmpresa;
import faculdade.br.picuma.control.EmpresaControl;
import faculdade.br.picuma.dialog.DialogTimePicker;
import faculdade.br.picuma.fragment.FragmentListaFotoGaleriaPerfilEmpresa;
import faculdade.br.picuma.fragment.FragmentListaGrupoServicoPrestadoPerfilEmpresa;
import faculdade.br.picuma.fragment.FragmentListaServicosPrestadosPerfilEmpresa;
import faculdade.br.picuma.fragment.FragmentPerfilEmpresaListaGaleria;
import faculdade.br.picuma.webService.SolicitarListaHorarioMarcadoEmpresa;

public class PerfilEmpresaActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TabLayout tabLayoutPerfilEmpresa;
    private ViewPager viewPagerPerfilEmpresa;
    private DrawerLayout drawerPerfilEmpresa;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationViewPerfilEmpresa;

    private ImageView ivEmpresaPerfil;
    private TextView tvNomePerfilEmpresa;

    private TabLayout tabLayoutVisualizarPerfilEmpresa;
    private ViewPager viewPagerVisualizarPerfilEmpresa;
    private int[] iconTab = {
            R.drawable.ic_perfil,
            R.drawable.ic_funcionarios,
            R.drawable.ic_servicos,
            R.drawable.ic_galeria,
            R.drawable.ic_classificacao
    };

    private static PerfilEmpresaActivity instance;
    private int paramParaEscolhaHorario;
    private EmpresaControl empresaControl;
    private FragmentManager fragmentManager;

    public static PerfilEmpresaActivity getInstance() {
        if (instance == null) {
            instance = new PerfilEmpresaActivity();
        }
        return instance;
    }

    public EmpresaControl getEmpresaControl() {
        if (empresaControl == null) {
            empresaControl = EmpresaControl.getInstance();
        }
        return empresaControl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getEmpresaControl().setPerfilEmpresaActivity(this);
        setContentView(R.layout.activity_perfil_empresa);
        getInstance();
        drawerPerfilEmpresa = findViewById(R.id.drawerPerfilEmpresa);
        navigationViewPerfilEmpresa = findViewById(R.id.navigationViewPerfilEmpresa);

        mToggle = new ActionBarDrawerToggle(this, drawerPerfilEmpresa, R.string.opem, R.string.close);
        drawerPerfilEmpresa.addDrawerListener(mToggle);
        setupDrawerContent(navigationViewPerfilEmpresa);

        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayoutPerfilEmpresa = findViewById(R.id.tabLayoutPerfilEmpresa);
        viewPagerPerfilEmpresa = findViewById(R.id.viewPagerPerfilEmpresa);

        viewPagerPerfilEmpresa.setAdapter(new AdapterGerenciadorFragmentPerfilEmpresa(getSupportFragmentManager(), getResources().getStringArray(R.array.title_tab_perfil_empresa)));
        tabLayoutPerfilEmpresa.setupWithViewPager(viewPagerPerfilEmpresa);

        for (int i = 0; i < tabLayoutPerfilEmpresa.getTabCount(); i++) {
            tabLayoutPerfilEmpresa.getTabAt(i).setIcon(iconTab[i]);
        }

        View vHeader = navigationViewPerfilEmpresa.getHeaderView(0);
        ivEmpresaPerfil = vHeader.findViewById(R.id.ivEmpresaPerfil);
        tvNomePerfilEmpresa = vHeader.findViewById(R.id.tvNomePerfilEmpresa);

        if (getEmpresaControl().getEmpresa().getLogoEmpresa() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(getEmpresaControl().getEmpresa().getLogoEmpresa(), 0, getEmpresaControl().getEmpresa().getLogoEmpresa().length);
            ivEmpresaPerfil.setImageBitmap(bitmap);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(getEmpresaControl().getEmpresa().getAreaAtuacao().getFotoAreaAtuacao(), 0, getEmpresaControl().getEmpresa().getAreaAtuacao().getFotoAreaAtuacao().length);
            ivEmpresaPerfil.setImageBitmap(bitmap);
        }
        tvNomePerfilEmpresa.setText(getEmpresaControl().getEmpresa().getNomeFantasia());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectItemDrawer(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navAgendamentoPerfilEmpresa:
                irParaAgendamentos();
                break;
            case R.id.navLogOutPerfilEmpresa:
                getEmpresaControl().destroyInstance();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
    }

    private void irParaAgendamentos() {
        SolicitarListaHorarioMarcadoEmpresa solicitarListaHorarioMarcadoEmpresa = new SolicitarListaHorarioMarcadoEmpresa();
        try {
            getEmpresaControl().setListaHorarioMarcado(solicitarListaHorarioMarcadoEmpresa.execute(getEmpresaControl().getEmpresa().getIdEmpresa()).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        drawerPerfilEmpresa.closeDrawer(GravityCompat.START);
        getEmpresaControl().irParaAgendamentos(getApplicationContext());
    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (getEmpresaControl().isListadoFotoGaleria()) {
            if (getEmpresaControl().isApresentandoFotosGaleria()) {
                fragmentManager = getSupportFragmentManager();
                getEmpresaControl().setApresentandoFotosGaleria(false);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_perfil_empresa_galeria, new FragmentListaFotoGaleriaPerfilEmpresa(), "fotos_galeria");
                fragmentTransaction.commit();
            } else {
                fragmentManager = getSupportFragmentManager();
                getEmpresaControl().setListadoFotoGaleria(false);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_perfil_empresa_galeria, new FragmentPerfilEmpresaListaGaleria(), "galeria");
                fragmentTransaction.commit();
            }
        } else if (getEmpresaControl().isListadoServicoPrestado()) {
            if (getEmpresaControl().getListadoProfissionaisPorServico()) {
                fragmentManager = getSupportFragmentManager();
                getEmpresaControl().setListadoProfissionaisPorServico(false);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_perfil_empresa_servicos, new FragmentListaServicosPrestadosPerfilEmpresa(), "servicos_prestados");
                fragmentTransaction.commit();
            } else {
                fragmentManager = getSupportFragmentManager();
                getEmpresaControl().setListadoServicoPrestado(false);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_perfil_empresa_servicos, new FragmentListaGrupoServicoPrestadoPerfilEmpresa(), "grupos_servicos_prestados");
                fragmentTransaction.commit();
            }
        } else {
            getEmpresaControl().destroyInstance();
            super.onBackPressed();
        }
    }

    public void abrirDialogHorario(int param) {
        paramParaEscolhaHorario = param;
        DialogTimePicker dialogTimePicker = new DialogTimePicker();
        dialogTimePicker.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        getEmpresaControl().getFragmentPerfilEmpresaInformacoes().apresentarHorarioEscolhido(hourOfDay, minute, paramParaEscolhaHorario);
    }
}
