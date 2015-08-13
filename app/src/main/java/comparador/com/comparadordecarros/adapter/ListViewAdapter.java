package comparador.com.comparadordecarros.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import comparador.com.comparadordecarros.R;
import comparador.com.comparadordecarros.helper.ImageLoader;

public class ListViewAdapter extends ArrayAdapter<SampleItem> {

    public ListViewAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public SampleItem getItem(int position) {
        return super.getItem(getCount() - position - 1);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.row, null);
        }
        TextView modelo = (TextView) convertView.findViewById(R.id.modelo);
        TextView ano = (TextView) convertView.findViewById(R.id.ano);
        TextView motor = (TextView) convertView.findViewById(R.id.motor);

        ImageView img = (ImageView) convertView.findViewById(R.id.thumb);

        ImageLoader loader = new ImageLoader();

        img.setImageBitmap(loader.loadImages(getItem(position).tag4));
        motor.setText(getItem(position).tag3);
        ano.setText(String.valueOf(getItem(position).tag2));
        modelo.setText(getItem(position).tag);

        return convertView;
    }

}

