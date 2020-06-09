package com.example.foodfreak.com.foodfreak.main.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodfreak.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MyAdapterRecyclerView extends RecyclerView.Adapter<FoodViewHolder>{
    private Context mContext;
    private static List<FoodData> myFoodList;
    //listeners for Buttons
    private  OnItemListener mListener;


    //iterfaces for listening to Button clicks
    public interface OnItemListener{
        public void onItemClicked(int position,int itemCount);
      
    }
    public void setOnItemClickedListener(OnItemListener listener)
    {
        mListener=listener;
    }

    public MyAdapterRecyclerView(Context mContext, List<FoodData> myFoodList) {
        this.mContext = mContext;
        this.myFoodList = myFoodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_cardview_item,parent,false);
        return new FoodViewHolder(mView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        //holder.itemImageView.setImageResource(myFoodList.get(position).getItemImage());
        if(myFoodList.get(position).getItemImageUrl().equals("null"))
        {
            holder.itemImageView.setVisibility(View.GONE);
        }
        else
        {
            Picasso.with(mContext)
                    .load(myFoodList
                            .get(position)
                            .getItemImageUrl()).into(holder.itemImageView);
        }

        if(myFoodList.get(position).getItemVegNonveg().equals("Veg")){
            holder.itemvegNonVegImageView.setImageResource(R.drawable.ic_veg);
        }
        else
        {
            holder.itemvegNonVegImageView.setImageResource(R.drawable.nonveg);
        }

        holder.mTitle.setText(myFoodList.get(position).getItemName());
        holder.mPrice.append(myFoodList.get(position).getItemPrice());
        holder.mDesc.setText(myFoodList.get(position).getItemDesc());
        if(myFoodList.get(position).getItemAvailability().equals("Available"))
        {holder.mAvailability.setVisibility(View.GONE);
        }
        else {
            holder.mAvailability.setText(myFoodList.get(position).getItemAvailability());
        }
        if(myFoodList.get(position).getItemCount()>0)
        {  holder.addToCartbtn.setVisibility(View.GONE);
           holder.inc_dec_btn_linear_layout_form.setVisibility(View.VISIBLE);
           holder.inc_dec_tv.setText(String.valueOf(myFoodList.get(position).getItemCount()));
        }

    }


    @Override
    public int getItemCount() {
        return myFoodList.size();
    }
    public static List<FoodData> getfoodlist(){
        return myFoodList;
    }
}
class FoodViewHolder extends RecyclerView.ViewHolder{
    ImageView itemImageView,itemvegNonVegImageView,inc_iv,dec_iv;
    TextView mTitle,mDesc,mPrice,mAvailability,inc_dec_tv;
    LinearLayout inc_dec_btn_linear_layout_form;
    Button addToCartbtn;
    CardView cardView;
    int itemCounter;

    public FoodViewHolder (View itemView, final MyAdapterRecyclerView.OnItemListener listener)
    {
        super(itemView);

        itemImageView=itemView.findViewById(R.id.itemImage);
        itemvegNonVegImageView=itemView.findViewById(R.id.veg_nonveg_display_iv);
        mTitle=itemView.findViewById(R.id.mTitle);
        mDesc=itemView.findViewById(R.id.itemDesc);
        mPrice=itemView.findViewById(R.id.itemPrice);
        cardView=itemView.findViewById(R.id.cardview_item);
        mAvailability=itemView.findViewById(R.id.itemAvailable);
        addToCartbtn=itemView.findViewById(R.id.addtoCartbtn_model_cardview);
        inc_dec_btn_linear_layout_form=itemView.findViewById(R.id.inc_dec_card_model_linear_layout);
        inc_iv=itemView.findViewById(R.id.cart_inc_model_card_view_iv);
        dec_iv=itemView.findViewById(R.id.cart_dec_model_card_view_iv);
        inc_dec_tv=itemView.findViewById(R.id.inc_dec_item_num_card_model_tv);
        itemCounter=0;

        //For addToCart Button
        /*addToCartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                {

                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        listener.OnAddBtnClick(position,v,);
                    }
                }

            }
        });*/
        addToCartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                inc_dec_btn_linear_layout_form.setVisibility(View.VISIBLE);
                MyAdapterRecyclerView.getfoodlist().get(getAdapterPosition()).setIncItemCount();
                inc_dec_tv.setText(String.valueOf(MyAdapterRecyclerView.getfoodlist().get(getAdapterPosition()).getItemCount()));
                listener.onItemClicked(getAdapterPosition(),itemCounter);

            }
        });
        inc_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCounter=itemCounter+1;
                MyAdapterRecyclerView.getfoodlist().get(getAdapterPosition()).setIncItemCount();
                inc_dec_tv.setText(String.valueOf(MyAdapterRecyclerView.getfoodlist().get(getAdapterPosition()).getItemCount()));
                listener.onItemClicked(getAdapterPosition(),itemCounter);
            }
        });
        dec_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyAdapterRecyclerView.getfoodlist().get(getAdapterPosition()).getItemCount() == 0)
                {
                    inc_dec_btn_linear_layout_form.setVisibility(View.GONE);
                    addToCartbtn.setVisibility(View.VISIBLE);
                    listener.onItemClicked(getAdapterPosition(),itemCounter);
                }
                else
                {
                    MyAdapterRecyclerView.getfoodlist().get(getAdapterPosition()).setDecItemCount();
                    inc_dec_tv.setText(String.valueOf(MyAdapterRecyclerView.getfoodlist().get(getAdapterPosition()).getItemCount()));
                    listener.onItemClicked(getAdapterPosition(),itemCounter);
                }
            }
        });
    }

}
