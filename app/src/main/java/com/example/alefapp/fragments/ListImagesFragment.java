package com.example.alefapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.alefapp.utils.AppUtility;
import com.example.alefapp.services.NetworkService;
import com.example.alefapp.R;
import com.example.alefapp.adapters.RecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListImagesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String FRAGMENT_TAG = "imgListFrag";

    private RecycleViewAdapter recycleViewAdapter;
    private OnClickImageListener clickImageListener;
    private SwipeRefreshLayout swipeRefreshLayout;

    public void setOnClickImageListener(OnClickImageListener listener) {
        clickImageListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_list, null, false);
        bindRecycleView(view);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                // Fetching data from server
                getURlsFromServer();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getURlsFromServer();
    }


    private void bindRecycleView(View view) {
        RecyclerView imageRecyclerView = view.findViewById(R.id.recyclerView);
        imageRecyclerView.setHasFixedSize(true);

        if (new AppUtility().isTabletDevice(view.getContext())) {
             GridLayoutManager gridLayoutManager = (GridLayoutManager) imageRecyclerView.getLayoutManager();
            if (gridLayoutManager != null) {
                gridLayoutManager.setSpanCount(3);
            }
        }
        recycleViewAdapter = new RecycleViewAdapter();
        recycleViewAdapter.setOnUserClickListener(new RecycleViewAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(int position) {
                if (clickImageListener == null) {
                    Log.d("ALEF", "onUserClick: " + "lol null");
                }
                clickImageListener.onClickImage(recycleViewAdapter.getElements().get(position));
            }
        });
        imageRecyclerView.setAdapter(recycleViewAdapter);
    }

    private void getURlsFromServer() {
        swipeRefreshLayout.setRefreshing(true);
        NetworkService.getInstance().requestURLsList(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                recycleViewAdapter.setUrlsArray((ArrayList<String>) response.body());
                recycleViewAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.d("RequestFail", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onRefresh() {
        getURlsFromServer();
    }

   public interface OnClickImageListener {
        public void onClickImage(String urlString);
    }
}
