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
 * TODO: Replace the implementation with code for your data type.
 */
public class ToDoItemRecyclerViewAdapter extends RecyclerView.Adapter<ToDoItemRecyclerViewAdapter.ViewHolder> {
    private final List<ToDoListItem> mValues;

    public ToDoItemRecyclerViewAdapter(List<ToDoListItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(ToDoFragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
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

    @Override
    public int getItemCount() {
        return mValues.size();
    }

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