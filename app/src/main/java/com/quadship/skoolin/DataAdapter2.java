package com.quadship.skoolin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//import com.bbmsource.imageloader.ImageLoader;

public class DataAdapter2 extends BaseAdapter {

	ArrayList<UserData> userArray;
	LayoutInflater inflater;
	ViewHolder holder;
	ImageLoader imgload;
	public DataAdapter2(Context baseContext, ArrayList<UserData> userArray2) {

		this.userArray = userArray2;
		inflater = (LayoutInflater)baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		imgload = new ImageLoader(baseContext);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return userArray.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		if(convertView == null)
		{
			vi = inflater.inflate(R.layout.row_layout, null);
			holder = new ViewHolder();

			holder.bbmPinView = (TextView)vi.findViewById(R.id.tv_android);
			//holder.bbmUserView = (TextView)vi.findViewById(R.id.bbmuser_name);
			holder.bbmUserImage = (ImageView)vi.findViewById(R.id.img_android);
			//holder.userGender = (ImageView)vi.findViewById(R.id.gender_icon);

			vi.setTag(holder);

		}
		else
		{
			holder = (ViewHolder)vi.getTag();
		}

		holder.bbmPinView.setText(userArray.get(position).getUserBbmPin());
		//holder.bbmUserView.setText(userArray.get(position).getUserName());
		imgload.DisplayImage(userArray.get(position).getUserImg(), holder.bbmUserImage);
		/*if(userArray.get(position).getUserImg()!=null)
		{
			byte [] encodeByte=Base64.decode(userArray.get(position).getUserImg().toString(),Base64.DEFAULT);
			Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);


			//Bitmap bt = decodeBase64(userArray.get(position).getUserImg().toString());

			holder.bbmUserImage.setImageBitmap(bitmap);

		}*/

	//	if(userArray.get(position).getUserGender().equalsIgnoreCase("M"))
	//	{
	//		holder.userGender.setImageResource(R.drawable.male_16);

	//	}else if(userArray.get(position).getUserGender().equalsIgnoreCase("F"))
	//	{
	//		holder.userGender.setImageResource(R.drawable.female_16);
	//	}
		
		if(userArray.get(position).getUserImg()!=null)
		{
			if(userArray.get(position).getUserImg().equalsIgnoreCase("NA")||
					userArray.get(position).getUserImg().equalsIgnoreCase(""))
			{
				if(userArray.get(position).getUserGender().equalsIgnoreCase("M"))
				{
					holder.bbmUserImage.setImageResource(R.drawable.default_user);
				}else if(userArray.get(position).getUserGender().equalsIgnoreCase("F"))
				{
					holder.bbmUserImage.setImageResource(R.drawable.user_female);
				}
			}else
			{
				holder.bbmUserImage.setImageResource(R.drawable.default_user);
				imgload.DisplayImage(userArray.get(position).getUserImg(), holder.bbmUserImage);
				}
		}

		//		if(holder.bbmUserImage!=null)
		//		{
		//		holder.bbmUserImage.setImageBitmap(getBitmap(""));
		//		}   
		return vi ;
	}

	/*public static Bitmap decodeBase64(String input) 
	{
		byte[] decodedByte = Base64.decode(input, 0);
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length); 
	}

	public Bitmap getBitmap(String bitmapUrl) {
		try {
			System.out.println("getBitmapdecoding");
			URL url = new URL(bitmapUrl);
			return BitmapFactory.decodeStream(url.openConnection().getInputStream()); 
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;}
	}*/

	static class ViewHolder{

		TextView bbmPinView;
		//TextView bbmUserView;
		ImageView bbmUserImage;
		//TextView bbmUserGender;
		//ImageView userGender;
	}

}