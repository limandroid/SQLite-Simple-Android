package garin.artemiy.sqlitesimple.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import garin.artemiy.sqlitesimple.R;
import garin.artemiy.sqlitesimple.example.operator.RecordsDAO;
import garin.artemiy.sqlitesimple.library.model.FTSModel;

/**
 * Author: Artemiy Garin
 * Date: 07.04.13
 */
public class MainAdapter extends ArrayAdapter<FTSModel> {

    private Context context;
    private RecordsDAO recordsDAO;

    public MainAdapter(Context context) {
        super(context, R.layout.main_layout);
        this.context = context;
        recordsDAO = new RecordsDAO(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.record_item, null);
        }

        FTSModel ftsModel = getItem(position);
        TextView recordText = (TextView) view.findViewById(R.id.recordTextView);
        recordText.setText(recordsDAO.readWhere("_id", ftsModel.getId()).getRecordText());

        return view;
    }

}
