package com.example.adk37_daovannamproject02.uliti;

import com.example.adk37_daovannamproject02.R;

public class Defile_method {
    public static double Defile_Method_Max(Double[] tempmax) {
        Double max = tempmax[0];
        for (int i = 0; i < tempmax.length; i++) {
            if (tempmax[i] > max) {
                max = tempmax[i];
            }
        }
        return max;
    }

    public static double Defile_Method_Min(Double[] tempmin) {
        Double min = tempmin[0];
        for (int i = 0; i < tempmin.length; i++) {
            if (tempmin[i] < min) {
                min = tempmin[i];
            }
        }
        return min;
    }
}
