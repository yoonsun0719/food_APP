package com.example.food;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    EditText edtGender, edtBirthday;
    private static final int  WOMAN=0;
    private static final int MAN=1;

    private static final int PRO_ICON =100;
    CircleImageView imgIcon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        imgIcon=findViewById(R.id.profile_icon);

        getSupportActionBar().setTitle("Profile 프로필설정");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_person_24);

        edtGender=findViewById(R.id.profile_gender);
        edtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] genders={"여자", "남자"}; //여자 =0 ,남자 =1
                AlertDialog.Builder box=new AlertDialog.Builder(ProfileActivity.this);
                box.setTitle("성별선택");
                box.setItems(genders, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edtGender.setText(genders[which]);
                        //dialog.dismiss();
                    }
                });
                box.show();
            }
        });

        //생년월일 눌렀을대 달력뜨게
        edtBirthday=findViewById(R.id.profile_birthday);
        edtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //오늘날짜 가져오기
                GregorianCalendar cal=new GregorianCalendar();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH); //0월부터 가져오므로 +1 해주어야함
                final int day = cal.get(Calendar.DAY_OF_MONTH);

                //날짜 출력해주는 상자
                DatePickerDialog box=new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            //set눌렀을때 edtBirthday test에 넣기
                        String strBirthday=String.format("%04d-%02d-%02d", year,month+1,dayOfMonth);//변수로해줄때 무조건 %임
                        edtBirthday.setText(strBirthday);
                    }
                }, year,month,day);
            box.show();
            }
        });

        //이미지 가져오기
        ImageView imgChangeIcon=findViewById(R.id.profile_icon_change);
        imgChangeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,ProfileIconActivity.class);
                startActivityForResult(intent,PRO_ICON);
            }
        });
    }
    //이미지 변경하고 돌아와서
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != RESULT_OK) return;
        //리퀘스트코드가 프로 아이콘과 동일하면
        if(requestCode == PRO_ICON) { //확인버튼 눌렀을때
            //System.out.println("이곳은 프로파일엑티비=>온엑티비티리설트 완료");
            String strIcon=(String)data.getStringExtra("strIcon");
            //System.out.println("!!!!!!!!!!!!!!"+strIcon);
            imgIcon.setImageBitmap(BitmapFactory.decodeFile(strIcon));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //1.뒤로가기 버튼 눌렀을때
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                //3.프로필저장설정
            case R.id.submit :
                setResult(RESULT_OK);
                finish(); //나를 호출한 리설트 엑티비티로이동 (=>메인엑티비티)
        }
        return super.onOptionsItemSelected(item);
    }
    //2.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit,menu);
        return super.onCreateOptionsMenu(menu);
    }
}