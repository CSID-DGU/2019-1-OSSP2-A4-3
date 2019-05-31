package com.example.yout1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity {
YouTubePlayerView youtubeView;
Button button;
YouTubePlayer.OnInitializedListener listener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.youtubeButton);
        youtubeView=(YouTubePlayerView) findViewById(R.id.youtubeView);

listener=new YouTubePlayer.OnInitializedListener(){
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        String a="VBbblm6wuz4";
        youTubePlayer.loadVideo(a);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
};
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        youtubeView.initialize("AIzaSyClU4tY3QrdJBWtfpcHOuY4Cmvw6SQAsO8",listener);
    }
});

    }
}
