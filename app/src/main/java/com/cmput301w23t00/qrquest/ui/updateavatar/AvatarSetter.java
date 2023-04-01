package com.cmput301w23t00.qrquest.ui.updateavatar;


import android.widget.ImageView;


import com.cmput301w23t00.qrquest.R;

public class AvatarSetter {
    public static void setImageResource(String avatarIdString, ImageView imageView){

        int avatarId = Integer.parseInt(avatarIdString);

        if (avatarId == 0){
            imageView.setImageResource(R.drawable.grug);
        } else if (avatarId == 1) {
            imageView.setImageResource(R.drawable.avatar1);
        } else if (avatarId == 2) {
            imageView.setImageResource(R.drawable.avatar2);
        } else if (avatarId == 3) {
            imageView.setImageResource(R.drawable.avatar3);
        } else if (avatarId == 4) {
            imageView.setImageResource(R.drawable.avatar4);
        } else if (avatarId == 5) {
            imageView.setImageResource(R.drawable.avatar5);
        } else if (avatarId == 6) {
            imageView.setImageResource(R.drawable.avatar6);
        } else if (avatarId == 7) {
            imageView.setImageResource(R.drawable.avatar7);
        } else if (avatarId == 8) {
            imageView.setImageResource(R.drawable.avatar8);
        } else if (avatarId == 9) {
            imageView.setImageResource(R.drawable.avatar9);
        } else if (avatarId == 10) {
            imageView.setImageResource(R.drawable.avatar10);
        } else if (avatarId == 11) {
            imageView.setImageResource(R.drawable.avatar11);
        } else if (avatarId == 12) {
            imageView.setImageResource(R.drawable.avatar12);
        }
    }
}
