package com.example.foodfreak.com.foodfreak.main.user;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodData {
    private  String itemName,itemDesc,itemPrice,itemAvailability,itemVegNonveg,itemImageUrl;
    int itemCount;

    public FoodData() {
    }



    public FoodData(String itemName, String itemDesc, String itemPrice, String itemAvailability, String itemVegNonvegImage, String itemImageUrl) {
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.itemPrice = itemPrice;
        this.itemVegNonveg = itemVegNonvegImage;
        this.itemAvailability=itemAvailability;
        this.itemImageUrl=itemImageUrl;
        itemCount=0;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public String getItemAvailability() {
        return itemAvailability;
    }

    public void setItemAvailability(String itemAvailability) {
        this.itemAvailability = itemAvailability;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemVegNonveg(String itemVegNonvegImage)
    {
        this.itemVegNonveg=itemVegNonvegImage;
    }
    public String getItemVegNonveg()
    {
        return  itemVegNonveg;
    }

    //itemCount
    public int getItemCount() {
        return itemCount;
    }

    public void setIncItemCount() {

        itemCount=itemCount+1;
    }
    public void setDecItemCount() {

        itemCount=itemCount-1;
    }


}
