package com.catapult.android.model;

import java.util.ArrayList;
import java.util.List;

import com.catapult.android.mca.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FruitAdapter extends ArrayAdapter {
	private List list = new ArrayList();

	public FruitAdapter(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}

	public void add(FruitClass object) {
		// TODO Auto-generated method stub
		list.add(object);
		super.add(object);
	}

	static class ImgHolder {
		TextView IMG;
		TextView NAME;
		TextView QTY;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.list.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row;
		row = convertView;
		ImgHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.row_layout, parent, false);
			
			holder = new ImgHolder();
			holder.IMG = (TextView) row.findViewById(R.id.fruit_img);
			holder.NAME = (TextView) row.findViewById(R.id.fruit_name);
			holder.QTY = (TextView) row.findViewById(R.id.fruit_qty);
			row.setTag(holder);
		} else {
			holder = (ImgHolder) row.getTag();

		}

		FruitClass FR = (FruitClass) getItem(position);
		//holder.IMG.setImageResource(FR.getFruit_resource());
		holder.IMG.setText(FR.getFruit_resource());
		holder.NAME.setText(FR.getFruit_name());
		holder.QTY.setText(FR.getFruit_qty());

		return row;
	}

}
