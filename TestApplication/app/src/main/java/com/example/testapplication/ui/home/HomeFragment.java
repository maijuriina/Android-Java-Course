package com.example.testapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.R;

public class HomeFragment extends Fragment implements View.OnClickListener  {

    Button gameButton; // introduce game button
    Button guessGameButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        gameButton = root.findViewById(R.id.gameButton);
        guessGameButton = root.findViewById(R.id.guessButton);
        gameButton.setOnClickListener(this);
        guessGameButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gameButton:
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);
            case R.id.guessButton:
                Intent guessIntent = new Intent(getActivity(), GuessActivity.class);
                startActivity(guessIntent);
        }
    }
}