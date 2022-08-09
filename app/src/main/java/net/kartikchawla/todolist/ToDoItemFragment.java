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
    /**
     * Class variables
     * dataModel is an object of class DataModel
     * mColumnCount is a constant that defines how many columns does this fragment has.
     */
    private final int mColumnCount = 1;
    private DataModel dataModel;

    /**
     * -     * Mandatory empty constructor for the fragment manager to instantiate the
     * -     * fragment (e.g. upon screen orientation changes).
     * -
     */
    public ToDoItemFragment() {
    }

    /**
     * This method is called when ever this view appears.
     * User to display the list of saved items.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataModel = new DataModel(context);
        Cursor data = dataModel.readData(getActivity().getSharedPreferences("ToDoListUser", Context.MODE_PRIVATE));
        ToDoListContent.makeToDoList(data);
    }


    /**
     * This method is used to populate the fragment.
     * -    * Inbuilt android method
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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