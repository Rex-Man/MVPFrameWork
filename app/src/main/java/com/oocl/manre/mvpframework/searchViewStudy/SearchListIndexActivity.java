package com.oocl.manre.mvpframework.searchViewStudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.oocl.manre.mvpframework.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchListIndexActivity extends AppCompatActivity implements SearchView.SearchViewListener{

    private SideBar sideBar;

    private SearchView searchView;

    private ListView sortListView;

    private SortAdapter sortAdapter;

    private TextView float_letter;
    //private SortAdapter adapter;
    public  List<TruckerModel> truckerModels;
    public  List<TruckerModel> truckerFilterModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_list_index);
        sortListView= (ListView) findViewById(R.id.informationList);
        truckerModels=getTruckerModelList();
        truckerFilterModels=new ArrayList<>();
        truckerFilterModels=deepCopyList(truckerModels);
        searchView= (SearchView) findViewById(R.id.main_search_layout);
        sortAdapter = new SortAdapter(this,truckerFilterModels);
        sortListView.setAdapter(sortAdapter);
        searchView.setSearchViewListener(this);
        sideBar=(SideBar)findViewById(R.id.slideBar);
        float_letter= (TextView) findViewById(R.id.float_letter);
        sideBar.setOnTouchLetterChangeListenner(new SideBar.OnTouchLetterChangeListenner() {

                    @Override
                    public void onTouchLetterChange(boolean isTouched, String s) {

                        int position = sortAdapter.getPositionForSection(s.charAt(0));
                        if(position!=-1) {
                            sortListView.setSelection(position);
                        }
                    }
                });
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(SearchListIndexActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected List<TruckerModel> getTruckerModelList(){

        List<TruckerModel> truckerModelList=new ArrayList<TruckerModel>();

           String[] letters={"A","B","C","D","E","F","G","H","I","G","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        for (String letter:letters) {
            TruckerModel truckerModel=new TruckerModel();
            truckerModel.setName(letter+" trucker ");
            truckerModel.setSortLetters(letter);
            truckerModelList.add(truckerModel);
        }
        return truckerModelList;
    }

    private void getResultData(String text)
    {
        if(text==null||text.isEmpty())
        {
            truckerFilterModels=deepCopyList(truckerModels);
        }else {
            if (truckerFilterModels == null) {
                truckerFilterModels = new ArrayList<TruckerModel>();
            } else {
                truckerFilterModels.clear();
            }
            for (TruckerModel truckerModel : truckerModels) {
                String name = truckerModel.getName().toUpperCase();
                if (name.contains(text.trim().toUpperCase())) {
                    truckerFilterModels.add(truckerModel);
                }
            }
        }
    }

    @Override
    public void onSearch(String text) {
        //更新result数据
        getResultData(text);
        //lvResults.setVisibility(View.VISIBLE);
        //第一次获取结果 还未配置适配器
        if (sortListView.getAdapter() == null) {
            //获取搜索数据 设置适配器
            sortListView.setAdapter(sortAdapter);
        } else {
            //更新搜索数据
            sortAdapter.notifyDataSetChanged();
        }
        Toast.makeText(this, "完成搜素", Toast.LENGTH_SHORT).show();
    }
    public static <T> List<T> deepCopyList(List<T> src)
    {

        try
        {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<T> dest = (List<T>) in.readObject();
            return dest;
        }
        catch (IOException e)
        {
             e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
       return src;
    }
}
