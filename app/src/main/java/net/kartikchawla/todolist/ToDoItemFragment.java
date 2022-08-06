package net.kartikchawla.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.kartikchawla.todolist.models.DataModel;
import net.kartikchawla.todolist.toDoList.ToDoListContent;

/**
 * A fragment representing a list of Items.
 */
public class ToDoItemFragment extends Fragment {
    private DataModel dataModel;


    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private  String str;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ToDoItemFragment newInstance(int columnCount) {
        ToDoItemFragment fragment = new ToDoItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    /**
     -     * Mandatory empty constructor for the fragment manager to instantiate the
     -     * fragment (e.g. upon screen orientation changes).
     -     */
   public ToDoItemFragment() {
           }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataModel =  new DataModel(context);
        Cursor data = dataModel.readData();
        ToDoListContent.makeToDoList(data);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.to_do_fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new ToDoItemRecyclerViewAdapter(ToDoListContent.ITEMS));
        }
        return view;
    }
}