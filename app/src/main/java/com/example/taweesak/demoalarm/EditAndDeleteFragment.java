package com.example.taweesak.demoalarm;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class EditAndDeleteFragment extends Fragment {

    private String idString;

    private EditText notiEditText, dayEditText, monthEditText, hourEditText, minuteEditText;

    private String tag = "4NovV2";

    public EditAndDeleteFragment() {
        // Required empty public constructor
    }

    public static EditAndDeleteFragment editAndDeleteInstante(String idString) {
        EditAndDeleteFragment editAndDeleteFragment = new EditAndDeleteFragment();
        Bundle bundle = new Bundle();
        bundle.putString("MyAlarm", idString);
        editAndDeleteFragment.setArguments(bundle);
        return editAndDeleteFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        idString = getArguments().getString("MyAlarm");
        Log.d("4NovV2", "id at Edit ==> " + idString);

//        Create Toolbar
        createToolbar();

//        Show Data
        showData();

    } // Main Method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemEditList) {

            editValueSQLite();

            return true;
        }

        if (item.getItemId() == R.id.itemDeleteList) {

            deleteValueSQLite();

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void deleteValueSQLite() {
        // todo
        try {

            SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                    Context.MODE_PRIVATE,null);
            sqLiteDatabase.delete("alarmTABLE","id="+idString,null);

            back();

        }catch (Exception e){
            Log.d(tag,"e delete ==> "+e.toString());
        }

    }

    private void editValueSQLite() {

        // todo edit data
        String notiString = notiEditText.getText().toString().trim();
        String dayString = dayEditText.getText().toString().trim();
        String monthString = monthEditText.getText().toString().trim();
        String hourString = hourEditText.getText().toString().trim();
        String minuteString = minuteEditText.getText().toString().trim();

        try {

            SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                    Context.MODE_PRIVATE, null);

            // Update Data

//            Edit by SQL Command
            /*sqLiteDatabase.execSQL("UPDATE alarmTABLE SET NotiTime = " + "'"+notiString +"',"+
                    " Day = "+"'"+dayString+"',"+
                    " Month = "+"'"+monthString+"',"+
                    " Hour = "+"'"+hourString+"',"+
                    " Minute = "+"'"+minuteString+"'"+
                    " WHERE id="+"'"+idString+"'" );*/

//            Edit by ContainValue
            ContentValues contentValues = new ContentValues();
            contentValues.put("NotiTime", notiString);
            contentValues.put("Day", dayString);
            contentValues.put("Month", monthString);
            contentValues.put("Hour", hourString);
            contentValues.put("Minute", minuteString);
            sqLiteDatabase.update("alarmTABLE",contentValues,"id=" + idString,null);


            back();


        } catch (Exception e) {
            Log.d(tag, "e editValue ==> " + e.toString());
        }

    }

    private void back() {
        getActivity().getSupportFragmentManager().popBackStack();// back to first page
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_edit, menu);


    }

    private void showData() {
        notiEditText = getView().findViewById(R.id.editTextNoti);
        dayEditText = getView().findViewById(R.id.editTextday);
        monthEditText = getView().findViewById(R.id.editTextMonth);
        hourEditText = getView().findViewById(R.id.editTextHour);
        minuteEditText = getView().findViewById(R.id.editTextMinute);

        try {
            // Connect Database
            SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                    Context.MODE_PRIVATE,
                    null);

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM alarmTABLE WHERE id=" + idString, null);
            cursor.moveToFirst();

            String[] strings = new String[cursor.getColumnCount()];

            for (int i = 0; i < strings.length; i++) {

                strings[i] = cursor.getString(i);

                Log.d(tag, "string[" + i + "]======= >" + strings[i]);


            }// for

            // init data
            notiEditText.setText(strings[1]);
            dayEditText.setText(strings[2]);
            monthEditText.setText(strings[3]);
            hourEditText.setText(strings[4]);
            minuteEditText.setText(strings[5]);


        } catch (Exception e) {
            Log.d(tag, "e Error ==> " + e.toString());
        }


    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarEdit);

        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Edit and Delete");
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Edit and Delete");

        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        setHasOptionsMenu(true); //ขออนุญเปิด menu


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_and_delete, container, false);
    }

}
