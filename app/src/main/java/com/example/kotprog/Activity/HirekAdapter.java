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

public class HirekAdapter  extends RecyclerView.Adapter<HirekAdapter.ViewHolder>
{
    private ArrayList<Hir> hirek;
    private Context context;

    public HirekAdapter(Context context, ArrayList<Hir> hirek) {
        this.hirek = hirek;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HirekAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_hirek, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.cim.setText(hirek.get(position).getCim());
        holder.hir.setText(hirek.get(position).getHir());
        holder.kep.setImageResource(hirek.get(position).getKep());
    }

    @Override
    public int getItemCount()
    {
        return hirek.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView cim;
        private TextView hir;
        private ImageView kep;

        public ViewHolder(View view){
            super(view);

            cim = view.findViewById(R.id.textViewCimHirek);
            hir = view.findViewById(R.id.textViewHirHirek);
            kep = view.findViewById(R.id.imageViewHirek);
        }
    }
}
