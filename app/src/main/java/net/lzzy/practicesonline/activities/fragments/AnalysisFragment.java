package net.lzzy.practicesonline.activities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import androidx.annotation.Nullable;

import net.lzzy.practicesonline.R;
import net.lzzy.practicesonline.activities.models.view.QuestionResult;
import net.lzzy.practicesonline.activities.utils.views.BarChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzzy_gxy on 2019/5/16.
 * Description:
 */


public class AnalysisFragment extends BaseFragment {

    List<QuestionResult> results;
    private OnCharSelectedListener listenter;
    public static final String ARGS_RESULT = "argResult";
    private static  final  String[] HORIZONTAL_AXIS={"正确","少选","多选","错选"};

    public static AnalysisFragment newInstance(List<QuestionResult> results) {
        AnalysisFragment fragment = new AnalysisFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGS_RESULT, (ArrayList<? extends Parcelable>) results);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            results = getArguments().getParcelableArrayList(ARGS_RESULT);
        }
    }

    @Override
    protected void populate() {
        TextView tvView = find(R.id.fragment_analysis_tv_go);
        tvView.setOnClickListener(v -> listenter.gotoGrid());
    }
    BarChartView barChart=find(R.id.fragment_analysis_bar);
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_analysis;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listenter= (OnCharSelectedListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"必须实现OnGridSkipListener接口");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listenter=null;

    }

    @Override
    public void search(String kw) {

    }
    public interface  OnCharSelectedListener{
        void gotoGrid();
    }


}
