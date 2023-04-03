package com.cmput301w23t00.qrquest.ui.updateavatar;


import android.widget.ImageView;


import com.cmput301w23t00.qrquest.R;

import java.util.ArrayList;

public class AvatarUtility {
    public static int getAvatarImageResource(int avatarId) {
        if (avatarId == 0){
            return R.drawable.grug;
        } else if (avatarId == 1) {
            return R.drawable.avatar1;
        } else if (avatarId == 2) {
            return R.drawable.avatar2;
        } else if (avatarId == 3) {
            return R.drawable.avatar3;
        } else if (avatarId == 4) {
            return R.drawable.avatar4;
        } else if (avatarId == 5) {
            return R.drawable.avatar5;
        } else if (avatarId == 6) {
            return R.drawable.avatar6;
        } else if (avatarId == 7) {
            return R.drawable.avatar7;
        } else if (avatarId == 8) {
            return R.drawable.avatar8;
        } else if (avatarId == 9) {
            return R.drawable.avatar9;
        } else if (avatarId == 10) {
            return R.drawable.avatar10;
        } else if (avatarId == 11) {
            return R.drawable.avatar11;
        } else if (avatarId == 12) {
            return R.drawable.avatar12;
        }

        // -1 means an error occured
        return -1;
    }

    public static ArrayList<Integer> getNonUseAvatarIds(int avatarId) {
        ArrayList<Integer> arrayList = new ArrayList();
        if (avatarId != 0) {
            arrayList.add(0);
        }

        if (avatarId != 1) {
            arrayList.add(1);
        }

        if (avatarId != 2) {
            arrayList.add(2);
        }

        if (avatarId != 3) {
            arrayList.add(3);
        }

        if (avatarId != 4) {
            arrayList.add(4);
        }

        if (avatarId != 5) {
            arrayList.add(5);
        }

        if (avatarId != 6) {
            arrayList.add(6);
        }

        if (avatarId != 7) {
            arrayList.add(7);
        }

        if (avatarId != 8) {
            arrayList.add(8);
        }

        if (avatarId != 9) {
            arrayList.add(9);
        }

        if (avatarId != 10) {
            arrayList.add(10);
        }

        if (avatarId != 11) {
            arrayList.add(11);
        }

        if (avatarId != 12) {
            arrayList.add(12);
        }

        return arrayList;
    }
}
