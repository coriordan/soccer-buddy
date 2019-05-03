package com.madein75.soccerbuddy.ui;

import com.google.firebase.firestore.DocumentSnapshot;

public interface OnItemClickListener {
    void onItemClick(DocumentSnapshot documentSnapshot, int position);
}
