package codehz.c4droidcodemanual;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.kenny.snackbar.SnackBar;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

public class BaseFragment extends Fragment {
    private static final String ARG_TARGET = "target";

    private CodeCategories mTarget;
    private RecyclerView mRecyclerView;
    private List<DataModel> mContentItems = new ArrayList<>();

    public BaseFragment() {
    }

    public static BaseFragment newInstance(final CodeCategories target) {
        BaseFragment fragment = new BaseFragment();
        fragment.mTarget = target;
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        BmobQuery<CodeRepositories> codeRepositoriesBmobQuery = new BmobQuery<>();
        codeRepositoriesBmobQuery.addWhereEqualTo("Category", mTarget);
        //codeRepositoriesBmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        codeRepositoriesBmobQuery.findObjects(getActivity(), new FindListener<CodeRepositories>() {
            @Override
            final public void onSuccess(List<CodeRepositories> list) {
                for (int i = 0; i < list.size(); i++) {
                    mContentItems.add(new DataModel(list.get(i).getTitle(),
                            list.get(i).getPreview()));
                    mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new RecyclerViewAdapter(mContentItems)));
                }
            }

            @Override
            public void onError(int i, String s) {
                SnackBar.show(getActivity(), String.format(getString(R.string.error_when_query), s));
            }
        });
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
}
