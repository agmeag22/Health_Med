package com.secondsave.health_med.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.secondsave.health_med.Database.Entities.Dose;
import com.secondsave.health_med.R;

import java.text.SimpleDateFormat;
import java.util.List;

public abstract class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    View v;
    private List<Dose> doseList;
    String[] mArray;
    private Context context;


    public ReminderAdapter(List<Dose> doseList, Context context) {
        this.doseList = doseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = parent.inflate(parent.getContext(), R.layout.reminders_cardview, null);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        mArray = context.getResources().getStringArray(R.array.medication_measurement);
        if (doseList != null) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            if (doseList.get(position).getName() != null) {
                holder.med_name.setText(doseList.get(position).getName());
            }
            if (doseList.get(position).getSize() > 0) {
                holder.dose_size.setText(doseList.get(position).getSize() + "");
            }
            if (doseList.get(position).getId_dose_type() >= 0) {
                holder.dose_type.setText(mArray[doseList.get(position).getId_dose_type()]);
            }
            if (doseList.get(position).getLapse() > 0) {
                holder.dose_lapse.setText(doseList.get(position).getLapse() + "");
            }
            if (doseList.get(position).getStart_date() != null) {


                holder.dose_start_date.setText(df.format(doseList.get(position).getStart_date()));
            }
            if (doseList.get(position).getEnd_date() != null) {

                holder.dose_end_date.setText(df.format(doseList.get(position).getEnd_date()));
            }
            if (doseList.get(position).getId_dose_type() > -1) {
                if (doseList.get(position).getId_dose_type() == 0) {
                    holder.layoucard.setBackgroundColor(context.getResources().getColor(R.color.health));
                }
                if (doseList.get(position).getId_dose_type() == 1) {
                    holder.layoucard.setBackgroundColor(context.getResources().getColor(R.color.settings));
                }
                if (doseList.get(position).getId_dose_type() == 2) {
                    holder.layoucard.setBackgroundColor(context.getResources().getColor(R.color.background));
                }
                if (doseList.get(position).getId_dose_type() == 3) {
                    holder.layoucard.setBackgroundColor(context.getResources().getColor(R.color.pharmacies));
                }
            }


            if (doseList.get(position).isReminder_enabled()) {
                holder.aSwitch.setChecked(true);
            } else {
                holder.aSwitch.setChecked(false);
            }
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.aSwitch.isChecked()) {
                        holder.aSwitch.setChecked(false);
                        setAlarmStatus(false, doseList.get(position));
                    } else {
                        holder.aSwitch.setChecked(true);
                        setAlarmStatus(true, doseList.get(position));
                    }
                }
            });

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemMethod(doseList.get(position));
                }
            });

        }
    }

    public abstract void setAlarmStatus(boolean i, Dose dose);

    public abstract void onClickItemMethod(Dose dose);


    @Override
    public int getItemCount() {
        if (doseList != null)
            return doseList.size();
        else return 0;
    }

    public void setList(List<Dose> doseList) {
        this.doseList = doseList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView med_name, dose_size, dose_type, dose_lapse, dose_start_date, dose_end_date;
        Switch aSwitch;
        View view;
        LinearLayout linearLayout, layoucard;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            med_name = itemView.findViewById(R.id.text_med_name);
            dose_size = itemView.findViewById(R.id.text_dose_size);
            dose_type = itemView.findViewById(R.id.text_dose_type);
            dose_lapse = itemView.findViewById(R.id.text_dose_time);
            dose_start_date = itemView.findViewById(R.id.text_start_date);
            dose_end_date = itemView.findViewById(R.id.text_end_date);
            aSwitch = itemView.findViewById(R.id.switchR);
            linearLayout = itemView.findViewById(R.id.layoutswitch);
            layoucard = itemView.findViewById(R.id.layoutcard);
        }
    }
}
