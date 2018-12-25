package com.pzlvv.coco;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class COCO {
    public COCO(String annotationFile) throws IOException {
        if (annotationFile != null) {
            System.out.println("loading annotations into memory...");
            dataset = new JSONObject(
                    new String(Files.readAllBytes(Paths.get(annotationFile))));
        }
        createIndex();
    }

    public void createIndex() {
        System.out.println("creating index...");

        if (dataset.has("annotations")) {
            System.out.println("creating annotations...");
            JSONArray annotations = dataset.getJSONArray("annotations");
            for (int i=0; i<annotations.length(); ++i) {
                JSONObject ann = annotations.getJSONObject(i);
                imgToAnns.get(ann.getInt("image_id")).add(ann);
                anns.put(ann.getInt("id"), ann);
            }
        }

        if (dataset.has("images")) {
            System.out.println("creating images...");
            JSONArray images = dataset.getJSONArray("images");
            for (int i=0; i<images.length(); ++i) {
                JSONObject img = images.getJSONObject(i);
                imgs.put(img.getInt("id"), img);
            }
        }

        if (dataset.has("categories")) {
            System.out.println("creating categories...");
            JSONArray categories = dataset.getJSONArray("categories");
            for (int i=0; i<categories.length(); ++i) {
                JSONObject cat = categories.getJSONObject(i);
                cats.put(cat.getInt("id"), cat);
            }
        }

        if (dataset.has("annotations") && dataset.has("categories")) {
            System.out.println("creating cat to img...");
            JSONArray annotations = dataset.getJSONArray("annotations");
            for (int i=0; i<annotations.length(); ++i) {
                JSONObject ann = annotations.getJSONObject(i);
                catToImgs.get(ann.getInt("category_id")).add(ann.getInt("image_id"));
            }
        }
        System.out.println("index created");
    }

    public List<Integer> getAnnIds(int[] imgIds, int[] catIds, double[] areaRng, boolean iscrowd) {
        /* Todo */
        return null;
    }

    public List<Integer> getImgIds(Integer[] imgIds, Integer[] catIds) {
        if (imgIds.length == 0 && catIds.length == 0) {
            return new ArrayList<>(imgs.keySet());
        }
        else {
            Set<Integer> ids = new HashSet<>(Arrays.asList(imgIds));
            for (int i=0; i<catIds.length; ++i) {
                Integer catId = catIds[i];
                if (i == 0 && ids.size() == 0) {
                    ids = new HashSet<>(catToImgs.get(catId));
                } else {
                    ids.addAll(catToImgs.get(catId));
                }
            }
            return new ArrayList<>(ids);
        }
    }

    public List<Integer> getCatIds(Integer[] catNms, Integer[] supNms, Integer[] catIds) {
        JSONArray cats;
        if (catNms.length == 0 && supNms.length == 0 && catIds.length == 0) {
           cats = dataset.getJSONArray("categories");
        } else {
           cats = dataset.getJSONArray("categories");
           JSONArray filterd = new JSONArray();
           for (int i=0; i<cats.length(); ++i) {
               JSONObject cat = cats.getJSONObject(i);
               if ((catNms.length == 0 || (Arrays.asList(catNms).contains(cat.getString("name")))) &&
                       (supNms.length == 0 || Arrays.asList(supNms).contains((cat.getString("supercategory")))) &&
                       (catIds.length == 0 || Arrays.asList(catIds).contains(cat.getInt("id")))
               ) {
                    filterd.put(cat);
               }
           }
           cats = filterd;
        }
        List<Integer> ids = new ArrayList<>();
        for (int i=0; i<cats.length(); ++i) {
            ids.add(cats.getJSONObject(i).getInt("id"));
        }
        return ids;
    }

    public List<JSONObject> loadAnns(List<Integer> ids) {
        List<JSONObject> items = new ArrayList<>();
        for (Integer id : ids) {
            items.add(anns.get(id));
        }
        return items;
    }


    private JSONObject dataset;
    DefaultDict<Integer, List<JSONObject>> imgToAnns = new DefaultDict<>(ArrayList.class);
    DefaultDict<Integer, List<Integer>> catToImgs = new DefaultDict<>(ArrayList.class);
    HashMap<Integer, JSONObject> anns = new HashMap<>();
    HashMap<Integer, JSONObject> imgs = new HashMap<>();
    HashMap<Integer, JSONObject> cats = new HashMap<>();
}
