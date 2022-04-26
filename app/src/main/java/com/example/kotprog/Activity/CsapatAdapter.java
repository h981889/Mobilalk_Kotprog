package com.example.kotprog.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kotprog.R;

import java.util.ArrayList;

public class CsapatAdapter extends RecyclerView.Adapter<CsapatAdapter.ViewHolder>
{
    private ArrayList<Csapat> csapatok;
    private Context context;

    public CsapatAdapter(Context cont, ArrayList<Csapat> csapatAdat)
    {
        csapatok = csapatAdat;
        context = cont;
    }

    @Override
    public CsapatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new CsapatAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_csapat, parent, false));
    }

    @Override
    public void onBindViewHolder(CsapatAdapter.ViewHolder holder, int position)
    {
        holder.nev.setText(csapatok.get(position).getNev());
        holder.stadion.setText(csapatok.get(position).getStadion());
        holder.edzo.setText(csapatok.get(position).getEdzo());
        holder.alapitva.setText(csapatok.get(position).getAlapitva());
        holder.cimer.setImageResource(csapatok.get(position).getCimer());
    }

    @Override
    public int getItemCount()
    {
        return csapatok.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView nev;
        private TextView stadion;
        private TextView edzo;
        private TextView alapitva;
        private ImageView cimer;

        public ViewHolder(View view){
            super(view);

            nev = view.findViewById(R.id.textViewNevCsapat);
            stadion = view.findViewById(R.id.textViewStadionCsapat);
            edzo = view.findViewById(R.id.textViewEdzoCsapat);
            alapitva = view.findViewById(R.id.textViewAlapitvaCsapat);
            cimer = view.findViewById(R.id.imageViewCimer);
        }
    }
}
