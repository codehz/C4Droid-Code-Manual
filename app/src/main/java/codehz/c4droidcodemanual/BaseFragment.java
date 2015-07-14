package codehz.c4droidcodemanual;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.List;

public class BaseFragment extends Fragment {
    private DataAsyncTask dataAsyncTask;
    private int mTarget;
    private RecyclerView mRecyclerView;
    private int color;

    public BaseFragment() {
    }

    public static BaseFragment newInstance(final int target, final int color) {
        BaseFragment fragment = new BaseFragment();
        fragment.mTarget = target;
        fragment.color = color;
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.base_recycler_view);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        dataAsyncTask = new DataAsyncTask(mTarget);
        dataAsyncTask.execute();

        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return null;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }

    private class DataAsyncTask extends AsyncTask<Void, Void, List<CodeRepositories>> {
        private int pos;

        public DataAsyncTask(int _pos) {
            super();
            pos = _pos;
        }

        @Override
        protected List<CodeRepositories> doInBackground(Void... params) {
            Log.d("BaseFragment", "Start" + pos);
            while (AppApplication.Get().repositories.get(pos) == null) {
                try {
                    synchronized (AppApplication.Get().repositories) {
                        AppApplication.Get().repositories.wait();
                    }
                    Log.d("BaseFragment", "wait finish" + pos);
                } catch (InterruptedException e) {
                    Log.d("BaseFragment", "wait interrupted" + pos);
                }
            }
            Log.d("BaseFragment", pos + "End" + AppApplication.Get().repositories.get(pos).size());
            return AppApplication.Get().repositories.get(pos);
        }

        @Override
        protected void onPostExecute(List<CodeRepositories> codeRepositories) {
            super.onPostExecute(codeRepositories);
            mRecyclerView.setAdapter(
                    new RecyclerViewMaterialAdapter(
                            new RecyclerViewAdapter(codeRepositories, mTarget, color)));
            Log.d("BaseFragment", "Set.");
        }
    }
}
