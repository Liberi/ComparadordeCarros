package comparador.com.comparadordecarros.adapter_resultado;

/**
 * Created by Carlos on 12/08/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import comparador.com.comparadordecarros.R;


public class ListViewAdapterResultado extends ArrayAdapter<SampleItemResultado> {

    public ListViewAdapterResultado(Context context) {
        super(context, 0);
    }

    @Override
    public SampleItemResultado getItem(int position) {
        return super.getItem(getCount() - position - 1);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.row_resultado, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.teste);
        TextView categoria = (TextView) convertView.findViewById(R.id.teste2);
        TextView teste3 = (TextView) convertView.findViewById(R.id.teste3);
        TextView teste4 = (TextView) convertView.findViewById(R.id.teste4);

        TextView textView6 = (TextView) convertView.findViewById(R.id.textView6);

        ProgressBar progressBar1 = (ProgressBar) convertView.findViewById(R.id.progressBar);
        ProgressBar progressBar2 = (ProgressBar) convertView.findViewById(R.id.progressBar2);

        progressBar1.setMax((getItem(position).tag5));
        progressBar2.setMax((getItem(position).tag5));

        progressBar1.setProgress(Integer.parseInt(getItem(position).tag3));
        progressBar2.setProgress(Integer.parseInt(getItem(position).tag4));

        textView6.setText(getItem(position).tag6);
        teste4.setText(getItem(position).tag4);
        teste3.setText(getItem(position).tag3);
        categoria.setText(getItem(position).tag2);
        title.setText(getItem(position).tag);

        return convertView;
    }

}

