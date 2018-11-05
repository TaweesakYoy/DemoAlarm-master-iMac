package com.example.taweesak.demoalarm;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ShowListFragment extends Fragment {


    public ShowListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //  Create recyclerview

        createRecyclerview();

//        Create Toolbar ====command /
        createToolbar();

    }// Main Method / option+command + M

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarListAlarm);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Show All Alarm");

        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack(); // ถอยหลังกลับ
            }
        });


    }

    private void createRecyclerview() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewListAlarm);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        try { // ทำ try catch เพราะ moveToFirst เวลาเจอไม่มีฐานข้อมูลจะ Error
            // ถ้าไม่ทำ try catch ก็ต้องเขียนเช็คว่ามีฐานข้อมูลหรือไม่

            //การเข้าถึงข้อมูล
            SQLiteDatabase sqLiteDatabase = getActivity()
                    .openOrCreateDatabase(MyOpenHelper.DATABASE_NAME, Context.MODE_PRIVATE,
                            null);

            // Cursor
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM alarmTABLE", null);


            Log.d("4NovV1", "cursor.count ==>" + cursor.getCount());


            cursor.moveToFirst();

            ArrayList<String> notiStringArrayList = new ArrayList<>();
            ArrayList<String> dayStringArrayList = new ArrayList<>();
            ArrayList<String> monthiStringArrayList = new ArrayList<>();
            ArrayList<String> hourStringArrayList = new ArrayList<>();
            ArrayList<String> minuteStringArrayList = new ArrayList<>();
            final ArrayList<String> idStringArrayList = new ArrayList<>();


            for (int i = 0; i < cursor.getCount(); i++) { // getCount คือ cursor ไปเช็ค database จากบันทัดบนแล้ว sqLiteDatabase.rawQuery

                idStringArrayList.add(cursor.getString(0));
                notiStringArrayList.add(cursor.getString(1));
                dayStringArrayList.add(cursor.getString(2));
                monthiStringArrayList.add(cursor.getString(3));
                hourStringArrayList.add(cursor.getString(4));
                minuteStringArrayList.add(cursor.getString(5));

                Log.d("4NovV1", "noti [" + i + "}==>" + notiStringArrayList.get(i));

                cursor.moveToNext();


            }// for

            Log.d("4NovV1", "noti ==>" + notiStringArrayList.toString());
            Log.d("4NovV1", "day ==>" + dayStringArrayList.toString());
            Log.d("4NovV1", "month ==>" + monthiStringArrayList.toString());
            Log.d("4NovV1", "HH ==>" + hourStringArrayList.toString());
            Log.d("4NovV1", "MM ==>" + minuteStringArrayList.toString());

            // Call RecyclerView Adapter
            ListAlarmAdapter listAlarmAdapter = new ListAlarmAdapter(getActivity(),
                    notiStringArrayList,
                    dayStringArrayList,
                    monthiStringArrayList,
                    hourStringArrayList,
                    minuteStringArrayList,
                    new OnClickListItem() {
                        @Override
                        public void OnClickListItem(View view, int position) {
                            Log.d("4NovV2", "id Send ==> " + idStringArrayList.get(position));

//                            Replace Fragment and put Value

                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.contentMainFragment,
                                            EditAndDeleteFragment.editAndDeleteInstante(idStringArrayList.get(position)))
                                    .addToBackStack(null) // Back to after page open
                                    .commit();




                        }
                    });

            recyclerView.setAdapter(listAlarmAdapter);

            cursor.close(); // Close Database

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("4NovV1", "e==>" + e.toString());
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_list, container, false);
    }

}
