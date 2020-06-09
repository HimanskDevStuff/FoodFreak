package com.example.foodfreak.com.foodfreak.main.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.foodfreak.R;
import com.example.foodfreak.com.foodfreak.main.user.FoodData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class Update_item extends Fragment {

    public Update_item() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_item, container, false);
    }

}
/*
package com.example.foodfreak.com.foodfreak.main.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.foodfreak.R;
import com.example.foodfreak.com.foodfreak.main.user.FoodData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class Add_new_item extends Fragment {
    TextInputLayout itemName,itemDesc,itemPrice;
    RadioGroup itemAvailability_rg,itemVegNonVeg_rg;
    RadioButton available_rb,unavailable_rb,veg_rb,nonveg_rb;
    ImageView itemImage;
    Button chooseFile,uploadFile;
    CheckBox skipUploadingImage;
    DatabaseReference reff;
    HelperClass_admin adminData;
    FoodData foodData;
    Uri filepath;
    Boolean ImageSelected =false;
    StorageReference storage;
    ProgressDialog progressDialog;
    //String values
     String itemName_str,itemDesc_str,itemPrice_str,itemVegNonVeg_str,itemAvailability_str,available_str,unavail_str,veg_str,nonveg_str;


    public Boolean validateItemName()
    {
        String val=itemName.getEditText().getText().toString();
        if(val.isEmpty())
        {
            itemName.setErrorEnabled(true);
            itemName.setError("Please enter valid item name !");
            return false;
        }
        else
        {
            itemName.setErrorEnabled(false);
            itemName.setError(null);
            return true;

        }
    }
    public Boolean validateItemDesc()
    {
        String val=itemDesc.getEditText().getText().toString();
        if(val.isEmpty())
        {
            itemDesc.setErrorEnabled(true);
            itemDesc.setError("Please enter valid item description !!");
            return false;
        }
        else if(val.length()<=20)
        {
            itemDesc.setErrorEnabled(true);
            itemDesc.setError("Description should be atleast 20 characters");
            return false;

        }
        else
        {
            itemDesc.setErrorEnabled(false);
            itemDesc.setError(null);
            return true;

        }
    }
    public Boolean validateItemPrice()
    {
        String val=itemPrice.getEditText().getText().toString();
        if(val.isEmpty())
        {
            itemPrice.setErrorEnabled(true);
            itemPrice.setError("Please enter valid item price!");
            return false;
        }
        else if(val.length()>=4)
        {
            itemPrice.setErrorEnabled(true);
            itemPrice.setError("Item price should not be greater than 999!");
            return false;

        }
        else
        {
            itemDesc.setErrorEnabled(false);
            itemDesc.setError(null);
            return true;

        }
    }
    public Boolean validateItemAvailability()
    {
        int i=itemAvailability_rg.getCheckedRadioButtonId();
        if(i==-1)
        {
            Toast.makeText(getActivity(),"Please select item availability !",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            if(i==R.id.available_rb)
            itemAvailability_str=available_str;
            else if(i==R.id.unavailable_rb)
            {
                itemAvailability_str=unavail_str;
            }
            return true;
        }
    }
    public Boolean validateItemVegNonVeg()
    {
        int i=itemVegNonVeg_rg.getCheckedRadioButtonId();
        if(i==-1)
        {
            Toast.makeText(getActivity(),"Please select item category!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            if(i==R.id.veg_rb)
                itemVegNonVeg_str=veg_str;
            else if(i==R.id.non_veg_rb)
            {
                itemVegNonVeg_str=nonveg_str;
            }
            return true;
        }
    }
    public Boolean validateAllDetails()
    {
        if(validateItemAvailability() & validateItemDesc() & validateItemName() & validateItemPrice() & validateItemVegNonVeg())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    Boolean completed=false;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data !=null && data.getData()!=null)
        {
            filepath = data.getData();
            if(Build.VERSION.SDK_INT < 28)
            {
                try{
                    Bitmap bitmap =  MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filepath);
                    itemImage.setImageBitmap(bitmap);
                    completed=true;
                }catch (IOException e)
                {
                    e.printStackTrace();
                    completed=false;
                }
            }/*
            if(Build.VERSION.SDK_INT>=28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), filepath);
                    try {
                    Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                    itemImage.setImageBitmap(bitmap);
                    completed = true;
                    } catch (IOException e) {
                    e.printStackTrace();
                    completed = false;
                    }
                    }

                    }
                    }*/
/*
public Boolean chooseImage()
        {
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(i.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select image"),1);
        if(completed.equals(true))
        return true;
        else
        return false;
        }



public Add_new_item() {
        // Required empty public constructor
        }



@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_add_new_item, container, false);
        itemName=v.findViewById(R.id.itemName_additem_frag);
        itemDesc=v.findViewById(R.id.itemDescription_additem_frag);
        itemPrice=v.findViewById(R.id.itemPrice_additem_frag);
        itemImage=v.findViewById(R.id.imageItemUpload);
        itemAvailability_rg=v.findViewById(R.id.availability_rg_additem_frag);
        itemVegNonVeg_rg=v.findViewById(R.id.vegnonveg_additem_rg);
        available_rb=v.findViewById(R.id.available_rb);
        unavailable_rb=v.findViewById(R.id.unavailable_rb);
        veg_rb=v.findViewById(R.id.veg_rb);
        nonveg_rb=v.findViewById(R.id.non_veg_rb);
        //hooks for buttons
        chooseFile=v.findViewById(R.id.choose_additem_frag_btn);
        uploadFile=v.findViewById(R.id.AddItem_btn_additem_frag);
        //hooks for checkbox
        skipUploadingImage=v.findViewById(R.id.skipUploadImage_additem_frag_cb);


        //initialize storage
        storage= FirebaseStorage.getInstance().getReference();
        //progress
        progressDialog= new ProgressDialog(getActivity());
        //setting visibility of imageview and Choosebtn to invisible when checkbox is checked
        skipUploadingImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
@Override
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(skipUploadingImage.isChecked())
        {
        itemImage.setVisibility(View.GONE);
        chooseFile.setVisibility(View.GONE);
        }
        else if(!skipUploadingImage.isChecked())
        {
        itemImage.setVisibility(View.VISIBLE);
        chooseFile.setVisibility(View.VISIBLE);
        }

        }
        });

        chooseFile.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        ImageSelected=chooseImage();

        }
        });
        uploadFile.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        //convert them to string
        itemName_str=itemName.getEditText().getText().toString();
        itemDesc_str=itemDesc.getEditText().getText().toString();
        itemPrice_str=itemPrice.getEditText().getText().toString();
        available_str=available_rb.getText().toString();
        unavail_str=unavailable_rb.getText().toString();
        veg_str=veg_rb.getText().toString();
        nonveg_str=nonveg_rb.getText().toString();
        if(skipUploadingImage.isChecked())
        {
        Log.d("In","Not checked");
        if(validateAllDetails())
        {
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        reff= FirebaseDatabase.getInstance().getReference("canteen_details");
        adminData=Account_admin.getAdminData();
        foodData=new FoodData(itemName_str,itemDesc_str,itemPrice_str,itemAvailability_str,itemVegNonVeg_str);
        DatabaseReference reff_canteen_name=reff.child(adminData.getCanteen_name());
        reff_canteen_name.child(foodData.getItemName()).setValue(foodData, new DatabaseReference.CompletionListener() {
@Override
public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
        if(databaseError !=null)
        {
        Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();


        }
        else
        {
        progressDialog.dismiss();
        Toast.makeText(getActivity(),"Data uploaded successfully to servers",Toast.LENGTH_SHORT).show();

        }
        }
        });
        }



        }

        else if(!skipUploadingImage.isChecked())
        {
        Log.d("In","Not checked");

        if(validateAllDetails() & ImageSelected)
        {
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        reff= FirebaseDatabase.getInstance().getReference("canteen_details");
        adminData=Account_admin.getAdminData();
        foodData=new FoodData(itemName_str,itemDesc_str,itemPrice_str,itemAvailability_str,itemVegNonVeg_str);
        DatabaseReference reffofcanteen=reff.child(adminData.getCanteen_name());
        reffofcanteen.child(itemName_str).setValue(foodData, new DatabaseReference.CompletionListener() {
@Override
public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
        if(databaseError !=null) {
        Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();

        }
        else
        {
        if(filepath !=null){
final StorageReference reffImage=storage.child(adminData.getCanteen_name() +"/"+foodData.getItemName())
        reffImage.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
@Override
public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(),"Data Uploaded successfully",Toast.LENGTH_SHORT).show();
        }

        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
@Override
public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
        progressDialog.setMessage("Uploaded " +(int)progress+"%");


        }
        });
        }

        }
        }
        });
        }




        }



        }
        });








        return v;
        }


        }

 */