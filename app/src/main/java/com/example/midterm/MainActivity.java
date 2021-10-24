package com.example.midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.midterm.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    enum Choices {
        ROCK(R.drawable.rock), PAPER(R.drawable.paper), SCISSORS(R.drawable.scissors);

        private static final Random random = new Random();
        private final int imageResId;

        Choices(int imageId) {
            this.imageResId = imageId;
        }

        public boolean isDraw(Choices other) {
            return this == other;
        }

        public boolean isWin(Choices other) {
            return (other == SCISSORS && this == ROCK) || (other.ordinal() - this.ordinal() == -1);
        }

        public static Choices makeRandom() {
            return Choices.values()[random.nextInt(3)];
        }
    }

    private ActivityMainBinding binding;
    private int gameCount = 0;
    private int winCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Í
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonPaper.setOnClickListener(v -> game(Choices.PAPER));
        binding.buttonPaper.setOnClickListener(v -> binding.imageYour.setImageResource(R.drawable.paper));

        binding.buttonScissors.setOnClickListener(v -> game(Choices.SCISSORS));
        binding.buttonRock.setOnClickListener(v -> game(Choices.ROCK));
        binding.buttonReset.setOnClickListener(v -> reset());
        reset();

        int value = 100;
        value = 200;
        Log.i("TEST", "value: " + value);
    }

    public void reset() {
        initView();
        gameCount = 0;
        winCount = 0;
    }

    public void initView() {
        binding.iconPaper.setVisibility(View.VISIBLE);
        binding.iconRock.setVisibility(View.VISIBLE);
        binding.iconScissors.setVisibility(View.VISIBLE);
        binding.buttonReset.setVisibility(View.GONE);
        binding.labelAi.setVisibility(View.INVISIBLE);
        binding.labelYou.setVisibility(View.INVISIBLE);
        binding.imageYour.setVisibility(View.INVISIBLE);
        binding.imageAi.setVisibility(View.INVISIBLE);
        binding.textInfo.setText("게임을 시작합니다");
        binding.message.setText("가위 바위 보!");
    }

    public void gameView() {
        binding.iconPaper.setVisibility(View.INVISIBLE);
        binding.iconRock.setVisibility(View.INVISIBLE);
        binding.iconScissors.setVisibility(View.INVISIBLE);
        binding.buttonReset.setVisibility(View.VISIBLE);
        binding.labelAi.setVisibility(View.VISIBLE);
        binding.labelYou.setVisibility(View.VISIBLE);
        binding.imageYour.setVisibility(View.VISIBLE);
        binding.imageAi.setVisibility(View.VISIBLE);
    }

    public void game(Choices your) {
        gameView();

        Choices ai = Choices.makeRandom();
        binding.imageAi.setImageResource(ai.imageResId);
        binding.imageYour.setImageResource(your.imageResId);

        if (ai.isDraw(your)) {
            binding.message.setText("다시 한번 가위바위보!");
            binding.textInfo.setVisibility(View.INVISIBLE);
            return;
        }
        gameCount++;

        String message = "당신이 졌습니다 😝";
        if (your.isWin(ai)) {
            winCount++;
            message = "당신이 이겼습니다 오예 👍";
        }
        binding.message.setText(message);
        float winningRate = winCount / (float) gameCount * 100;
        String info = String.format("%d번중 %d번 이겼습니다. 승률은 %.2f%%입니다", gameCount, winCount, winningRate);
        binding.textInfo.setVisibility(View.VISIBLE);
        binding.textInfo.setText(info);
    }
}