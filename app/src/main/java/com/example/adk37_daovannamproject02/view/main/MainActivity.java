package com.example.adk37_daovannamproject02.view.main;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.adk37_daovannamproject02.R;
import com.example.adk37_daovannamproject02.SaveData.SQLHelper;
import com.example.adk37_daovannamproject02.adapter.AdapterListView;
import com.example.adk37_daovannamproject02.adapter.AdapterSlide;
import com.example.adk37_daovannamproject02.databinding.ActivityMainBinding;
import com.example.adk37_daovannamproject02.model.ObjectACity;
import com.example.adk37_daovannamproject02.uliti.JobServices;
import com.example.adk37_daovannamproject02.view.search.ViewMap;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class MainActivity extends AppCompatActivity implements InterfaceMain {
    private static final String TAG = "MainActivity";
    AdapterSlide myadapter;
    ActivityMainBinding binding;
    PresenterMain presenterMain;
    ArrayList<ObjectACity> objectACityfulls = new ArrayList<>();
    int CorF = 0;
    Double CorFDo = 274.15;
    //Số trang
    int pagenumber;
    //Bàn phím
    InputMethodManager imm;
    //Lấy vị trí hiện tại
    static boolean GPS;
    //Database
    SQLHelper sqlHelper;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        presenterMain = new PresenterMain(this, MainActivity.this);

        presenterMain.progressBar = findViewById(R.id.progress);

        sqlHelper = new SQLHelper(getBaseContext());
        //Bắt sự kiện nhấn enter bàn phím ảo
        imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        //Lấy dữ liệu từ database cho màn hình khởi động
        whenOpenApp();

        clickImgMenu();

        clickBtnHuy();

        pressEnter();

        clickImgSearch();

        clickImgBackListView();

        itemClickListview();
//        if (!isJobServiceOn()) {
//            ServiceRun();
//        }
    }

    private void whenOpenApp() {
        if (sqlHelper.getAllProduct() != null && sqlHelper.getAllProduct().get(0).getUnit() != null) {
            if (sqlHelper.getAllProduct().get(0).getUnit().equals("°C")) {
                setUnitlIsC(true);
            } else {
                setUnitlIsC(false);
            }
            presenterMain.loadByLoacation(CorFDo);
            objectACityfulls.addAll(sqlHelper.getAllProduct());
            binding.tvTimeLoad.setText(objectACityfulls.get(0).getTimeupdate());
            setAdapterNew(true);
            presenterMain.progressBar.setVisibility(View.GONE);
        }
        //Nếu data trống thì là lần chạy đầu tiên
        if (objectACityfulls.size() == 0) {
            setUnitlIsC(true);
            presenterMain.saveNameCity.add("London");
            presenterMain.saveNameCity.add("Sơn La");
            presenterMain.loadByLoacation(CorFDo);
            presenterMain.loadFull(presenterMain.saveNameCity);
        }
    }

    private void clickImgMenu() {
        binding.imgmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Tạo popupmenu
                Context wrapper = new ContextThemeWrapper(getBaseContext(), R.style.PopupMenu);
                final android.widget.PopupMenu popupMenu = new PopupMenu(wrapper, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                //Hiện độ C
                if (CorF == 1) {
                    popupMenu.getMenu().findItem(R.id.menuDoC).setVisible(true);
                    popupMenu.getMenu().findItem(R.id.menuDoF).setVisible(false);
                    //Hiện độ F
                } else {
                    popupMenu.getMenu().findItem(R.id.menuDoC).setVisible(false);
                    popupMenu.getMenu().findItem(R.id.menuDoF).setVisible(true);
                }
                //Lấy trang hiện tại
                pagenumber = binding.viewpager.getCurrentItem();
                //Thêm hay xóa
                int addOrDelete = 0;
                //Kiểm tra vị trí bằng id
                for (int i = 0; i <sqlHelper.getAllProduct().size(); i++) {
                    if (objectACityfulls.get(pagenumber).getID() ==sqlHelper.getAllProduct().get(i).getID()) {
                        addOrDelete = 1;
                        break;
                    }
                }
                if (addOrDelete == 0) {
                    //Hiện thêm
                    popupMenu.getMenu().findItem(R.id.menuAdd).setVisible(true);
                    popupMenu.getMenu().findItem(R.id.menuXoa).setVisible(false);
                } else if (addOrDelete != 0 && pagenumber == 0) {
                    //Khi có trong danh sách nhưng là vị trí hiện tại thì k hiện thêm hay xóa
                    popupMenu.getMenu().findItem(R.id.menuAdd).setVisible(false);
                    popupMenu.getMenu().findItem(R.id.menuXoa).setVisible(false);
                } else {
                    //Hiện xóa
                    popupMenu.getMenu().findItem(R.id.menuAdd).setVisible(false);
                    popupMenu.getMenu().findItem(R.id.menuXoa).setVisible(true);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuUpdate:
                                menuUpdate();
                                break;
                            case R.id.menuDoC:
                                changeUnitlIsC(true);
                                break;
                            case R.id.menuDoF:
                                changeUnitlIsC(false);
//                                save(objectACityfulls);
                                break;
                            case R.id.menuSearch:
                                binding.linesearch.setVisibility(View.VISIBLE);
                                //Hiện bàn phím android
                                imm.showSoftInput(binding.edtsearch, InputMethodManager.SHOW_IMPLICIT);
                                break;
                            case R.id.menuSearchByMap:
                                Intent intent = new Intent(MainActivity.this, ViewMap.class);
                                intent.putExtra("lat", objectACityfulls.get(binding.viewpager.getCurrentItem()).getLat());
                                intent.putExtra("lon", objectACityfulls.get(binding.viewpager.getCurrentItem()).getLon());
                                intent.putExtra("CorF", CorFDo);
                                //Gửi đi bị đóng app
                                intent.putExtra("objFull", objectACityfulls);
                                startActivityForResult(intent, 115);
                                break;
                            case R.id.menuAdd:
                                presenterMain.saveNameCity.add(objectACityfulls.get(pagenumber).getNameCity());
                                presenterMain.arrayID.add(objectACityfulls.get(pagenumber).getID());
                                save(objectACityfulls);
                                break;
                            case R.id.menuXoa:
                                presenterMain.objectACityfull.clear();
                                int xoa = pagenumber;
                                objectACityfulls.remove(pagenumber);
                                presenterMain.objectACityfull.addAll(objectACityfulls);
                                save(objectACityfulls);
                                setAdapterNew(false);
                                if (xoa <= presenterMain.saveNameCity.size()) {
                                    binding.viewpager.setCurrentItem(xoa);
                                } else {
                                    binding.viewpager.setCurrentItem(xoa - 1);
                                }
                                break;
                            case R.id.menuList:
                                binding.relTong.setVisibility(View.GONE);
                                binding.linList.setVisibility(View.VISIBLE);
                                AdapterListView adapterListView = new AdapterListView(MainActivity.this, R.layout.item_list, objectACityfulls);
                                binding.lvList.setAdapter(adapterListView);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
    private void clickImgSearch() {
        binding.imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
                imm.hideSoftInputFromWindow(binding.tvHuy.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        });
    }

    private void pressEnter() {
        binding.edtsearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            search();
                            imm.hideSoftInputFromWindow(binding.tvHuy.getWindowToken(),
                                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
                            return true;
                    }
                }
                return true;
            }
        });
    }

    private void clickBtnHuy() {
        binding.tvHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.linesearch.setVisibility(View.GONE);
                binding.edtsearch.setText("");
                //Ẩn bàn phím ảo dt
                imm.hideSoftInputFromWindow(binding.tvHuy.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        });
    }
    private void save(ArrayList<ObjectACity>listSave) {
//        Đang bị lỗi lưu thừa vị trí hiện tại
//        if(GPS){
//            listSave.remove(listSave.size()-1);
//        }
        sqlHelper.deleteNoteAll();
        sqlHelper.insertProduct(listSave);
    }

    private void menuUpdate() {
        ArrayList<String> nameCity = new ArrayList<>();
        if (GPS) {
            //update bằng API theo tên thành phố
            for (int i = 1; i < objectACityfulls.size(); i++) {
                nameCity.add(objectACityfulls.get(i).getNameCity());
            }
        } else {
            for (int i = 0; i < objectACityfulls.size(); i++) {
                nameCity.add(objectACityfulls.get(i).getNameCity());
            }
        }
        objectACityfulls.clear();
        presenterMain.objectACityfull.clear();
        presenterMain.arrayID.clear();
        presenterMain.loadByLoacation(CorFDo);
        presenterMain.loadFull(nameCity);
    }

    private void search() {
        GPS = false;
        binding.linesearch.setVisibility(View.GONE);
        presenterMain.loadByCityName(binding.edtsearch.getText().toString(), CorFDo, false);
        binding.edtsearch.setText("");
    }

    public void setUnitlIsC(boolean isC) {
        int vitri = binding.viewpager.getCurrentItem();
        //Chuyển từ F sang C
        if (isC) {
            CorF = 0;
            CorFDo = 274.15;
        } else {
            //Chuyển từ C sang F
            CorF = 1;
            CorFDo = 0.0;
        }
        binding.viewpager.setCurrentItem(vitri);
    }
    private void changeUnitlIsC(boolean isC){
        int vitri = binding.viewpager.getCurrentItem();
        //Chuyển từ F sang C
        if (isC) {
            CorF = 0;
            CorFDo = 274.15;
            presenterMain.objectACityfull.clear();
            presenterMain.objectACityfull.addAll(objectACityfulls);
            presenterMain.setUnitl(CorF);
            setAdapterNew(false);
            save(objectACityfulls);
        } else {
            //Chuyển từ C sang F
            CorF = 1;
            CorFDo = 0.0;
            presenterMain.objectACityfull.clear();
            presenterMain.objectACityfull.addAll(objectACityfulls);
            presenterMain.setUnitl(CorF);
            setAdapterNew(false);
            save(objectACityfulls);
        }
        binding.viewpager.setCurrentItem(vitri);
    }
    private void clickImgBackListView() {
        binding.imgBacklistview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.relTong.setVisibility(View.VISIBLE);
                binding.linList.setVisibility(View.GONE);
            }
        });
    }

    private void itemClickListview() {
        binding.lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                binding.relTong.setVisibility(View.VISIBLE);
                binding.linList.setVisibility(View.GONE);
                binding.viewpager.setCurrentItem(position);
            }
        });
    }

    private void setAdapterNew(boolean newOrOld) {
        if (newOrOld) {
            myadapter = new AdapterSlide(MainActivity.this, objectACityfulls, GPS);
            binding.viewpager.setAdapter(myadapter);
        } else {
            myadapter.notifyDataSetChanged();
            binding.viewpager.setAdapter(myadapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == 115) {
                    int vitri = data.getIntExtra("vitri", -1);
                    if (vitri != -1) {
                        binding.viewpager.setCurrentItem(vitri);
                    } else {
                        objectACityfulls.clear();
                        presenterMain.objectACityfull.clear();
                        ArrayList<ObjectACity> objViewMap;
                        objViewMap = (ArrayList<ObjectACity>) data.getSerializableExtra("viewMapsend");
                        objectACityfulls.addAll(objViewMap);
                        presenterMain.objectACityfull.addAll(objViewMap);
                        setAdapterNew(false);
                        binding.viewpager.setCurrentItem(objectACityfulls.size() - 1);
                        save(objectACityfulls);
                    }
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ServiceRun() {
        //Khởi chạy severcie Ở đây là ExampleJobService
        ComponentName componentName = new ComponentName(this, JobServices.class);
        JobInfo info = new JobInfo.Builder(JobServices.JOB_ID, componentName)
                .setPeriodic(15 * 60 * 1000) //Thời gian chạy
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean isJobServiceOn() {
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        boolean hasBeenScheduled = false;
        if (scheduler.getPendingJob(JobServices.JOB_ID) != null) {
            hasBeenScheduled = true;
        }
        return hasBeenScheduled;
    }

    @Override
    public void loadfullCity(ArrayList<ObjectACity> objectACity) {
        objectACityfulls.clear();
        objectACityfulls.addAll(objectACity);
        setAdapterNew(true);
        //Cập nhật lúc
        binding.tvTimeLoad.setText(objectACityfulls.get(0).getTimeupdate());
        save(objectACity);
    }

    @Override
    public void loadACityForSearch(ObjectACity objectACity, int vitrit) {
        if (vitrit != -1) {
            //Nếu có trong danh sách, chuyển đến trang có tp đó
            setAdapterNew(false);
            binding.viewpager.setCurrentItem(vitrit);
        } else {
            //Nếu không có trong danh sách thì thêm vào và hiển thị đúng vị trí cần
            objectACityfulls.add(objectACity);
            setAdapterNew(false);
            binding.viewpager.setCurrentItem(objectACityfulls.size() - 1);
            save(objectACityfulls);
        }
    }

    @Override
    public void loadlocation(ObjectACity objectACity) {
        objectACityfulls.add(objectACity);
        setAdapterNew(true);
        save(objectACityfulls);
    }

    @Override
    public void onMessenger(String mes) {
        Toast.makeText(MainActivity.this, mes, Toast.LENGTH_SHORT).show();
    }
}
