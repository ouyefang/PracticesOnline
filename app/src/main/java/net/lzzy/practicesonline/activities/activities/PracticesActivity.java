package net.lzzy.practicesonline.activities.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import net.lzzy.practicesonline.R;
import net.lzzy.practicesonline.activities.fragments.PracticesFragment;
import net.lzzy.practicesonline.activities.models.PracticeFactory;
import net.lzzy.practicesonline.activities.network.DetectWebService;
import net.lzzy.practicesonline.activities.utils.AppUtils;
import net.lzzy.practicesonline.activities.utils.ViewUtils;

import static net.lzzy.practicesonline.activities.activities.QuestionActivity.EXTRA_PRACTICE_ID;

/**
 *
 * @author lzzy_gxy
 * @date 2019/4/16
 * Description:
 */
public class PracticesActivity extends BaseActivity implements PracticesFragment.PracticeSelectedListener {
    public static final String EXTRA_LOCAL_COUNT = "localCount";
    private ServiceConnection connection;
    public static final String EXTRA_API_ID = "extraApiId";
    private boolean refresh=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        if (getIntent()!=null){
            refresh = getIntent().getBooleanExtra(DetectWebService.EXTRA_REFRESH,false);
        }
        connection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DetectWebService.DetectWebBinder binder= (DetectWebService.DetectWebBinder) service;
                binder.detect();
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        int localCount= PracticeFactory.getInstance().get().size();
        Intent intent=new Intent(this, DetectWebService.class);
        intent.putExtra(EXTRA_LOCAL_COUNT,localCount);
        bindService(intent,connection,BIND_AUTO_CREATE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refresh){
            ((PracticesFragment)getFragment()).startRefresh();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("退出应用吗？")
                .setPositiveButton("退出",(dialog, which) -> AppUtils.exit())
                        .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }


    private void initViews() {
        SearchView search=findViewById(R.id.bar_title_search);
        search.setOnQueryTextListener(new ViewUtils.AbstractQueryListener() {
            @Override
            protected void handleQuery(String kw) {
                ((PracticesFragment)getFragment()).search(kw);
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_practices;
    }

    @Override
    protected int getContainerId() {
        return R.id.activity_practices_container;
    }

    @Override
    protected Fragment createFragment() {
        return new PracticesFragment();
    }


    @Override
    public void onPracticeSelected(String practiceId, int apiId) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(EXTRA_PRACTICE_ID, practiceId);
        intent.putExtra(EXTRA_API_ID, apiId);
        startActivity(intent);
    }
}
