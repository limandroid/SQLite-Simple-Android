package garin.artemiy.simple.example;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import garin.artemiy.simple.R;
import garin.artemiy.simple.example.adapter.MainCursorAdapter;
import garin.artemiy.simple.example.model.Record;
import garin.artemiy.simple.example.operator.RecordsOperator;

import java.util.GregorianCalendar;

/**
 * author: Artemiy Garin
 * date: 11.12.12
 */
public class MainActivity extends ListActivity {

    private RecordsOperator recordsOperator;
    private Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recordsOperator = new RecordsOperator(this);
        setContentView(R.layout.main);

        cursor = recordsOperator.selectCursorDescFromTable();
        MainCursorAdapter cursorAdapter = new MainCursorAdapter(this, cursor);
        setListAdapter(cursorAdapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                recordsOperator.delete(id);
                updateAdapter();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAdapter();
    }

    private void updateAdapter() {
        MainCursorAdapter cursorAdapter = (MainCursorAdapter) getListAdapter();
        cursorAdapter.changeCursor(recordsOperator.selectCursorDescFromTable());
        cursorAdapter.notifyDataSetChanged();
    }

    private Record generateRecord() {
        Record record = new Record();
        record.setDateOfPublication(GregorianCalendar.getInstance().getTimeInMillis());
        record.setPublished(true);
        return record;
    }

    @SuppressWarnings("unused")
    public void onClickAddRecord(View view) {
        recordsOperator.create(generateRecord());

        updateAdapter();
    }

    @SuppressWarnings("unused")
    public void onClickDeleteAllRecords(View view) {
        recordsOperator.deleteAll();
        updateAdapter();
    }

    @SuppressWarnings("unused")
    public void onClickUpdateLastRow(View view) {
        recordsOperator.update(recordsOperator.getLastRowId(), generateRecord());
        updateAdapter();
    }

}