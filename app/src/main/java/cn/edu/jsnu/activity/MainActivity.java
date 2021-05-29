package cn.edu.jsnu.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import cn.edu.jsnu.R;
import cn.edu.jsnu.fragment.CollectFragment;
import cn.edu.jsnu.fragment.MeFragment;
import cn.edu.jsnu.fragment.SearchFragment;
import cn.edu.jsnu.fragment.ShopListFragment;

public class MainActivity extends BaseActivity {

    private RadioButton ra_shouye_bt, ra_collect_bt, ra_search_bt, ra_wo_bt;
    private Fragment shopListFragment, collectFragment, searchFragment, meFragment;
    private FragmentManager fgManager;
    private int i = 0;

    protected void init(Context context) {
        setContentView(R.layout.activity_main);
        fgManager = getFragmentManager();
        ra_shouye_bt = (RadioButton) findViewById(R.id.ra_shouye_bt);
        ra_collect_bt = (RadioButton) findViewById(R.id.ra_collect_bt);
        ra_search_bt = (RadioButton) findViewById(R.id.ra_search_bt);
        ra_wo_bt = (RadioButton) findViewById(R.id.ra_wo_bt);

        shopListFragment = new ShopListFragment();
        collectFragment = new CollectFragment();
        searchFragment = new SearchFragment();
        meFragment = new MeFragment();
        //默认进入首页
        changeFrament(shopListFragment, null, "shouyeFragment");
        ra_shouye_bt.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.shouye1, 0, 0);

        ra_shouye_bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shopListFragment = new ShopListFragment();
                changeFrament(shopListFragment, null, "shouyeFragment");
                changeRadioButtonImage(v.getId());
            }
        });
        ra_collect_bt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                collectFragment = new CollectFragment();
                changeFrament(collectFragment, null, "collectFragment");
                changeRadioButtonImage(v.getId());
            }
        });
        ra_search_bt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                searchFragment = new SearchFragment();
                changeFrament(searchFragment, null, "searchFragment");
                changeRadioButtonImage(v.getId());
            }
        });
        ra_wo_bt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                meFragment = new MeFragment();
                changeFrament(meFragment, null, "meFragment");
                changeRadioButtonImage(v.getId());
            }
        });
    }

    public void changeFrament(Fragment fragment, Bundle bundle, String tag) {
        for (int i = 0, count = fgManager.getBackStackEntryCount(); i < count; i++) {
            fgManager.popBackStack();
        }
        FragmentTransaction fg = fgManager.beginTransaction();
        fragment.setArguments(bundle);
        fg.add(R.id.fragmentRoot, fragment, tag);
        fg.addToBackStack(tag);
        fg.commit();
    }

    public void changeRadioButtonImage(int btids) {
        int[] imageh = {R.drawable.shouye, R.drawable.collect,
                R.drawable.chazhao, R.drawable.gerenxinxi};
        int[] imagel = {R.drawable.shouye1, R.drawable.collect1,
                R.drawable.chazhao1, R.drawable.gerenxinxi1};
        int[] rabt = {R.id.ra_shouye_bt, R.id.ra_collect_bt, R.id.ra_search_bt,
                R.id.ra_wo_bt};
        switch (btids) {
            case R.id.ra_shouye_bt:
                changeImage(imageh, imagel, rabt, 0);
                break;
            case R.id.ra_collect_bt:
                changeImage(imageh, imagel, rabt, 1);
                break;
            case R.id.ra_wo_bt:
                changeImage(imageh, imagel, rabt, 3);
                break;
            case R.id.ra_search_bt:
                changeImage(imageh, imagel, rabt, 2);
                break;
            default:
                break;
        }
    }
    public void changeImage(int[] image1, int[] image2, int[] rabtid, int index) {
        for (int i = 0; i < image1.length; i++) {
            if (i != index) {
                ((RadioButton) findViewById(rabtid[i]))
                        .setCompoundDrawablesWithIntrinsicBounds(0, image1[i],
                                0, 0);
            } else {
                ((RadioButton) findViewById(rabtid[i]))
                        .setCompoundDrawablesWithIntrinsicBounds(0, image2[i],
                                0, 0);
            }
        }
    }
}
