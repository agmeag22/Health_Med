package com.secondsave.health_med.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.secondsave.health_med.Database.Entities.User;
import com.secondsave.health_med.R;

import java.util.List;

public class UserAdapter  extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    View v;
    private List<User> mUsers;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = parent.inflate(parent.getContext(),R.layout.element_user,null);
        ViewHolder vh= new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(mUsers!=null) {
            holder.username.setText(mUsers.get(position).getUsername());
            holder.password.setText(mUsers.get(position).getPassword());
        }
    }

    @Override
    public int getItemCount() {
        if (mUsers != null)
            return mUsers.size();
        else return 0;
    }

    public void setmUsers(List<User> mUsers) {
        this.mUsers = mUsers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username,password;
        public ViewHolder(View itemView) {
            super(itemView);
            password = itemView.findViewById(R.id.passwort_txt);
            username = itemView.findViewById(R.id.username_txt);
        }

    }
}
