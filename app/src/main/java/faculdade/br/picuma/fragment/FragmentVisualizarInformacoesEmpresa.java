package faculdade.br.picuma.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import faculdade.br.picuma.R;
import faculdade.br.picuma.adapter.AdapterListaContato;
import faculdade.br.picuma.adapter.AdapterListaHorarioEmpresa;
import faculdade.br.picuma.control.VisualizarEmpresaControl;
import faculdade.br.picuma.util.Constantes;

public class FragmentVisualizarInformacoesEmpresa extends Fragment implements OnMapReadyCallback {

    private VisualizarEmpresaControl visualizarEmpresaControl;
    private AdapterListaContato adapterListaContato;
    private AdapterListaHorarioEmpresa adapterListaHorario;
    private TextView tvAreaAtuacaoVisualizarInfoEmpresa;
    private GoogleMap mMap;
    private LinearLayout llDescricaoEmpresaVisualizarPerfilEmpresa;
    private TextView tvDescricaoEmpresaVisualizarPerfilEmpresa;

    public VisualizarEmpresaControl getVisualizarEmpresaControl() {

        if (visualizarEmpresaControl == null) {
            visualizarEmpresaControl = VisualizarEmpresaControl.getInstance();
        }
        return visualizarEmpresaControl;
    }

    private TextView tvPublicoAlvoVisualizarInfoEmpresa;
    private ImageView ivImagemLogoVisualizarEmpresa;
    private TextView tvNomeVisualizarInfoEmpresa;
    private TextView tvCpfCnpjVisualizarInfoEmpresa;
    private ListView lvListaContatoVisualizarInfoEmpresa;
    private ListView lvListaHorarioVisualizarInfoEmpresa;
    private TextView tvEnderecoLogradouroVisualizarInfoEmpresa;
    private TextView tvEnderecoNumeroVisualizarInfoEmpresa;
    private TextView tvEnderecoComplementoVisualizarInfoEmpresa;
    private TextView tvEnderecoBairroVisualizarInfoEmpresa;
    private TextView tvEnderecoCidadeVisualizarInfoEmpresa;
    private TextView tvEnderecoEstadoVisualizarInfoEmpresa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = getVisualizarEmpresaControl().getViewInfoEmpresa(inflater, container);

        if (getVisualizarEmpresaControl().getPrimeiraExecucaoInfoEmpresa()) {

            tvPublicoAlvoVisualizarInfoEmpresa = v.findViewById(R.id.tvPublicoAlvoVisualizarInfoEmpresa);
            ivImagemLogoVisualizarEmpresa = v.findViewById(R.id.ivImagemLogoVisualizarEmpresa);
            tvNomeVisualizarInfoEmpresa = v.findViewById(R.id.tvNomeVisualizarInfoEmpresa);
            tvCpfCnpjVisualizarInfoEmpresa = v.findViewById(R.id.tvCpfCnpjVisualizarInfoEmpresa);
            tvAreaAtuacaoVisualizarInfoEmpresa = v.findViewById(R.id.tvAreaAtuacaoVisualizarInfoEmpresa);
            lvListaContatoVisualizarInfoEmpresa = v.findViewById(R.id.lvListaContatoVisualizarInfoEmpresa);
            lvListaHorarioVisualizarInfoEmpresa = v.findViewById(R.id.lvListaHorarioVisualizarInfoEmpresa);
            tvEnderecoLogradouroVisualizarInfoEmpresa = v.findViewById(R.id.tvEnderecoLogradouroVisualizarInfoEmpresa);
            tvEnderecoNumeroVisualizarInfoEmpresa = v.findViewById(R.id.tvEnderecoNumeroVisualizarInfoEmpresa);
            tvEnderecoComplementoVisualizarInfoEmpresa = v.findViewById(R.id.tvEnderecoComplementoVisualizarInfoEmpresa);
            tvEnderecoBairroVisualizarInfoEmpresa = v.findViewById(R.id.tvEnderecoBairroVisualizarInfoEmpresa);
            tvEnderecoCidadeVisualizarInfoEmpresa = v.findViewById(R.id.tvEnderecoCidadeVisualizarInfoEmpresa);
            tvEnderecoEstadoVisualizarInfoEmpresa = v.findViewById(R.id.tvEnderecoEstadoVisualizarInfoEmpresa);
            llDescricaoEmpresaVisualizarPerfilEmpresa = v.findViewById(R.id.llDescricaoEmpresaVisualizarPerfilEmpresa);
            tvDescricaoEmpresaVisualizarPerfilEmpresa = v.findViewById(R.id.tvDescricaoEmpresaVisualizarPerfilEmpresa);

            SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
                    .findFragmentById(R.id.fragmentMapaVisualizacaoEmpresa);
            mapFragment.
                    getMapAsync(this);

            apresentarDadosIniciais();
        }

        return v;
    }

    private void apresentarDadosIniciais() {

        if (getVisualizarEmpresaControl().getEmpresa().getLogoEmpresa() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(getVisualizarEmpresaControl().getEmpresa().getLogoEmpresa(), 0, getVisualizarEmpresaControl().getEmpresa().getLogoEmpresa().length);
            ivImagemLogoVisualizarEmpresa.setImageBitmap(bitmap);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(getVisualizarEmpresaControl().getEmpresa().getAreaAtuacao().getFotoAreaAtuacao(), 0, getVisualizarEmpresaControl().getEmpresa().getAreaAtuacao().getFotoAreaAtuacao().length);
            ivImagemLogoVisualizarEmpresa.setImageBitmap(bitmap);
        }
        tvNomeVisualizarInfoEmpresa.setText(getVisualizarEmpresaControl().getEmpresa().getNomeFantasia());
        tvPublicoAlvoVisualizarInfoEmpresa.setText(getVisualizarEmpresaControl().getEmpresa().getPublicoAlvo().equals(Constantes.UNISSEX) ? Constantes.M_ATENDIMENTO_PUBLICO_UNISSEX + Constantes.UNISSEX : Constantes.M_ATENDIMENTO_PUBLICO_ESPECIFICO + getVisualizarEmpresaControl().getEmpresa().getPublicoAlvo());
        tvCpfCnpjVisualizarInfoEmpresa.setText(getVisualizarEmpresaControl().getEmpresa().getCnpjEmpresa() != null ?
                Constantes.M_CNPJ + getVisualizarEmpresaControl().getEmpresa().getCnpjEmpresa() : Constantes.M_CPF +
                getVisualizarEmpresaControl().getEmpresa().getCpfEmpresa());
        tvAreaAtuacaoVisualizarInfoEmpresa.setText(getVisualizarEmpresaControl().getEmpresa().getAreaAtuacao().getAreaAtuacao());

        adapterListaContato = new AdapterListaContato(getContext());
        adapterListaHorario = new AdapterListaHorarioEmpresa(getContext());

        lvListaContatoVisualizarInfoEmpresa.setAdapter(adapterListaContato);
        lvListaHorarioVisualizarInfoEmpresa.setAdapter(adapterListaHorario);
        adapterListaContato.setListaContato(getVisualizarEmpresaControl().getEmpresa().getListaContato());
        adapterListaHorario.setListaHorarioEmpresa(getVisualizarEmpresaControl().getEmpresa().getListaHorarioEmpresa());

        tvEnderecoBairroVisualizarInfoEmpresa.setText(getVisualizarEmpresaControl().getEmpresa().getEndereco().getBairro());
        tvEnderecoCidadeVisualizarInfoEmpresa.setText(getVisualizarEmpresaControl().getEmpresa().getEndereco().getCidade());
        tvEnderecoEstadoVisualizarInfoEmpresa.setText(getVisualizarEmpresaControl().getEmpresa().getEndereco().getEstado());
        tvEnderecoLogradouroVisualizarInfoEmpresa.setText(getVisualizarEmpresaControl().getEmpresa().getEndereco().getLogradouro());
        tvEnderecoNumeroVisualizarInfoEmpresa.setText(getVisualizarEmpresaControl().getEmpresa().getEndereco().getNumero());

        if (getVisualizarEmpresaControl().getEmpresa().getEndereco().getComplemento() != null &&
                !getVisualizarEmpresaControl().getEmpresa().getEndereco().getComplemento().isEmpty()) {
            tvEnderecoComplementoVisualizarInfoEmpresa.setTextSize(14);
            tvEnderecoComplementoVisualizarInfoEmpresa.setText(getVisualizarEmpresaControl().getEmpresa().getEndereco().getComplemento());
        }

        if (getVisualizarEmpresaControl().getEmpresa().getDescricaoEmpresa() != null &&
                !getVisualizarEmpresaControl().getEmpresa().getDescricaoEmpresa().isEmpty()) {

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 10, 0, 0);
            llDescricaoEmpresaVisualizarPerfilEmpresa.setLayoutParams(layoutParams);
            tvDescricaoEmpresaVisualizarPerfilEmpresa.setText(getVisualizarEmpresaControl().getEmpresa().getDescricaoEmpresa());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.setMinZoomPreference(13);
        LatLng latLng = new LatLng(getVisualizarEmpresaControl().getEmpresa().getEndereco().getLatitude(), getVisualizarEmpresaControl().getEmpresa().getEndereco().getLatitude());
        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);
        marker.title(getVisualizarEmpresaControl().getEmpresa().getNomeFantasia());
        marker.snippet(getVisualizarEmpresaControl().getEmpresa().getPublicoAlvo().equals(Constantes.UNISSEX) ? Constantes.M_ATENDIMENTO_PUBLICO_UNISSEX + Constantes.UNISSEX : Constantes.M_ATENDIMENTO_PUBLICO_ESPECIFICO + getVisualizarEmpresaControl().getEmpresa().getPublicoAlvo());
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }
}

