package faculdade.br.picuma.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import faculdade.br.picuma.R;
import faculdade.br.picuma.model.Funcionario;

public class AdapterListaFuncionario extends BaseAdapter {

    private List<Funcionario> funcionarios = new ArrayList<>();
    private Context context;

    public AdapterListaFuncionario(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return funcionarios.size();
    }

    @Override
    public Funcionario getItem(int position) {
        return funcionarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return funcionarios.get(position).getIdFuncionario();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lista_funcionario_empresa, parent, false);
        TextView etListaNomeFuncionario = view.findViewById(R.id.tvListaNomeFuncionario);
        CircleImageView ivListaFotoFuncionario = view.findViewById(R.id.ivListaFotoFuncionario);
        Funcionario funcionario = funcionarios.get(position);
        etListaNomeFuncionario.setText(funcionario.getNomeFuncionario());
        if (funcionario.getFotoFuncionario() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(funcionario.getFotoFuncionario(), 0, funcionario.getFotoFuncionario().length);
            ivListaFotoFuncionario.setImageBitmap(bitmap);
        } else {
            ivListaFotoFuncionario.setImageResource(R.drawable.imagem_padrao_sem_foto);
        }
        return view;
    }

    public void setListaFuncionario(List<Funcionario> listaFuncionario) {
        this.funcionarios = listaFuncionario;
        notifyDataSetChanged();
    }
}
