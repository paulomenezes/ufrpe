package br.deinfo.ufrpe.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.adapters.MessagesAdapter;
import br.deinfo.ufrpe.adapters.SubjectAdapter;
import br.deinfo.ufrpe.models.AVAUser;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.Messages;
import br.deinfo.ufrpe.services.AVAService;
import br.deinfo.ufrpe.services.Requests;
import br.deinfo.ufrpe.utils.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by paulo on 11/03/2017.
 */

public class SubjectFragment  extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_subject, container, false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Course> courses = Session.getUser(getActivity()).getCourses();
        HashMap<String, List<Course>> coursesPerPeriod = new HashMap<>();

        for (Course course: courses) {
            String[] name = course.getFullname().split(" \\| ");
            if (name.length > 1) {
                if (!coursesPerPeriod.containsKey(name[0])) {
                    coursesPerPeriod.put(name[0], new ArrayList<Course>());
                }

                coursesPerPeriod.get(name[0]).add(course);
            }
        }

        SubjectAdapter subjectAdapter = new SubjectAdapter(coursesPerPeriod);
        recyclerView.setAdapter(subjectAdapter);

        return view;
    }
}

