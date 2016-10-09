package studio.crazybt.blockingsms.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import studio.crazybt.blockingsms.R;
import studio.crazybt.blockingsms.models.SmsBlocked;

/**
 * Created by Brucelee Thanh on 09/10/2016.
 */

public class SmsBlockedListAdapter extends RecyclerView.Adapter<SmsBlockedListAdapter.ViewHolder>{

    private List<SmsBlocked> smsBLockedList;

    public SmsBlockedListAdapter(List<SmsBlocked> smsBLockedList) {
        this.smsBLockedList = smsBLockedList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sms_blocked, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvPhoneNumber.setText(smsBLockedList.get(position).getPhoneNumber());
        holder.tvTime.setText(smsBLockedList.get(position).getTime());
        holder.tvContent.setText(smsBLockedList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return smsBLockedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvPhoneNumber;
        TextView tvTime;
        TextView tvContent;
        public ViewHolder(View itemView) {
            super(itemView);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        }
    }
}
