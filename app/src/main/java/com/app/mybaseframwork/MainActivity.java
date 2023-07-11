package com.app.mybaseframwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.mybaseframwork.base.MyBaseDataBindingActivity;
import com.app.mybaseframwork.base.base_model.CommonViewModel;
import com.app.mybaseframwork.databinding.ActivityMainBinding;
import com.app.mybaseframwork.test.CanScrollTopVpActivity;
import com.dy.fastframework.util.LogUtils;
import com.dy.fastframework.util.MD5Utils;
import com.dy.fastframework.util.NoDoubleClickListener;
import com.dy.fastframework.util.ScreenUtils;
import com.dy.fastframework.util.ToastUtil;
import com.dy.fastframework.view.PopWindowUtils;
import com.vise.utils.assist.RandomUtil;
import com.vise.utils.cipher.MD5;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import java.util.Arrays;


/**
 * ViewModel继承基类ViewModel,实现模型和Activity，fragment分离
 */
public class MainActivity extends MyBaseDataBindingActivity<CommonViewModel<ActivityMainBinding>, ActivityMainBinding> {

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initFirst() {
        setActivityTitle(getResString(R.string.app_name));
        binding.llTitleBar.ivTitleBack.setVisibility(View.GONE);
        setClick();
    }

    public void setClick(){
        binding.tvCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPromt();

            }
        });

        binding.tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.copy(binding.etPromt.getText().toString());
                showTs("复制成功");
            }
        });

        binding.tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ActivityHistory.class));
            }
        });
    }



    private void createPromt() {
        String prom="";
        String[] frontQualityList = getResources().getStringArray(R.array.quality_Z);
        String qualityStr = frontQualityList[getRandIndex(frontQualityList)];

        String[] jueSe = getResources().getStringArray(R.array.jue_se);
        String jueSeStr = jueSe[getRandIndex(jueSe)];

        String[] girls_face = getResources().getStringArray(R.array.girls_face);
        String girls_faceStr = girls_face[getRandIndex(girls_face)];

        String[] face_emotion = getResources().getStringArray(R.array.face_emotion);
        String face_emotionStr = face_emotion[getRandIndex(face_emotion)];

        String[] hair_type = getResources().getStringArray(R.array.hair_type);
        String hair_typeStr = hair_type[getRandIndex(hair_type)];

        String[] liu_hai = getResources().getStringArray(R.array.liu_hai);
        String liu_haiStr = liu_hai[getRandIndex(liu_hai)];

        String[] ma_wei = getResources().getStringArray(R.array.ma_wei);
        String ma_weiStr = ma_wei[getRandIndex(ma_wei)];

        String[] faxin = getResources().getStringArray(R.array.faxin);
        String faxinStr = faxin[getRandIndex(faxin)];

        String[] shi_pin_on_hair = getResources().getStringArray(R.array.shi_pin_on_hair);
        String shi_pin_on_hairStr = shi_pin_on_hair[getRandIndex(shi_pin_on_hair)];

        String[] shang_zhuang = getResources().getStringArray(R.array.shang_zhuang);
        String shang_zhuangStr = shang_zhuang[getRandIndex(shang_zhuang)];

        String[] lu_chu = getResources().getStringArray(R.array.lu_chu);
        String lu_chuStr = lu_chu[getRandIndex(lu_chu)];

        String[] xia_zhuang = getResources().getStringArray(R.array.xia_zhuang);
        String xia_zhuangStr = xia_zhuang[getRandIndex(xia_zhuang)];

        String[] foot_shoe = getResources().getStringArray(R.array.foot_shoe);
        String foot_shoeStr = foot_shoe[getRandIndex(foot_shoe)];

        String[] leg_socks = getResources().getStringArray(R.array.leg_socks);
        String leg_socksStr = leg_socks[getRandIndex(leg_socks)];

        String[] zhi_shi = getResources().getStringArray(R.array.zhi_shi);
        String zhi_shiStr = zhi_shi[getRandIndex(zhi_shi)];

        String[] look_type = getResources().getStringArray(R.array.look_type);
        String look_typeStr = look_type[getRandIndex(look_type)];

        String[] ji_jie = getResources().getStringArray(R.array.ji_jie);
        String ji_jieStr = ji_jie[getRandIndex(ji_jie)];

        String[] place_out_door = getResources().getStringArray(R.array.place_out_door);
        String place_out_doorStr = place_out_door[getRandIndex(place_out_door)];

        String[] place_in_door = getResources().getStringArray(R.array.place_in_door);
        String place_in_doorStr = place_in_door[getRandIndex(place_in_door)];

        String[] background_type_jie_ri = getResources().getStringArray(R.array.background_type_jie_ri);
        String background_type_jie_riStr = background_type_jie_ri[getRandIndex(background_type_jie_ri)];

        String[] light_type = getResources().getStringArray(R.array.light_type);
        String light_typeStr = light_type[getRandIndex(light_type)];

        String[] shot_angle = getResources().getStringArray(R.array.shot_angle);
        String shot_angleStr = shot_angle[getRandIndex(shot_angle)];

        String[] quality_F = getResources().getStringArray(R.array.quality_F);
        String quality_FStr = quality_F[getRandIndex(quality_F)];

        prom+=qualityStr;

        if(binding.swJueSe.isChecked()){
            prom+=jueSeStr+",";
        }

        prom+=girls_faceStr+","+face_emotionStr+",";

        if(!binding.swUpFull.isChecked()){
            //没有选择全身，随机拍摄角度
            prom+=shot_angleStr+",";
        }else{
            prom+="full body,";
        }

        if(binding.swHairColorRand.isChecked()){
            prom+=hair_typeStr+",";
        }

        boolean hasLiuHai=RandomUtil.getRandom(2)==1;
        if(hasLiuHai){
            prom+=liu_haiStr+",";
        }

        boolean mawei=RandomUtil.getRandom(2)==1;
        if(mawei){
            prom+=ma_weiStr+",";
        }

        boolean hasFaxin=RandomUtil.getRandom(2)==1;
        if(hasFaxin){
            prom+=faxinStr+",";
        }

        boolean hasShiPin=RandomUtil.getRandom(3)>1;
        if(hasShiPin){
            prom+=shi_pin_on_hairStr+",";
        }

        prom+=shang_zhuangStr+",";

        if(binding.swBody.isChecked()){
            prom+=lu_chuStr+",";
        }


        if(binding.swUpFull.isChecked()){
            prom+=xia_zhuangStr+",";
        }

        if(binding.swUpFull.isChecked()){
            //全身
            boolean isShowShoe=RandomUtil.getRandom(2)==1;
            if(isShowShoe) {
                prom += foot_shoeStr + ",";
            }
           prom+=leg_socksStr+",";
        }

        if(binding.actionRand.isChecked()){
            //姿势
            prom+=zhi_shiStr+",";
        }

        if(binding.swEyesLook.isChecked()){
            prom+=look_typeStr+",";
        }else{
            prom+="look at viewer,";
        }

        if(binding.swOutDoor.isChecked()){
            prom+=place_out_doorStr+",";
            if(binding.swLight.isChecked()){
                prom+=light_typeStr+",";
            }else {
                boolean isShowLight = RandomUtil.getRandom(2) == 1;
                if (isShowLight) {
                    prom += light_typeStr + ",";
                }
            }
        }else{
            boolean isShowInDoors=RandomUtil.getRandom(2)==1;
            if(isShowInDoors){
                prom+=place_in_doorStr+",";
            }else{
                prom+=place_out_doorStr+",";
                if(binding.swLight.isChecked()){
                    prom+=light_typeStr+",";
                }else {
                    boolean isShowLight = RandomUtil.getRandom(2) == 1;
                    if (isShowLight) {
                        prom += light_typeStr + ",";
                    }
                }
            }
        }

        boolean isShowBackgroundJieRi = RandomUtil.getRandom(3) == 1;
        if (isShowBackgroundJieRi) {
            prom += background_type_jie_riStr + ",";
        }

        if(prom.endsWith(",")){
            prom=prom.substring(0,prom.length()-1);
        }
        fanYi(prom);
        binding.etPromt.setText(prom);
    }


    @Override
    public boolean setIsExitActivity() {
        return true;
    }

    public void fanYi(String prom){
        long salt=System.currentTimeMillis();
//        http://api.fanyi.baidu.com/api/trans/vip/translate?q=apple&from=en&to=zh&appid=2015063000000001&salt=1435660288&sign=f89f9594663708c1605f3d736d01d2d4;
        String pingJie="20230501001662516"+prom+salt+"Nr1cTqMjiThMdpN_GgBq";
        LogUtils.v("请求原始："+pingJie);
        String sign = MD5Utils.get(pingJie);
        LogUtils.e("请求MD5："+sign);
        String requestUrl="api/trans/vip/translate?"+"q="+prom+"&from=en&to=zh&appid="+"20230501001662516&salt="+salt+"&sign="+sign;
        LogUtils.v("请求地址："+ViseHttp.CONFIG().getBaseUrl()+requestUrl);
        ViseHttp.GET(requestUrl)
                .request(new ACallback<ConvertResult>() {
                    @Override
                    public void onSuccess(ConvertResult data) {
                        LogUtils.v("请求翻译内容："+data);
                        if(data.getTrans_result()!=null&&data.getTrans_result().size()>0) {
                            binding.etFan.setText(data.getTrans_result().get(0).getDst());
                            ConvertResult historyData= (ConvertResult) getSpUtil().getObj("ConvertResult",ConvertResult.class);
                            if(historyData==null) {
                                getSpUtil().saveObject("ConvertResult", data);
                            }else{
                                historyData.getTrans_result().add(0,data.getTrans_result().get(0));
                                getSpUtil().saveObject("ConvertResult",historyData);
                            }
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
    }


    public int getRandIndex(String[] datas){
        int maxIndex = datas.length;
        return RandomUtil.getRandom(maxIndex);
    }

    @Override
    protected CommonViewModel<ActivityMainBinding> createViewModel() {
        return new CommonViewModel<ActivityMainBinding>(this,binding);
    }
}