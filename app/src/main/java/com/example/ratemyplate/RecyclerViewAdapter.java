package com.example.ratemyplate;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    // delcaration
    private ArrayList<String> imageNames = new ArrayList<>();
    private ArrayList<String> imageCaptions = new ArrayList<>();
    private ArrayList<Bitmap> images = new ArrayList<>();

    private Context context;


    // constructor
    public RecyclerViewAdapter(ArrayList<String> imageNames, ArrayList<String> imageCaptions, ArrayList<Bitmap> images, Context context) {
        this.imageNames = imageNames;
        this.imageCaptions = imageCaptions;
        this.images = images;
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plate_list_element, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // log for debugging
        Log.d(TAG,"onBindViewHolder: called.");

        //gets the images, converts to bitmap, loads into holder


        holder.imageName.setText(imageNames.get(position));

        holder.imageCaption.setText(imageCaptions.get(position));

        holder.image.setImageBitmap(images.get(position));

//         user clicks on list in the list
//        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: clicked on:" + imageNames.get(position));
//
//                Toast.makeText(context, imageNames.get(position), Toast.LENGTH_SHORT).show();
//
//                //takes user to the item in the list they clicked on
//                Intent intent = new Intent(context, Plate_item.class);
//                intent.putExtra("image", images.get(position));
//                intent.putExtra("image_name", imageNames.get(position));
//                intent.putExtra("image_caption", imageCaptions.get(position));
//                context.startActivity(intent);
//            }
//        });

        holder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                customDialog("Confirm delete", "Are you sure you want to delete your plate?", "cancelMethod", "okMethod", position);
                return false;
            }
        });
    }



    @Override
    public int getItemCount() {

        // return the list, without this, blank screen
        return imageNames.size();
    }


    public void customDialog(String title, String message, final String cancelMethod, final String okMethod, final int position) {
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(context);
        builderSingle.setIcon(R.drawable.ic_android_black_24dp);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: Cancel Called.");
                        if (cancelMethod.equals("cancelMethod")) {
                            cancelMethod();
                        }
                    }
                }
        );

        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: OK Called.");
                        if (okMethod.equals("okMethod")) {
                            okMethod(position);
                        }
                    }
                }
        );
        builderSingle.show();
    }

    public void cancelMethod(){
        Log.d(TAG, "cancelMethod: Called.");
        Toast.makeText(context, "Cancelled.", Toast.LENGTH_SHORT).show();
    }

    public void okMethod(int position){
        Log.d(TAG, "okMethod: Called.");
        imageNames.remove(position);
        imageCaptions.remove(position);
        images.remove(position);
        notifyItemRemoved(position);
        Toast.makeText(context, "Plate deleted.", Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView imageName;
        TextView imageCaption;
        CircleImageView image;
        RelativeLayout parentLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            imageCaption = itemView.findViewById(R.id.image_caption);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}