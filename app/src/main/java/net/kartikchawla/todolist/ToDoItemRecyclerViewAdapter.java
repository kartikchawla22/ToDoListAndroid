package net.kartikchawla.todolist;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.kartikchawla.todolist.databinding.ToDoFragmentItemBinding;
import net.kartikchawla.todolist.toDoList.ToDoListContent.ToDoListItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ToDoListItem}.
 */
public class ToDoItemRecyclerViewAdapter extends RecyclerView.Adapter<ToDoItemRecyclerViewAdapter.ViewHolder> {
    /**
     * Class variables
     * All values stored in the DB are added to the array mValues.
     */
    private final List<ToDoListItem> mValues;

    /**
     * Used to populate mValues with all the items.
     *
     * @param items
     */
    public ToDoItemRecyclerViewAdapter(List<ToDoListItem> items) {
        mValues = items;
    }

    /**
     * Defines and returns a {@link androidx.recyclerview.widget.RecyclerView.ViewHolder}
     * Used to inflate the view in which particular data will be shown.
     *
     * @param parent
     * @param viewType
     * @return ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(ToDoFragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    /**
     * User to bind the viewHolder with textViews present on the screen.
     * Populates the views using data from data stored in DB.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(position + 1));
        holder.mContentView.setText(mValues.get(position).content);
        holder.mDateTimeView.setText(mValues.get(position).dateTime);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailsViewIntent = new Intent(view.getContext(), DetailsViewActivity.class);
                detailsViewIntent.putExtra("id", mValues.get(position).id);
                view.getContext().startActivity(detailsViewIntent);
            }
        });

    }

    /**
     * @return size of mValues
     */

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * View holder class that holds all the text views needs to be displayed on the screen.
     */

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mDateTimeView;
        public ToDoListItem mItem;

        public ViewHolder(ToDoFragmentItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            mDateTimeView = binding.timeStamp;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}