package com.pzlvv.coco;

import java.util.ArrayList;
import java.util.List;

public class Params {

    public static double[] linspace(double min, double max, int points) {
        double[] d = new double[points];
        for (int i = 0; i < points; i++){
            d[i] = min + i * (max - min) / (points - 1);
        }
        return d;
    }

    public void setDetParams(){
        imgIds = new ArrayList<>();
        catIds = new ArrayList<>();
        iouThrs = Params.linspace(.5, 0.95, (int)((0.95 - .5) / .05) + 1);
        recThrs = Params.linspace(.0, 1.00, (int)((1.00 - .0) / .01) + 1);
        maxDets = new int[]{1, 10, 100};
        areaRng = new double[][] {
                {0*0, 1e5*1e5},
                {0*0, 32*32},
                {32*32, 96*96},
                {96*96, 1e5*1e5}
        };
        areaRngLbl = new String[]{"all", "small", "medium", "large"};
        useCats = true;
    }

    public Params(String iouType) throws Exception {
        if (iouType != "bbox") {
            throw new Exception("Only bbox has been implemented");
        }
        if (iouType == "segm" || iouType == "bbox") {
            setDetParams();
        }
        this.iouType = iouType;
    }
    double [][] areaRng;
    int[] maxDets;
    boolean useCats;
    String[] areaRngLbl;
    List<Integer> imgIds;
    List<Integer> catIds;
    double[] iouThrs;
    double[] recThrs;
    String iouType;

}


