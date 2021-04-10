package faculdade.br.picuma.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdapterVisualizadorFotosGaleria extends PagerAdapter {

    private Context context;
    private List<byte[]> listaFotoGaleria;

    public AdapterVisualizadorFotosGaleria(Context context, List<byte[]> listaFotoGaleria) {
        this.context = context;
        this.listaFotoGaleria = listaFotoGaleria;
    }

    @Override
    public int getCount() {
        return listaFotoGaleria.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Bitmap bitmap = BitmapFactory.decodeByteArray(listaFotoGaleria.get(position), 0, listaFotoGaleria.get(position).length);
        imageView.setImageBitmap(bitmap);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }
}
