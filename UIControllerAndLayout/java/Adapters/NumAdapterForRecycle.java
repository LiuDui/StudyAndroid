package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.uicontrollers.R;

import java.util.List;

import entity.NumInCHEN;

public class NumAdapterForRecycle extends RecyclerView.Adapter<NumAdapterForRecycle.ViewHolder> {
    private List<NumInCHEN> numList = null;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView numChinese;
        TextView numEnglish;

        public ViewHolder(View view){
            super(view);
            numChinese = view.findViewById(R.id.num_inabric);
            numEnglish = view.findViewById(R.id.num_inenglish);
        }
    }

    public NumAdapterForRecycle(List<NumInCHEN> list){
        numList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.num_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NumInCHEN num = numList.get(position);
        holder.numEnglish.setText(num.getNumInEnglish());
        holder.numChinese.setText(String.valueOf(num.getNumInAbric()));
    }

    @Override
    public int getItemCount() {
        return numList.size();
    }


}
