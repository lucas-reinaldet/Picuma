package faculdade.br.picuma.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.control.ClienteControl;
import faculdade.br.picuma.model.Empresa;
import faculdade.br.picuma.model.Favoritos;
import faculdade.br.picuma.util.Constantes;

public class AdapterRecyclerListaEmpresa extends RecyclerView.Adapter<AdapterRecyclerListaEmpresa.ViewHolder> {

    private Context mContext;
    private List<Empresa> listaEmpresa;
    private ClienteControl clienteControl;

    public ClienteControl getClienteControl() {
        if (clienteControl == null) {
            clienteControl = ClienteControl.getInstance();
        }
        return clienteControl;
    }

    public AdapterRecyclerListaEmpresa(Context mContext, List<Empresa> lista) {

        this.mContext = mContext;
        this.listaEmpresa = lista;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int currentItem;
        public Empresa empresa;
        public ImageView imageViewListaEmpresa;
        public TextView tvNomeListaEmpresa;
        public TextView tvEnderecoListaEmpresa;
        public ImageView ibFavoritoListaEmpresa;

        private ClienteControl clienteControl;
        private boolean isFavorito;


        public ClienteControl getClienteControl() {

            if (clienteControl == null) {

                clienteControl = ClienteControl.getInstance();

            }

            return clienteControl;
        }

        public ViewHolder(final View view) {
            super(view);

            imageViewListaEmpresa = view.findViewById(R.id.imageViewListaEmpresa);
            tvNomeListaEmpresa = view.findViewById(R.id.tvNomeListaEmpresa);
            tvEnderecoListaEmpresa = view.findViewById(R.id.tvEnderecoListaEmpresa);
            ibFavoritoListaEmpresa = view.findViewById(R.id.ibFavoritoListaEmpresa);

            imageViewListaEmpresa.setOnClickListener(this);
            tvNomeListaEmpresa.setOnClickListener(this);
            tvEnderecoListaEmpresa.setOnClickListener(this);
            ibFavoritoListaEmpresa.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.imageViewListaEmpresa:
                    this.visualizarPerfilEmpresa(v);
                    break;
                case R.id.tvNomeListaEmpresa:
                    this.visualizarPerfilEmpresa(v);
                    break;
                case R.id.tvEnderecoListaEmpresa:
                    this.visualizarPerfilEmpresa(v);
                    break;
                case R.id.ibFavoritoListaEmpresa:
                    this.adicionarAoFavoritos(v);
                    break;
            }
        }

        private void adicionarAoFavoritos(View v) {
            Favoritos favorito = new Favoritos();

            if (getClienteControl().getCliente().getListaFavoritos() != null) {
                for (Favoritos favoritos : getClienteControl().getCliente().getListaFavoritos()) {
                    if (favoritos.getIdEmpresa() == empresa.getIdEmpresa()) {
                        favorito = favoritos;
                        isFavorito = true;
                    }
                }
            } else {
                getClienteControl().getCliente().setListaFavoritos(new ArrayList<Favoritos>());
            }

            if (isFavorito) {
                if (getClienteControl().desfavoritarEmpresa(favorito)) {
                    ibFavoritoListaEmpresa.setImageResource(R.drawable.ic_favorito_false);
                } else {
                    Toast.makeText(v.getContext(), Constantes.M_FALHA_AO_RETIRAR_DE_FAVORITOS_EMPRESA, Toast.LENGTH_LONG).show();
                }
            } else {
                favorito.setIdEmpresa(empresa.getIdEmpresa());
                favorito.setIdCliente(getClienteControl().getCliente().getIdCliente());
                if (getClienteControl().adicionarEmpresaAosFavoritos(favorito)) {
                    ibFavoritoListaEmpresa.setImageResource(R.drawable.ic_favorito_true);
                } else {
                    Toast.makeText(v.getContext(), Constantes.M_FALHA_AO_ADICIONAR_EMPRESA_FAVORITOS, Toast.LENGTH_LONG).show();
                }
            }
        }

        private void visualizarPerfilEmpresa(View v) {
            getClienteControl().visualizarPerfilEmpresa(v.getContext(), empresa);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_empresa_lista, parent, false);

        ViewHolder mViewHolder = new ViewHolder(v);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.empresa = listaEmpresa.get(position);

        if (listaEmpresa.get(position).getLogoEmpresa() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(listaEmpresa.get(position).getLogoEmpresa(), 0, listaEmpresa.get(position).getLogoEmpresa().length);
            holder.imageViewListaEmpresa.setImageBitmap(bitmap);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(listaEmpresa.get(position).getAreaAtuacao().getFotoAreaAtuacao(), 0, listaEmpresa.get(position).getAreaAtuacao().getFotoAreaAtuacao().length);
            holder.imageViewListaEmpresa.setImageBitmap(bitmap);
        }
        holder.tvNomeListaEmpresa.setText(listaEmpresa.get(position).getNomeFantasia());


        String endereco = listaEmpresa.get(position).getEndereco().getLogradouro() + " nº " + listaEmpresa.get(position).getEndereco().getNumero() + " - " + listaEmpresa.get(position).getEndereco().getCidade();

        holder.tvEnderecoListaEmpresa.setText(endereco);
        boolean favorito = false;
        if (getClienteControl().getCliente().getListaFavoritos() != null) {
            for (Favoritos favoritos : getClienteControl().getCliente().getListaFavoritos()) {
                if (favoritos.getIdEmpresa() == listaEmpresa.get(position).getIdEmpresa()) {
                    favorito = true;
                    break;
                }
            }
            if (favorito) {
                holder.ibFavoritoListaEmpresa.setImageResource(R.drawable.ic_favorito_true);
            } else {
                holder.ibFavoritoListaEmpresa.setImageResource(R.drawable.ic_favorito_false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listaEmpresa.size();
    }
}
