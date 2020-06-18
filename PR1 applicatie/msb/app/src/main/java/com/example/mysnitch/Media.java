package com.example.mysnitch;

public class Media {
    private byte[] imageByteArray;

    public Media(byte[] array){
        this.setImageByteArray(array);
    }

    public byte[] getImageByteArray() {
        return imageByteArray;
    }

    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }
}
