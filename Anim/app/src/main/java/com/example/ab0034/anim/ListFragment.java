package com.example.ab0034.anim;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends android.support.v4.app.ListFragment {
    ChessPieceListener chessPieceListener;


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.pieces)));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            chessPieceListener = (ChessPieceListener) context;
        } catch (Exception e) {
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        chessPieceListener.onchessSelected(position);
    }

    public interface ChessPieceListener {
        public void onchessSelected(int index);

    }
}
