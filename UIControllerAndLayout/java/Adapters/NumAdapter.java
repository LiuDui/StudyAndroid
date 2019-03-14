package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.root.uicontrollers.R;

import java.util.List;

import entity.NumInCHEN;

public class NumAdapter extends ArrayAdapter<NumInCHEN> {
    private int resourceId;

    public NumAdapter(Context context, int textViewResourceId, List<NumInCHEN> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 每个子项在被滚动到屏幕内时，会调用getView方法
//        NumInCHEN it = getItem(position); //获取当前项的item实例
//        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
//        TextView textView1 = view.findViewById(R.id.num_inabric);
//        TextView textView2 = view.findViewById(R.id.num_inenglish);
//
//        textView1.setText(String.valueOf(it.getNumInAbric()));
//        textView2.setText(it.getNumInEnglish());
//        return view;
//

        /**
         * 优化1：缓存布局
         */
//        NumInCHEN it = getItem(position); //获取当前项的item实例
//        View view = null;
//        if(convertView == null){ // convertView 用于将之前加载好的布局进行缓存
//            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
//        }else{
//            view = convertView;
//        }
//        TextView textView1 = view.findViewById(R.id.num_inabric);
//        TextView textView2 = view.findViewById(R.id.num_inenglish);
//
//        textView1.setText(String.valueOf(it.getNumInAbric()));
//        textView2.setText(it.getNumInEnglish());
//        return view;

        /**
         * 优化2：每次调用都会调用findWiewById来获取实例，可以把这个也缓存起来
         */
        NumInCHEN it = getItem(position); //获取当前项的item实例
        View view = null;
        ViewHolder viewHolder = null;
        if(convertView == null){ // convertView 用于将之前加载好的布局进行缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView1 = view.findViewById(R.id.num_inabric);
            viewHolder.textView2 = view.findViewById(R.id.num_inenglish);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        TextView textView1 =viewHolder.textView1;
        TextView textView2 = viewHolder.textView2;

        textView1.setText(String.valueOf(it.getNumInAbric()));
        textView2.setText(it.getNumInEnglish());
        return view;



    }
    class ViewHolder{
        TextView textView1;
        TextView textView2;
    }
}
