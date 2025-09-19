package com.MajOfMyth.Nadir.color;

import dev.shadowsoffire.placebo.color.GradientColor;
import java.awt.Color;

public class NColors {

    public static int[] CreateGradient(int Color1, int Color2, int Steps) {

        Color RGBColor1 = new Color(Color1);
        Color RGBColor2 = new Color(Color2);

        float blending, inverseBlending;

        int[] Gradient = new int[Steps];


        for(int i = 0; i < Steps; i++) {

            blending = (float) i / (Steps-1);
            inverseBlending = 1 - blending;

            int Red   = (int) (RGBColor1.getRed()   * inverseBlending + RGBColor2.getRed()   * blending);
            int Green = (int) (RGBColor1.getGreen() * inverseBlending + RGBColor2.getGreen() * blending);
            int Blue  = (int) (RGBColor1.getBlue()  * inverseBlending + RGBColor2.getBlue()  * blending);

            int Blended = (Red << 16) | (Green << 8) | Blue;
            Gradient[i] = Blended;

        }

        return Gradient;
    }

    public static int[] PingPong(int[] Base) {

        int[] Final = new int[(Base.length*2)];

        System.arraycopy(Base, 0, Final, 0, Base.length);

        for(int i = 0; i < Base.length; i++) {
            System.arraycopy(Base, Base.length-i-1, Final, Base.length+i, 1);
        }

        return Final;
    }

    public static final int[] STELLAR_GRADIENT = CreateGradient(0x00FFFF, 0x0000FF, 100);
    public static final int[] DIVINE_GRADIENT = CreateGradient(0xFFED5C, 0xFFFBED, 100);
    public static final int[] ESOTERIC_GRADIENT = CreateGradient(0x00AA47, 0x005DAA, 100);

    public static final GradientColor STELLAR = new GradientColor(PingPong(STELLAR_GRADIENT), "stellar");
    public static final GradientColor DIVINE = new GradientColor(PingPong(DIVINE_GRADIENT), "divine");
    public static final GradientColor ESOTERIC = new GradientColor(PingPong(ESOTERIC_GRADIENT), "esoteric");

}
