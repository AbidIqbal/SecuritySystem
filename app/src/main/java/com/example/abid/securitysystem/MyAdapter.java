package com.example.abid.securitysystem;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> values;
    private List<Bitmap> images;


    int[] IMAGES={R.drawable.cat1,R.drawable.t2,R.drawable.t3,R.drawable.t4,R.drawable.t5,R.drawable.t6,R.drawable.t4};

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView Heading;
        public TextView Detail;
        public TextView timeview;
        public TextView date;
        public ImageView imageView;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            layout = v;
            Heading = (TextView) v.findViewById(R.id.name);
            Detail = (TextView) v.findViewById(R.id.detail);
            imageView=(ImageView) v.findViewById(R.id.imageView);
            timeview=(TextView) v.findViewById(R.id.time);
            date=(TextView) v.findViewById(R.id.date);
        }

        @Override
        public void onClick(View v) {

            remove(getAdapterPosition());
        }
    }

    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Bitmap> Images,List<String> time) {
        values = time;
        images= Images;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.custom_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Log.i("position","Original Position is "+position);

        final String name = values.get(position);



        holder.imageView.setImageBitmap(images.get(position));

//        String [] tokens=name.split(",");
//        String [] time=tokens[0].split("=");
//        String [] object=tokens[1].split("=");
//        String [] secondPart=tokens[2].split("=");
//        Log.i("object","firstplace "+secondPart[0]);
//        Log.i("object",secondPart[1]);
//        Log.i("object",""+secondPart[1].length());
//        String objectname=object[1].substring(0,(object[1].length()-1));

//        String timevalue=time[1];
        try {
            String timevalue=values.get(position);
            Long TimeStamp=Long.parseLong(timevalue);
            Date date=new Date((long)TimeStamp*1000);

            java.text.SimpleDateFormat dateFormat=new java.text.SimpleDateFormat("dd-MM-yyyy");
            java.text.SimpleDateFormat timeFormat=new java.text.SimpleDateFormat("HH:mm:ss");
            String datevalue=dateFormat.format(date);
            String timeValue=timeFormat.format(date);

            holder.timeview.setText(timeValue);
            holder.date.setText(datevalue);

            String objectname="Human";

            holder.Heading.setText(objectname+" Detected");
            holder.Detail.setText("Hello sir, A "+objectname+" has been detected");

        }
        catch (Exception e){
        }



//        holder.imageView.setImageResource(IMAGES[position]);
//        holder.Heading.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                remove(holder.getLayoutPosition());
//                AppCompatActivity activity=(AppCompatActivity) v.getContext();
//                DetailFragment fragment=new DetailFragment();
//                activity.getSupportFragmentManager().beginTransaction().add(fragment,"hy").commit();
//
//            }
//        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }




    public Bitmap converter(String img1, String img2){
        try {
            byte[] b1= Base64.decode(img1,Base64.DEFAULT);
            byte[] b2= Base64.decode(img2,Base64.DEFAULT);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            byteArrayOutputStream.write(b1);
            byteArrayOutputStream.write(b2);

            byte [] image=byteArrayOutputStream.toByteArray();
            Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
            return bitmap;

        }
        catch (Exception e){
            return null;
        }
    }
    public int removePosition(int position){
        if(position%2==0){
            return (position+1);
        }
        else {
            return position;
        }
    }

}