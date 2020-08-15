package com.example.myguessgame.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myguessgame.R;

public class CheatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.cheatContainer);

        if (fragment == null) {
            CheatActivityFragment cheatActivityFragment = new CheatActivityFragment();
            fragmentManager.beginTransaction().add(R.id.cheatContainer, cheatActivityFragment).
                    commit();
        }
    }

}