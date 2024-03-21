package com.example.demo.profilepic;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUtil {
    public static StorageReference getCurrentProfilePicStorage(){
        return FirebaseStorage.getInstance().getReference().child("profile_pic");
    }
}
