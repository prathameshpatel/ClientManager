package io.github.prathameshpatel.clientmanager;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.prathameshpatel.clientmanager.db.AppDatabase;
import io.github.prathameshpatel.clientmanager.entity.Client;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoritesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private AppDatabase mdb;
    public List<Client> mclientList;

//    private OnFragmentInteractionListener mListener;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mdb = AppDatabase.getAppDatabase(getActivity().getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        new DatabaseAsync().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View favoriteFragmantView = inflater.inflate(R.layout.fragment_favorites, container, false);
        mRecyclerView = favoriteFragmantView.findViewById(R.id.favorite_recycler_view);
        new DatabaseAsync().execute();
        //The RecyclerView.LayoutManager defines how elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        return favoriteFragmantView;
    }

    private class DatabaseAsync extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            mdb.beginTransaction();
            try {
                FavoritesFragment.this.mclientList = mdb.clientDao().loadFavoriteFullNames(1);
                mdb.setTransactionSuccessful();
            } finally {
                mdb.endTransaction();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Define an adapter
            mAdapter = new AllRecyclerAdapter(mclientList);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class RemoveFavoriteAsync extends AsyncTask<Integer,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mdb = AppDatabase.getAppDatabase(getActivity().getApplicationContext());
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mdb.beginTransaction();
            try {
                mdb.clientDao().removeFavorite(integers[0],0);
                mdb.setTransactionSuccessful();
            } finally {
                mdb.endTransaction();
            }
            return null;
        }

//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            //Define an adapter
////            mAdapter = new AllRecyclerAdapter(mclientList);
////            mRecyclerView.setAdapter(mAdapter);
//            mAdapter.notifyDataSetChanged();
//        }
    }

    public void removeFavorite(int clientid) {
        new RemoveFavoriteAsync().execute(clientid);
    }

    private class AddFavoriteAsync extends AsyncTask<Integer,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mdb = AppDatabase.getAppDatabase(getContext());
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mdb.beginTransaction();
            try {
                mdb.clientDao().addFavorite(integers[0],1);
                mdb.setTransactionSuccessful();
            } finally {
                mdb.endTransaction();
            }
            return null;
        }
    }

    public void addFavorite(int clientid) {
        new AddFavoriteAsync().execute(clientid);
    }

    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/

}
