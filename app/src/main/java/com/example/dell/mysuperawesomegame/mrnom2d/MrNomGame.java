package com.example.dell.mysuperawesomegame.mrnom2d;

import com.badlogic.androidgames.framework.AndroidGame;
import com.badlogic.androidgames.framework.Screen;

public class MrNomGame extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);

    }
}/*extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mr_nom_game);
    }
}*/
