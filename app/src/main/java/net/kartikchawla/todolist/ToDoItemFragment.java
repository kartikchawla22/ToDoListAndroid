package net.kartikchawla.todolist;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.kartikchawla.todolist.models.DataModel;
import net.kartikchawla.todolist.toDoList.ToDoListContent;

/**
 * A fragment representing a list of Items.
 */
public class ToDoItemFragment extends Fragment {

    private final int mColumnCount = 1;
    private DataModel dataModel;

    /**
     * -     * Mandatory empty constructor for the fragment manager to instantiate the
     * -     * fragment (e.g. upon screen orientation changes).
     * -
     */
    public ToDoItemFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        refreshData(context);
    }

    public void refreshData(Context context) {
        dataModel = new DataModel(context);
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