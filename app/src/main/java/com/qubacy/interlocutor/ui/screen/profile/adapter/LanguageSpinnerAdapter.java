package com.qubacy.interlocutor.ui.screen.profile.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.data.general.export.struct.profile.LanguageEnum;

public class LanguageSpinnerAdapter implements SpinnerAdapter {
    private final LayoutInflater m_layoutInflater;

    protected LanguageSpinnerAdapter(final LayoutInflater layoutInflater) {
        m_layoutInflater = layoutInflater;
    }

    public static LanguageSpinnerAdapter getInstance(final Context context) {
        if (context == null) return null;

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (layoutInflater == null) return null;

        return new LanguageSpinnerAdapter(layoutInflater);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Log.d("TEST", "getDropDownView");

        return getView(position, convertView, parent);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return LanguageEnum.values().length;
    }

    @Override
    public LanguageEnum getItem(final int position) {
        return LanguageEnum.getLanguageById(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private View createLanguageView(final int position, final ViewGroup parent) {
        View itemView = m_layoutInflater.inflate(
                R.layout.fragment_profile_language, parent, false);

        TextView nameTextView =
                itemView.findViewById(R.id.profile_language_item_caption);

        if (nameTextView == null) return null;

        LanguageEnum language = LanguageEnum.getLanguageById(position);

        if (language == null) return null;

        nameTextView.setText(language.getName());

        return itemView;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("TEST", "getView");

        if (convertView == null)
            return createLanguageView(position, parent);

        TextView captionTextView = convertView.findViewById(R.id.profile_language_item_caption);

        if (captionTextView == null)
            return createLanguageView(position, parent);

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return (getCount() <= 0);
    }
}
