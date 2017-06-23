package test.ruili.com.universaltool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import test.ruili.com.universaltool.Bean.Tree;
import test.ruili.com.universaltool.Dao.TreeDBDao;
import test.ruili.com.universaltool.Utils.ToastUtil;

public class MainActivity extends AppCompatActivity {

    private Button btn_open_browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListen();

//        SQLiteDemo();
    }


    private void initView(){
        btn_open_browser = (Button)findViewById(R.id.btn_open_browser);
    }

    private void initListen(){
        btn_open_browser.setOnClickListener(my_Listener);
    }

    private View.OnClickListener my_Listener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btn_open_browser:
                    Intent intent = new Intent("com.example.baidu");
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mian,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                ToastUtil.showShort(this,"add_item");
                break;
            case R.id.remove_item:
                ToastUtil.showShort(this,"remove_item");
                break;
        }
        return true;
    }

    private void SQLiteDemo(){
        TreeDBDao mDBDao = new TreeDBDao(MainActivity.this);//实例化对象
        mDBDao.openDataBase();//打开数据库，并进行建表（若还没建表）

        //增删改查操作
        mDBDao.insertData(new Tree("GreenTree", 12, 2321.5f));//增加数据
        mDBDao.deleteData(1);//删除数据
        mDBDao.updateData(1, new Tree("RedTree", 20, 5200f));//更新数据
        mDBDao.deleteAllData();//删除所有数据

        List<Tree> list = mDBDao.queryData(1);//查询id为1的数据
        Log.v("-->", list.get(0).toString());

        List<Tree> lists = mDBDao.queryDataList();//查询所有数据
        for (Tree tree : lists) {
            Log.v("-->", tree.toString());
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
