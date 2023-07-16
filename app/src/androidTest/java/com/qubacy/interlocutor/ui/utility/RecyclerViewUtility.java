package com.qubacy.interlocutor.ui.utility;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewUtility {
    public static RecyclerView.ViewHolder getViewHolderByPosition(
            final RecyclerView recyclerView,
            final int position)
    {
        if (recyclerView == null || position < 0)
            return null;

        RecyclerView.ViewHolder viewHolder =
                recyclerView.findViewHolderForLayoutPosition(position);

        if (viewHolder == null) return null;

        return viewHolder;
    }
}
