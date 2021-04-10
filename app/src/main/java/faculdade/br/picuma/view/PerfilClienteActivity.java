package faculdade.br.picuma.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.util.concurrent.ExecutionException;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterGerenciadorFragmentPerfilCliente;
import faculdade.br.picuma.control.ClienteControl;
import faculdade.br.picuma.fragment.FragmentListaAreaAtuacao;
import faculdade.br.picuma.fragment.FragmentListaGrupoServicoPerfilCliente;
import faculdade.br.picuma.model.Login;
import faculdade.br.picuma.webService.SolicitarListaHorarioMarcadoCliente;

public class PerfilClienteActivity extends AppCompatActivity {

    private TabLayout tabLayoutPerfilCliente;
    private ViewPager viewPagerPerfilCliente;
    private ImageView ivViewClientePerfil;
    private TextView tvNomeClientePerfilCliente;
    private DrawerLayout drawerPerfilCliente;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationViewPerfilCliente;
    private Login dadosUsuario;
    private FragmentManager fragmentManager;
    private ClienteControl clienteControl;


    public ClienteControl getClienteControl() {
        if (clienteControl == null) {
            clienteControl = ClienteControl.getInstance();
        }
        return clienteControl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_perfil_cliente);
        drawerPerfilCliente = findViewById(R.id.drawerPerfilCliente);
        navigationViewPerfilCliente = findViewById(R.id.navigationViewPerfilCliente);
        fragmentManager = getSupportFragmentManager();

        getClienteControl();

        mToggle = new ActionBarDrawerToggle(this, drawerPerfilCliente, R.string.opem, R.string.close);
        drawerPerfilCliente.addDrawerListener(mToggle);
        setupDrawerContent(navigationViewPerfilCliente);

        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayoutPerfilCliente = findViewById(R.id.tabLayoutPerfilCliente);
        viewPagerPerfilCliente = findViewById(R.id.viewPagerPerfilCliente);

        viewPagerPerfilCliente.setAdapter(new AdapterGerenciadorFragmentPerfilCliente(getSupportFragmentManager(), getResources().getStringArray(R.array.title_tab_perfil_cliente)));
        tabLayoutPerfilCliente.setupWithViewPager(viewPagerPerfilCliente);

        View vHeader = navigationViewPerfilCliente.getHeaderView(0);

        ivViewClientePerfil = vHeader.findViewById(R.id.ivViewClientePerfil);
        tvNomeClientePerfilCliente = vHeader.findViewById(R.id.tvNomeClientePerfilCliente);

        if (getClienteControl().getCliente().getFotoCliente() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(getClienteControl().getCliente().getFotoCliente(), 0, getClienteControl().getCliente().getFotoCliente().length);
            ivViewClientePerfil.setImageBitmap(bitmap);
        } else {
            ivViewClientePerfil.setImageResource(R.drawable.imagem_padrao_sem_foto);
        }

        tvNomeClientePerfilCliente.setText(getClienteControl().getCliente().getNomeCliente());
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

            case R.id.navAgendamentoPerfilCliente:
                irParaAgendamentos();
                break;
            case R.id.navFavoritos:
                startActivity(new Intent(getApplicationContext(), FavoritosActivity.class));
                break;
            case R.id.navLogOutPerfilCliente:
                getClienteControl().destroyCliente();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
    }

    private void irParaAgendamentos() {
        SolicitarListaHorarioMarcadoCliente solicitarListaHorarioMarcadoCliente = new SolicitarListaHorarioMarcadoCliente();
        try {
            getClienteControl().setListaHorarioMarcado(solicitarListaHorarioMarcadoCliente.execute(getClienteControl().getCliente().getIdCliente()).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(getApplicationContext(), AgendamentosPerfilClienteActivity.class));
    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerPerfilCliente.isDrawerOpen(GravityCompat.START)) {
            drawerPerfilCliente.closeDrawer(GravityCompat.START);
        } else if (getClienteControl().isListadoGrupoServico()) {
            if (getClienteControl().isListadoEmpresaPorCategoria()) {
                getClienteControl().setListadoEmpresaPorCategoria(false);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_perfil_cliente_lista_por_categoria, new FragmentListaGrupoServicoPerfilCliente(), "grupo_servico");
                fragmentTransaction.commit();
            } else {
                getClienteControl().setListadoGrupoServico(false);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_perfil_cliente_lista_por_categoria, new FragmentListaAreaAtuacao(), "area_atuacao");
                fragmentTransaction.commit();
            }
        } else {
            super.onBackPressed();
        }
    }
}
