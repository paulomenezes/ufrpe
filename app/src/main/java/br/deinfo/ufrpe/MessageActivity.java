package br.deinfo.ufrpe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.parceler.Parcels;

import java.util.Collections;
import java.util.List;

import br.deinfo.ufrpe.adapters.ChatAdapter;
import br.deinfo.ufrpe.models.Message;
import br.deinfo.ufrpe.models.Messages;
import br.deinfo.ufrpe.models.SendMessage;
import br.deinfo.ufrpe.services.AVAService;
import br.deinfo.ufrpe.services.Requests;
import br.deinfo.ufrpe.utils.CompareMessages;
import br.deinfo.ufrpe.utils.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    private EditText mType;
    private Button mSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        final List<Message> messages = Parcels.unwrap(getIntent().getParcelableExtra("messages"));

        final AVAService avaServices = Requests.getInstance().getAVAService();
        Call<Messages> messagesCall = avaServices.getMessages(messages.get(0).getUseridfrom(),
                Session.getUser(this).getAvaID(), 0, 50, 0, "conversations", 1, Requests.FUNCTION_GET_MESSAGES, Session.getUser(this).getToken());

        final ChatAdapter adapter = new ChatAdapter(messages);

        messagesCall.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                messages.addAll(response.body().getMessages());
                Collections.sort(messages, new CompareMessages());

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {

            }
        });

        setTitle(messages.get(0).getUserfromfullname());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager linear = new LinearLayoutManager(this);
        linear.setReverseLayout(true);

        recyclerView.setLayoutManager(linear);
        recyclerView.setAdapter(adapter);

        mType = (EditText) findViewById(R.id.type);
        mSend = (Button) findViewById(R.id.send);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mType.getText().toString().isEmpty() || mType.getText().toString().replaceAll(" ", "").length() == 0) {
                    mType.setError(getString(R.string.required_field));
                } else {
                    Call<List<SendMessage>> send = avaServices.sendMessage(messages.get(0).getUseridto(), mType.getText().toString(), 1, Requests.FUNCTION_SEND_MESSAGE, Session.getUser(MessageActivity.this).getToken());
                    send.enqueue(new Callback<List<SendMessage>>() {
                        @Override
                        public void onResponse(Call<List<SendMessage>> call, Response<List<SendMessage>> response) {
                            if (response.body() != null && response.body().size() > 0) {
                                Message newMessage = new Message();
                                newMessage.setId(response.body().get(0).getMsgid());
                                newMessage.setSmallmessage(mType.getText().toString());
                                newMessage.setUseridfrom(Session.getUser(MessageActivity.this).getAvaID());
                                newMessage.setTimecreated(System.currentTimeMillis() / 1000);

                                messages.add(0, newMessage);
                                adapter.notifyDataSetChanged();

                                mType.setText("");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<SendMessage>> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
