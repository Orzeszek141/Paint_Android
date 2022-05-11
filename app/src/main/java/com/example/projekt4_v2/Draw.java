package com.example.projekt4_v2;

import android.graphics.Path;


public class Draw {
    public int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Path path;

    public Draw(int color, Path path) {
        this.color = color;
        this.path = path;
    }
}
