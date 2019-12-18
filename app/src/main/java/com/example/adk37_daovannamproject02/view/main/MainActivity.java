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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.adk37_daovannamproject02.R;
import com.example.adk37_daovannamproject02.adapter.AdapterListView;
import com.example.adk37_daovannamproject02.adapter.AdapterSlide;
import com.example.adk37_daovannamproject02.databinding.ActivityMainBinding;
import com.example.adk37_daovannamproject02.model.ObjectACity;
import com.example.adk37_daovannamproject02.uliti.Defile;
import com.example.adk37_daovannamproject02.uliti.JobServices;
import com.example.adk37_daovannamproject02.uliti.SharedPreferenceclass;
import com.example.adk37_daovannamproject02.view.search.ViewMap;

import java.util.ArrayList;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        presenterMain = new PresenterMain(this, MainActivity.this);
        presenterMain.progressBar = findViewById(R.id.progress);
        //Bắt sự kiện nhấn enter bàn phím ảo
        imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);

        if (objectACityfulls.size() == 0) {
            presenterMain.loadBYLoacation(CorFDo);
            presenterMain.saveNameCity.addAll(SharedPreferenceclass.readfile(MainActivity.this, Defile.SAVE, Defile.SAVEFILENAME));
            if (presenterMain.saveNameCity.size() == 0) {
                presenterMain.saveNameCity.add("London");
                presenterMain.saveNameCity.add("Sơn La");
            }
            presenterMain.loadfull(presenterMain.saveNameCity);
        }
        clickImgMenu();

        clickBtnHuy();

        pressEnter();

        clickImgSearch();

        clickImgBackListView();

        itemClickListview();
        if (!isJobServiceOn()){
            ServiceRun();
        }
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

    private void clickImgMenu() {
        binding.imgmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Tạo popupmenu
                Context wrapper = new ContextThemeWrapper(getBaseContext(), R.style.PopupMenu);
                final android.widget.PopupMenu popupMenu = new PopupMenu(wrapper, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                //Ẩn hiện item trong popupmenu
                //Độ C hay F
                if (CorF != 0) {
                    popupMenu.getMenu().findItem(R.id.menuDoC).setVisible(true);
                    popupMenu.getMenu().findItem(R.id.menuDoF).setVisible(false);
                } else {
                    popupMenu.getMenu().findItem(R.id.menuDoC).setVisible(false);
                    popupMenu.getMenu().findItem(R.id.menuDoF).setVisible(true);
                }
                //Lấy trang hiện tại
                pagenumber = binding.viewpager.getCurrentItem();
                //Thêm hay xóa
                int addordelete = 0;
                //Kiểm tra vị trí bằng id
                for (int i = 0; i < presenterMain.arrayID.size(); i++) {
                    if (objectACityfulls.get(pagenumber).getID() == presenterMain.arrayID.get(i)) {
                        addordelete = 1;
                        break;
                    }
                }
                if (addordelete == 0) {
                    //Hiện thêm
                    popupMenu.getMenu().findItem(R.id.menuAdd).setVisible(true);
                    popupMenu.getMenu().findItem(R.id.menuXoa).setVisible(false);
                } else if (addordelete != 0 && pagenumber == 0) {
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
                                changeunitl();
                                break;
                            case R.id.menuDoF:
                                changeunitl();
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
                                save();
                                break;
                            case R.id.menuXoa:
                                presenterMain.saveNameCity.remove(pagenumber - 1);
                                presenterMain.arrayID.remove(pagenumber - 1);
                                save();
                                int xoa = pagenumber;
                                objectACityfulls.remove(pagenumber);
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

    private void menuUpdate() {
        ArrayList<String> nameCity = new ArrayList<>();
        if (GPS) {
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
        presenterMain.loadBYLoacation(CorFDo);
        presenterMain.loadfull(nameCity);
    }

    private void search() {
        GPS = false;
        binding.linesearch.setVisibility(View.GONE);
        presenterMain.loadByCityName(binding.edtsearch.getText().toString(), CorFDo, false);
        binding.edtsearch.setText("");
    }

    public void changeunitl() {
        int vitri = binding.viewpager.getCurrentItem();
        if (CorF != 0) {
            CorF = 0;
            CorFDo = 274.15;
            presenterMain.changeunitl(CorF);
            setAdapterNew(false);
        } else {
            CorF = 1;
            CorFDo = 0.0;
            presenterMain.changeunitl(CorF);
            setAdapterNew(false);
        }
        binding.viewpager.setCurrentItem(vitri);
    }

    private void save() {
        SharedPreferenceclass.save(MainActivity.this, Defile.SAVE, presenterMain.saveNameCity, Defile.SAVEFILENAME);
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

    @Override
    public void loadfullCity(ArrayList<ObjectACity> objectACity) {
        objectACityfulls.clear();
        objectACityfulls.addAll(objectACity);
        setAdapterNew(true);
        //Cập nhật lúc
        binding.tvTimeLoad.setText(objectACityfulls.get(0).getTimeupdate());
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
        }
    }

    @Override
    public void loadlocation(ObjectACity objectACity) {
        objectACityfulls.add(objectACity);
        setAdapterNew(true);
    }

    @Override
    public void onMessenger(String mes) {
        Toast.makeText(MainActivity.this, mes, Toast.LENGTH_SHORT).show();
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
}
