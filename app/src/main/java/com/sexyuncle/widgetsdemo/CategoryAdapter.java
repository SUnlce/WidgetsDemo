package com.sexyuncle.widgetsdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by dev-sexyuncle on 16/4/22.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private String[] categorys;
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public CategoryAdapter() {
        categorys = WidgetApplication.getInstance().getResources().getStringArray(R.array.category_array);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.list_itrm, null);
        CategoryViewHolder viewHolder = new CategoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        holder.text.setText(categorys[position]);
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRecyclerViewItemClickListener != null)
                    mOnRecyclerViewItemClickListener.onItemClick(v, position);
            }

        });
    }

    @Override
    public int getItemCount() {
        return categorys.length;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView text;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.item_text);
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener) {
        this.mOnRecyclerViewItemClickListener = mOnRecyclerViewItemClickListener;
    }

    public String getItem(int position){
        return categorys[position];
    }
    /**
     * @description RecyclerView item 点击事件
     */
    public interface OnRecyclerViewItemClickListener {
        /**
         * @param childView RecyclerView item view
         * @param position  RecyclerView item position
         */
        void onItemClick(View childView, int position);
    }
}
