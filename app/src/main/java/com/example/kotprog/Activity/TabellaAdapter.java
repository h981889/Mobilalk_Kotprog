package com.example.kotprog.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kotprog.R;

import java.util.ArrayList;

public class TabellaAdapter extends RecyclerView.Adapter<TabellaAdapter.ViewHolder>
{
    private ArrayList<Tabella> tabellak;
    private Context context;

    public TabellaAdapter(Context context, ArrayList<Tabella> tabellak) {
        this.tabellak = tabellak;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new TabellaAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_tabella, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.helyezes.setText(Integer.toString(tabellak.get(position).getHelyezes()));
        holder.csapat.setText(tabellak.get(position).getCsapat());
        holder.pontok.setText(Integer.toString(tabellak.get(position).getPontok()));
    }

    @Override
    public int getItemCount() {
        return tabellak.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView helyezes;
        private TextView csapat;
        private TextView pontok;

        public ViewHolder(View view){
            super(view);

            helyezes = view.findViewById(R.id.textViewHelyezesTabella);
            csapat = view.findViewById(R.id.textViewCsapatTabella);
            pontok = view.findViewById(R.id.textViewPontTabella);
    }
    }
}
