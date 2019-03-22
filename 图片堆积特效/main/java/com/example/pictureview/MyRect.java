package com.example.pictureview;

import android.graphics.Rect;

// 现对于一个点的一个矩形区域
public class MyRect {
    Rect onePiece;

    public MyRect(Rect onePiece) {
        this.onePiece = onePiece;
    }

    public Rect getOnePiece() {
        return onePiece;
    }

    public void setOnePiece(Rect onePiece) {
        this.onePiece = onePiece;
    }
}
