package br.deinfo.ufrpe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.adapters.MessagesAdapter;
import br.deinfo.ufrpe.models.Messages;
import br.deinfo.ufrpe.services.AVAService;
import br.deinfo.ufrpe.services.Requests;
import br.deinfo.ufrpe.utils.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paulo on 30/10/2016.
 */

public class MessagesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_messages, container, false);

        AVAService avaServices = Requests.getInstance().getAVAService();
        Call<Messages> messagesCall = avaServices.getMessages(Session.getUser().getAvaID(),
                0, 0, 50, 1, "conversations", 1, Requests.FUNCTION_GET_MESSAGES, Session.getUser().getToken());

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        messagesCall.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                Messages messages = response.body();
                recyclerView.setAdapter(new MessagesAdapter(messages.getMessages()));
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {

            }
        });

        return view;
    }
}
