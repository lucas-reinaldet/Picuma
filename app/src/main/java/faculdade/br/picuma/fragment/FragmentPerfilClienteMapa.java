package faculdade.br.picuma.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import faculdade.br.picuma.R;
import faculdade.br.picuma.control.ClienteControl;
import faculdade.br.picuma.model.Empresa;
import faculdade.br.picuma.util.Constantes;

public class FragmentPerfilClienteMapa extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnInfoWindowClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LatLng latLngCliente;
    private View view;
    private GoogleApiClient googleApiClient;

    private ClienteControl clienteControl;

    public ClienteControl getClienteControl() {
        if (clienteControl == null) {
            clienteControl = ClienteControl.getInstance();
        }
        return clienteControl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_mapa, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.
                getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //Tentando conexão com o Google API. Se a tentativa for bem sucessidade, o método onConnected() será chamado, senão, o método onConnectionFailed() será chamado.
        googleApiClient.connect();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.confIniciais(googleMap);

        if (getClienteControl().getListaEmpresa() != null) {
            for (Empresa empresa : getClienteControl().getListaEmpresa()) {
                LatLng localizacao = new LatLng(empresa.getEndereco().getLatitude(), empresa.getEndereco().getLongitute());
                MarkerOptions marker = new MarkerOptions();
                marker.position(localizacao);
                marker.title(empresa.getNomeFantasia());
                marker.snippet(empresa.getPublicoAlvo().equals(Constantes.UNISSEX) ? Constantes.M_ATENDIMENTO_PUBLICO_UNISSEX + Constantes.UNISSEX : Constantes.M_ATENDIMENTO_PUBLICO_ESPECIFICO + empresa.getPublicoAlvo());
                mMap.addMarker(marker);
            }
        }
    }

    private void confIniciais(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnInfoWindowClickListener(this);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.setMinZoomPreference(10);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Criteria criteria = new Criteria();
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                String provider = locationManager.getBestProvider(criteria, true);
                locationManager.requestLocationUpdates(provider, 0, 0, this);
            }
        } catch (SecurityException e) {
            Log.e(Constantes.TAG_FRAGMENT_MAPA_CLIENTE, "Error", e);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        latLngCliente = new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(provider, 0, 0, this);
        } catch (SecurityException e) {
            Log.e(Constantes.TAG_FRAGMENT_MAPA_CLIENTE, "Error", e);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        try {
            Criteria criteria = new Criteria();
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            String outro = locationManager.getBestProvider(criteria, true);
            locationManager.requestLocationUpdates(outro, 0, 0, this);
        } catch (SecurityException e) {
            Log.e(Constantes.TAG_FRAGMENT_MAPA_CLIENTE, "Error", e);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (clienteControl.getListaEmpresa() != null) {
            for (Empresa empresa : clienteControl.getListaEmpresa()) {
                if (empresa.getNomeFantasia().equals(marker.getTitle())) {
                    getClienteControl().visualizarPerfilEmpresa(getContext(), empresa);
                    break;
                }
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (pedirPermissaoDeAcesso()) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                getClienteControl().setLatLngCliente(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getClienteControl().getLatLngCliente(), 13));
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        pararConexaoComGoogleApi();
    }

    @Override
    public void onStop() {
        super.onStop();
        pararConexaoComGoogleApi();
    }

    public void pararConexaoComGoogleApi() {
        //Verificando se está conectado para então cancelar a conexão!
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Constantes.COD_SOLICITACAO_PERMISSAO_LOCALIZACAO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
            }
        }
    }

    private boolean pedirPermissaoDeAcesso() {

        if (Build.VERSION.SDK_INT >= 23) {

            String[] PERMISSIONS = {Manifest.permission.LOCATION_HARDWARE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            if (!hasPermissions(getContext(), PERMISSIONS)) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, Constantes.COD_SOLICITACAO_PERMISSAO_GALERIA_CAMERA);
            } else {
                return true;
            }
        }
        return true;
    }
}
