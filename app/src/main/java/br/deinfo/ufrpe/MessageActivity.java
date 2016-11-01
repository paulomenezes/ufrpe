package br.deinfo.ufrpe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import org.parceler.Parcels;

import java.util.List;

import br.deinfo.ufrpe.models.Message;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        List<Message> messages = Parcels.unwrap(getIntent().getParcelableExtra("messages"));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
    }
}
