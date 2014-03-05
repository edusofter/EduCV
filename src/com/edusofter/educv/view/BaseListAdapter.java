package com.edusofter.educv.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edusofter.educv.R;

public class BaseListAdapter extends BaseAdapter {
	private int resource;
	private List<? extends CVListItem> items;
	private Context context;

	public BaseListAdapter(Context context, int resource, List<? extends CVListItem> edu) {
		this.resource = resource;
		this.items = edu;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout view;
		if(convertView == null){
			 LayoutInflater vi = LayoutInflater.from(context);
	         view = (LinearLayout) vi.inflate(resource, null);
		} else {
			view = (LinearLayout) convertView;
		}
		
		CVListItem item = items.get(position);
		((TextView)view.findViewById(R.id.textName)).setText(item.getName());
		((TextView)view.findViewById(R.id.textDesc)).setText(item.getDesc());
		((TextView)view.findViewById(R.id.textStartDate)).setText(item.getStartDate());
		((TextView)view.findViewById(R.id.textEndDate)).setText(item.getEndDate());
		((TextView)view.findViewById(R.id.textEmployer)).setText(item.getEmployer());
		return view;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getId();
	}

}
