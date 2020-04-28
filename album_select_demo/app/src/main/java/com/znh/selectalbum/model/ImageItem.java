package com.znh.selectalbum.model;

import android.graphics.Bitmap;


import com.znh.selectalbum.utils.ImageHelper;

import java.io.IOException;
import java.io.Serializable;


public class ImageItem implements Serializable {
    public String imageId;
    public String thumbnailPath;
    public String imagePath;
    private Bitmap bitmap;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageItem imageItem = (ImageItem) o;

        return !(imageId != null ? !imageId.equals(imageItem.imageId) : imageItem.imageId != null);

    }

    @Override
    public int hashCode() {
        return imageId != null ? imageId.hashCode() : 0;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        if (bitmap == null) {
            try {
                bitmap = ImageHelper.revitionImageSize(imagePath);
                int degree= ImageHelper.readPictureDegree(imagePath);
                if(degree!=0){
                    bitmap=ImageHelper.rotaingImageView(degree,bitmap);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    @Override
    public String toString() {
        return "ImageItem{" +
                "imageId='" + imageId + '\'' +
                ", thumbnailPath='" + thumbnailPath + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
