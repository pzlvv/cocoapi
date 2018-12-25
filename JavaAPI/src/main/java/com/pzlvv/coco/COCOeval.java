package com.pzlvv.coco;

import java.util.*;

public class COCOeval {
    public COCOeval(COCO cocoGt, COCO cocoDt, String iouType) throws Exception {
        this.cocoGt = cocoGt;
        this.cocoDt = cocoDt;
        params = new Params(iouType);

        if (cocoGt != null) {
            params.imgIds = cocoGt.getImgIds(new Integer[]{}, new Integer[]{});
            params.catIds = cocoGt.getCatIds(new Integer[]{}, new Integer[]{}, new Integer[]{});
            Collections.sort(params.imgIds);
            Collections.sort(params.catIds);
        }
    }

    protected void prepare() {
    }

    public void evaluate() {
        System.out.println("Running per image evaluation...");
        Params p = params;
        System.out.println("Evaluate annotation type *"+p.iouType+"*");
        p.imgIds = new ArrayList<>(new HashSet<Integer>(params.imgIds));
        Collections.sort(p.imgIds);
        if (p.useCats) {
            p.catIds = new ArrayList<>(new HashSet<Integer>(params.catIds));
            Collections.sort(p.catIds);
        }
        Arrays.sort(p.maxDets);
    }

    private COCO cocoGt;
    private COCO cocoDt;
    private Params params;
}
