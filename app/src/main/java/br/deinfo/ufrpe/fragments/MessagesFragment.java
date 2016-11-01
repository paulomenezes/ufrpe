package br.deinfo.ufrpe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.adapters.MessagesAdapter;
import br.deinfo.ufrpe.models.AVAUser;
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

        final AVAService avaServices = Requests.getInstance().getAVAService();
        Call<Messages> messagesCall = avaServices.getMessages(Session.getUser().getAvaID(),
                0, 0, 50, 1, "conversations", 1, Requests.FUNCTION_GET_MESSAGES, Session.getUser().getToken());

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        messagesCall.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                final Messages messages = response.body();
                final MessagesAdapter adapter = new MessagesAdapter(messages.getMessages());
                recyclerView.setAdapter(adapter);

                final Set<Integer> userIds = new LinkedHashSet<Integer>();
                for (int i = 0; i < messages.getMessages().size(); i++) {
                    userIds.add(messages.getMessages().get(i).getUseridfrom());
                }

                int[] userids = new int[userIds.size()];
                for (int i = 0; i < userids.length; i++) {
                    userids[i] = (int)userIds.toArray()[i];
                }

                Call<List<AVAUser>> usersCall = avaServices.getUserById(userids, Requests.FUNCTION_GET_USER, Session.getUser().getToken());
                usersCall.enqueue(new Callback<List<AVAUser>>() {
                    @Override
                    public void onResponse(Call<List<AVAUser>> call, Response<List<AVAUser>> response) {
                        List<AVAUser> users = response.body();

                        for (int i = 0; i < messages.getMessages().size(); i++) {
                            for (int j = 0; j < users.size(); j++) {
                                if (messages.getMessages().get(i).getUseridfrom() == users.get(j).getId()) {
                                    messages.getMessages().get(i).setContexturl(users.get(j).getProfileimageurl());
                                    break;
                                }
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<AVAUser>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {

            }
        });

        return view;
    }
}
