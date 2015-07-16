package codehz.c4droidcodemanual;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;

public class ContentFragment extends Fragment {
    private int category, num;

    public ContentFragment() {
    }

    public static ContentFragment newInstance(final int category, final int num) {
        ContentFragment fragment = new ContentFragment();
        fragment.category = category;
        fragment.num = num;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
        textCardView.setContent(
                AppApplication.Get().repositories.get(category).get(num).getContent());
        textCardView.decode();
        //textCardView.setPic();
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), textCardView, null);
    }
}
