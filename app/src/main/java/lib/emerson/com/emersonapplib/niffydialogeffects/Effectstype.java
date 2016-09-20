package lib.emerson.com.emersonapplib.niffydialogeffects;


import lib.emerson.com.emersonapplib.niffydialogeffects.BaseEffects;
import lib.emerson.com.emersonapplib.niffydialogeffects.FadeIn;
import lib.emerson.com.emersonapplib.niffydialogeffects.Fall;
import lib.emerson.com.emersonapplib.niffydialogeffects.FlipH;
import lib.emerson.com.emersonapplib.niffydialogeffects.FlipV;
import lib.emerson.com.emersonapplib.niffydialogeffects.NewsPaper;
import lib.emerson.com.emersonapplib.niffydialogeffects.RotateBottom;
import lib.emerson.com.emersonapplib.niffydialogeffects.RotateLeft;
import lib.emerson.com.emersonapplib.niffydialogeffects.Shake;
import lib.emerson.com.emersonapplib.niffydialogeffects.SideFall;
import lib.emerson.com.emersonapplib.niffydialogeffects.SlideBottom;
import lib.emerson.com.emersonapplib.niffydialogeffects.SlideLeft;
import lib.emerson.com.emersonapplib.niffydialogeffects.SlideRight;
import lib.emerson.com.emersonapplib.niffydialogeffects.SlideTop;
import lib.emerson.com.emersonapplib.niffydialogeffects.Slit;

/**
 * Created by lee on 2014/7/30.
 */
public enum  Effectstype {

    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    SlideBottom(SlideBottom.class),
    Slideright(SlideRight.class),
    Fall(Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    RotateBottom(RotateBottom.class),
    RotateLeft(RotateLeft.class),
    Slit(Slit.class),
    Shake(Shake.class),
    Sidefill(SideFall.class);
    private Class effectsClazz;

    private Effectstype(Class mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        try {
            return (BaseEffects) effectsClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
