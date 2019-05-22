package net.lzzy.practicesonline.activities.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.formatter.IFillFormatter;

import net.lzzy.practicesonline.R;
import net.lzzy.practicesonline.activities.fragments.ChartFragment;
import net.lzzy.practicesonline.activities.fragments.GridFragment;
import net.lzzy.practicesonline.activities.models.view.QuestionResult;

import java.util.List;

/**
 *
 * @author lzzy_gxy
 * @date 2019/5/13
 * Description:
 */
public class ResultActivity extends BaseActivity implements GridFragment.OnGridSKipListenter,
        ChartFragment.OnChartSkipListener
{
    public static final String POSITION="position";
    public static final int RESULT_CODE=1;
    public static final String PRACTICES_ID="practiceId";
    public static final int RESULT_CODE_PRACTICE=2;
    private List<QuestionResult> results;
     String practiceId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        practiceId=getIntent().getStringExtra(QuestionActivity.EXTRA_PRACTICE_ID);
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_result;
    }

    @Override
    protected int getContainerId() {
        return R.id. activity_result_container;
    }

    @Override
    protected Fragment createFragment() {
        results=getIntent().getParcelableArrayListExtra(QuestionActivity.EXTRA_RESULT);

        return GridFragment.newInstance(results);
    }

    @Override
    public void gotoGrid() {
        getManager().beginTransaction().replace(R.id.activity_result_container,
                GridFragment.newInstance(results)).commit();
    }

    @Override
    public void onGridSKip(int position) {
        Intent intent=new Intent(this,QuestionActivity.class);
        intent.putExtra(POSITION,position);
        setResult(RESULT_CODE,intent);
        finish();

    }

    @Override
    public void gotoChart() {
        getManager().beginTransaction().replace(R.id.activity_result_container,
                ChartFragment.newInstance(results)).commit();

    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("确定要返回题目吗？")
                .setNeutralButton("退出",(dialog, which) -> {

                    finish();
                })
                .setNegativeButton("章节列表",(dialog, which) -> {
                    startActivity(new Intent(this,PracticesActivity.class));
                    finish();
                })
                .setPositiveButton("查看收藏",(dialog, which) ->{
                    Intent intent=new Intent(this,QuestionActivity.class);
                    intent.putExtra(PRACTICES_ID,practiceId);
                    setResult(RESULT_CODE_PRACTICE,intent);
                    finish();
                } )
                .show();
    }
}
