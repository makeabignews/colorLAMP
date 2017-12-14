package com.gpiopi.www.colorlight;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int i=0;
    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    //System.out.println("handleMessage thread id " + Thread.currentThread().getId());
                    //System.out.println("msg.arg1:" + msg.arg1);
                    //System.out.println("msg.arg2:" + msg.arg2);
                    String inmsg=(String)msg.obj;


                    ((TextView)MainActivity.this.findViewById(R.id.button)).setText(inmsg);
                    break;
                case 7:
                    //System.out.println("handleMessage thread id " + Thread.currentThread().getId());
                    //System.out.println("msg.arg1:" + msg.arg1);
                    //System.out.println("msg.arg2:" + msg.arg2);
                    String color=(String)msg.obj;


                    //((TextView)MainActivity.this.findViewById(R.id.button)).setText(inmsg);
                    setTitle(color);
                    break;
                case 9:
                    //System.out.println("handleMessage thread id " + Thread.currentThread().getId());
                    //System.out.println("msg.arg1:" + msg.arg1);
                    //System.out.println("msg.arg2:" + msg.arg2);
                    String returnMSG=(String)msg.obj;
                    /*
                    if(returnCode.equals("已连接")){
                        setTitle("蓝牙彩灯（已连接）");
                    }
                    if(returnCode.equals("连接失败")){
                        setTitle("蓝牙彩灯（未连接）");
                    }*/
                    setTitle("蓝牙彩灯（"+returnMSG+"）");
                    //((TextView)MainActivity.this.findViewById(R.id.button)).setText(inmsg);

                    break;
            }
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        final Button btn = (Button) findViewById(R.id.button);

        final Ble ble=new Ble(this,uiHandler ,"00:15:83:00:92:C7","0000ffe0-0000-1000-8000-00805f9b34fb","0000ffe1-0000-1000-8000-00805f9b34fb","0000ffe1-0000-1000-8000-00805f9b34fb");
        ble.conn();

        /*
        ColorPickerView cpv=(ColorPickerView)this.findViewById(R.id.cpv);
        cpv.setOnColorChangedListenner(new ColorPickerView.OnColorChangedListener() {
            @Override
            public void onColorChanged(int r, int g, int b) {
                if(r==0 && g==0 && b==0){
                    return;
                }
                //Toast.makeText(MyViewActivity.this, "选取 RGB:"+r+","+g+","+b, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onMoveColor(int r, int g, int b) {
                if(r==0 && g==0 && b==0){
                    return;
                }
                //setTitle("RGB:"+r+","+g+","+b);
                ble.write(("gsj@"+r+"."+g+"."+b+"\n").getBytes());

            }
        });*/


        setTitle("智能蓝牙彩灯");



        //conn();



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i+=1;
                if(i==1){
                    ble.write("gsj@255.255.255\n".getBytes());
                    btn.setBackgroundColor(Color.rgb(255,255,255));
                }
                if(i==2){
                    ble.write("gsj@255.0.0\n".getBytes());
                    btn.setBackgroundColor(Color.rgb(255,0,0));
                }
                if(i==3){
                    ble.write("gsj@255.255.0\n".getBytes());
                    btn.setBackgroundColor(Color.rgb(255,255,0));
                }
                if(i==4){
                    ble.write("gsj@0.0.255\n".getBytes());
                    btn.setBackgroundColor(Color.rgb(0,0,255));
                }
                if(i==5){
                    ble.write("gsj@255.0.255\n".getBytes());
                    btn.setBackgroundColor(Color.rgb(255,0,255));
                }
                if(i==6){
                    ble.write("gsj@192.192.192\n".getBytes());
                    btn.setBackgroundColor(Color.rgb(192,192,192));
                }
                if(i==7){
                    ble.write("gsj@0.0.0\n".getBytes());
                    btn.setBackgroundColor(Color.rgb(0,0,0));
                    i=0;
                }



                /*
                new ColorPickerDialog(MainActivity.this, Color.BLACK,"选择颜色",new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void colorChanged(int color) {
                        int alpha = (color & 0xff000000) >>> 24;
                        int red   = (color & 0x00ff0000) >> 16;
                        int green = (color & 0x0000ff00) >> 8;
                        int blue  = (color & 0x000000ff);
                        String cmd="gsj@"+red+"."+green+"."+blue+"\n";
                        ble.write(cmd.getBytes());
                    }
                }).show();*/

            }
        });
    }
}
