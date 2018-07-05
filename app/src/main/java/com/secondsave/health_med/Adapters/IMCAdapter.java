package com.secondsave.health_med.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.secondsave.health_med.Database.Entities.IMCEntry;
import com.secondsave.health_med.R;
import com.secondsave.health_med.Utils.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IMCAdapter extends RecyclerView.Adapter<IMCAdapter.ViewHolder> {
    View v;
    private List<IMCEntry> imcEntries;


    public IMCAdapter(List<IMCEntry> imcEntries) {
        this.imcEntries = imcEntries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = parent.inflate(parent.getContext(),R.layout.item_imc_list,null);
        ViewHolder vh= new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if(imcEntries !=null) {
            IMCEntry imc = imcEntries.get(position);
            holder.weight.setText(imc.getWeight()+" kg");
            holder.height.setText(imc.getFat()+"%");
            holder.imc.setText(imc.getImc()+"");
            DateFormat df = new SimpleDateFormat(Constants.DOB_FORMAT);
            String dt = df.format(imc.getCreate_date());
            holder.date.setText(dt);
            if(position>0){
                IMCEntry imcold = imcEntries.get(position-1);
                float diff = imc.getWeight()-imcold.getWeight();
                if(diff>0) {
                    holder.difference.setText("+"+diff+" kg");
                }else{
                    holder.difference.setText("-"+diff+" kg");
                }
            }else{
                holder.difference.setText("-");
            }


        }
    }


    @Override
    public int getItemCount() {
        if (imcEntries != null)
            return imcEntries.size();
        else return 0;
    }

    public void setList(List<IMCEntry> mUsers) {
        this.imcEntries = mUsers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView weight,height,imc,difference,date;
        public ViewHolder(View itemView) {
            super(itemView);
            weight= itemView.findViewById(R.id.text1);
            height= itemView.findViewById(R.id.text2);
            imc= itemView.findViewById(R.id.text3);
            difference= itemView.findViewById(R.id.text4);
            date= itemView.findViewById(R.id.text5);
        }
    }
}
