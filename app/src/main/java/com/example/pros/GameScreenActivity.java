package com.example.pros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameScreenActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private int windowHeight, windowWidth, userCurrentSkinImageId;
    private User currentUser;
    private GameView gameView;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference().child("Pros").child("users").child(firebaseUser.getUid());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
                userCurrentSkinImageId = currentUser.getChosenSkinImageId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        frameLayout = findViewById(R.id.frameLayout_gameScreen_gameFrameLayout);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        windowHeight = frameLayout.getHeight();
        windowWidth = frameLayout.getWidth();
        gameView = new GameView(this, windowHeight, windowWidth, userCurrentSkinImageId);
        frameLayout.addView(gameView);
    }

//    @Override
//    public void onPause(){
//        super.onPause();
//        gameView.gameTread.interrupt();
//        finish();
//    }

//    @Override
//    public void onBackPressed() {
//
//    }

}
