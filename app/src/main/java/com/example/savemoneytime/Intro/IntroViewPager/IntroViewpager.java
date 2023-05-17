package com.example.savemoneytime.Intro.IntroViewPager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.savemoneytime.MainApplication.MainActivity;
import com.example.savemoneytime.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class IntroViewpager extends AppCompatActivity {
    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndication;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_viewpager);

        layoutOnboardingIndication = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction= findViewById(R.id.buttonOnboardingAction);

        setupOnbooardingItems();
        ViewPager2 onboardingViewpager = findViewById(R.id.onboardingViewpager);
        onboardingViewpager.setAdapter(onboardingAdapter);
        setupOnboardingIndicator();
        setCurrentOnboardingIndicators(0);

        onboardingViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicators(position);
            }
        });

        //xử lý sự kiện nút, chuyển sangIntro khác hoặc là chuyển vào phần chính
        buttonOnboardingAction.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(onboardingViewpager.getCurrentItem()+1 < onboardingAdapter.getItemCount()){
                    onboardingViewpager.setCurrentItem(onboardingViewpager.getCurrentItem()+1);
                }else{
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });

    }
    //hàm nãy để add dữ liệu vào list được thêm thông qua model của OnboardingItem sau đó Gọi Adapter của Onboardo
    // để thêm list này vào tạo thành viewpager
    private void setupOnbooardingItems(){
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemManagerWork = new OnboardingItem();
        itemManagerWork.setTitle("Quản Lý Lịch Trình Làm Việc !");
        itemManagerWork.setDescripts("Làm Việc Có Quy Tắc");
        itemManagerWork.setImage(R.drawable.task);

        OnboardingItem moneyManager = new OnboardingItem();
        moneyManager.setTitle("Quản lý chi tiêu!");
        moneyManager.setDescripts("Giúp bạn quản lý tốt chi tiêu của mình !");
        moneyManager.setImage(R.drawable.money);


        OnboardingItem itemManagerTime = new OnboardingItem();
        itemManagerTime.setTitle("Thời Gian Là Vàng Là Bạc !");
        itemManagerTime.setDescripts("Hãy quản lý tốt nó để có một cuộc sống thật chất lượng !");
        itemManagerTime.setImage(R.drawable.timetable);



        onboardingItems.add(itemManagerWork);
        onboardingItems.add(moneyManager);
        onboardingItems.add(itemManagerTime);
        onboardingAdapter = new OnboardingAdapter(onboardingItems);

    }
    //hàm thêm các dấu chấm khi chuyển slide
    private void setupOnboardingIndicator(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParams.setMargins(8,0,8,0);
        for(int i=0; i<indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.img_onboarding_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            //add các biểu tượng đã thiết lập trong drawable vào linearlayout đã tạo trong activity_intro_view
            layoutOnboardingIndication.addView(indicators[i]);
        }
    }
    //hàm này để thiết lập chyển trạng thái khi chuyển slide của dấu chấm nhỏ bên góc bên trái
    private void setCurrentOnboardingIndicators(int index){
        int childCount = layoutOnboardingIndication.getChildCount();
        for(int i = 0; i < childCount; i++){
            ImageView imageView= (ImageView) layoutOnboardingIndication.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                getApplicationContext(),
                                R.drawable.imag_onboarding_indicator_acticve
                ));
            }else{
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                getApplicationContext(),
                                R.drawable.img_onboarding_inactive
                        ));
            }
            if(index == onboardingAdapter.getItemCount()-1){
                buttonOnboardingAction.setText("Bắt Đầu");
            }else{
                buttonOnboardingAction.setText("Tiếp Tục");
            }
        }
    }
}