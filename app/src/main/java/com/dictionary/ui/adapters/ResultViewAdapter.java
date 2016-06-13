package com.dictionary.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dictionary.Model.Word;
import com.dictionary.R;

import java.util.List;

/**
 * Created by winhtaikaung on 3/24/16.
 */
public class ResultViewAdapter extends RecyclerView.Adapter<ResultViewAdapter.ViewHolder> {

    private List<Word> mResults;

    public ResultViewAdapter(List<Word> results) {
        mResults = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.result_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Word word = mResults.get(position);
        // Set item views based on the data model
        TextView textResult = holder.textResult;
        TextView textResultType = holder.textResultType;
        ImageView imageViewResultFav = holder.imageViewResultFav;
        textResult.setText(word.getWord());
        textResultType.setText(word.getWordType());

        if (word.getFav() != null) {
            Log.e("RESULT_VUE_ADAPTER", String.valueOf(word.getFav()));
            if (Boolean.valueOf(word.getFav())) {

                imageViewResultFav.setImageResource(R.drawable.ic_fav);
            } else {
                imageViewResultFav.setImageResource(R.drawable.ic_unfav);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textResult;
        public ImageView imageViewResultFav;
        public TextView textResultType;

        public ViewHolder(final View itemView) {
            super(itemView);
            // Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
            textResult = (TextView) itemView.findViewById(R.id.result_text);
            textResultType = (TextView) itemView.findViewById(R.id.result_textType);
            imageViewResultFav = (ImageView) itemView.findViewById(R.id.favImgView);
        }
    }

    //Add Click Listener
    private static OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
