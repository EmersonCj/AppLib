package lib.emerson.com.emersonapplib.domain;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import lib.emerson.com.emersonapplib.R;
import lib.emerson.com.emersonapplib.niffydialogeffects.Effectstype;
import lib.emerson.com.emersonapplib.niffydialogeffects.NiftyDialogBuilder;

/**
 * Created by Administrator on 2016/6/2.
 */
public class DialogActivity extends baseActivity {
    private Effectstype effect;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }

    //按键监听
    public void dialogShow(View v){
        final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(this);

        ////设置dialog动画效果
        switch (v.getId()){
            case R.id.fadein:effect=Effectstype.Fadein;break;
            case R.id.slideright:effect=Effectstype.Slideright;break;
            case R.id.slideleft:effect=Effectstype.Slideleft;break;
            case R.id.slidetop:effect=Effectstype.Slidetop;break;
            case R.id.slideBottom:effect=Effectstype.SlideBottom;break;
            case R.id.newspager:effect=Effectstype.Newspager;break;
            case R.id.fall:effect=Effectstype.Fall;break;
            case R.id.sidefall:effect=Effectstype.Sidefill;break;
            case R.id.fliph:effect=Effectstype.Fliph;break;
            case R.id.flipv:effect=Effectstype.Flipv;break;
            case R.id.rotatebottom:effect=Effectstype.RotateBottom;break;
            case R.id.rotateleft:effect=Effectstype.RotateLeft;break;
            case R.id.slit:effect=Effectstype.Slit;break;
            case R.id.shake:effect=Effectstype.Shake;break;
        }

        //设置dialog参数
        dialogBuilder
                .withTitle("Modal Dialog")                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage("This is a modal Dialog.")                     //.withMessage(null)  no Msg
                .withMessageColor("#FFFFFF")                                //def
                .withIcon(getResources().getDrawable(R.drawable.icon))
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(700)                                          //def
                .withEffect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("OK")                                      //def gone
                .withButton2Text("Cancel")                                  //def gone
                .setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "OK", Toast.LENGTH_SHORT).show();
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                        dialogBuilder.dismiss();
                    }
                })
                .show();

    }

}
