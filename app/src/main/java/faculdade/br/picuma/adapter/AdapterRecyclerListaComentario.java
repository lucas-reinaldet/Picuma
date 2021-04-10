package faculdade.br.picuma.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import faculdade.br.picuma.R;
import faculdade.br.picuma.control.VisualizarEmpresaControl;
import faculdade.br.picuma.model.Comentario;

public class AdapterRecyclerListaComentario extends RecyclerView.Adapter<AdapterRecyclerListaComentario.ViewHolder> {

    private Context mContext;
    private List<Comentario> listaComentario;

    private VisualizarEmpresaControl visualizarEmpresaControl;

    public VisualizarEmpresaControl getVisualizarEmpresaControl() {
        if (visualizarEmpresaControl == null) {
            visualizarEmpresaControl = VisualizarEmpresaControl.getInstance();
        }
        return visualizarEmpresaControl;
    }

    public AdapterRecyclerListaComentario(Context mContext, List<Comentario> lista) {
        this.mContext = mContext;
        this.listaComentario = lista;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Comentario comentario;

        private final ImageView ivFotoPerfilClienteComentarioEmpresa;
        private final TextView tvNomeClienteComentarioEmpresa;
        private final ImageButton ibDenunciaComentarioEmpresa;
        private final TextView tvComentarioClienteEmpresa;

        private VisualizarEmpresaControl visualizarEmpresaControl;

        public VisualizarEmpresaControl getVisualizarEmpresaControl() {
            if (visualizarEmpresaControl == null) {
                visualizarEmpresaControl = VisualizarEmpresaControl.getInstance();
            }
            return visualizarEmpresaControl;
        }

        public ViewHolder(final View view) {
            super(view);

            ivFotoPerfilClienteComentarioEmpresa = view.findViewById(R.id.ivFotoPerfilClienteComentarioEmpresa);
            tvNomeClienteComentarioEmpresa = view.findViewById(R.id.tvNomeClienteComentarioEmpresa);
            ibDenunciaComentarioEmpresa = view.findViewById(R.id.ibDenunciaComentarioEmpresa);
            tvComentarioClienteEmpresa = view.findViewById(R.id.tvComentarioClienteEmpresa);
            ibDenunciaComentarioEmpresa.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.ibDenunciaComentarioEmpresa:
                    this.denunciarComentario(v);
                    break;
            }
        }
        private void denunciarComentario(View v) {
            getVisualizarEmpresaControl().denunciarComentario(v, comentario);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_comentario_lista, parent, false);
        ViewHolder mViewHolder = new ViewHolder(v);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.comentario = listaComentario.get(position);

        if (listaComentario.get(position).getCliente().getFotoCliente() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(listaComentario.get(position).getCliente().getFotoCliente(), 0 , listaComentario.get(position).getCliente().getFotoCliente().length);
            holder.ivFotoPerfilClienteComentarioEmpresa.setImageBitmap(bitmap);
        } else {
            holder.ivFotoPerfilClienteComentarioEmpresa.setImageResource(R.drawable.imagem_padrao_sem_foto);
        }

        holder.tvNomeClienteComentarioEmpresa.setText(listaComentario.get(position).getCliente().getNomeCliente());
        holder.tvComentarioClienteEmpresa.setText(listaComentario.get(position).getComentario());

    }

    @Override
    public int getItemCount() {
        return listaComentario.size();
    }
}
