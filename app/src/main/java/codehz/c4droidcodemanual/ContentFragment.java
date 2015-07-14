package codehz.c4droidcodemanual;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;

public class ContentFragment extends Fragment {
    private CodeRepositories repository;

    public ContentFragment() {
    }

    public static ContentFragment newInstance(final CodeRepositories repository) {
        ContentFragment fragment = new ContentFragment();
        fragment.repository = repository;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextCardView textCardView = (TextCardView) view.findViewById(R.id.main_view);
        textCardView.setContent(repository.getContent());
        textCardView.decode();
        //textCardView.setPic();
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), textCardView, null);
    }
}
