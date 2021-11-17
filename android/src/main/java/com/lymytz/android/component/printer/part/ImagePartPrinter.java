package com.lymytz.android.component.printer.part;

import android.graphics.Bitmap;

public class ImagePartPrinter extends PartPrinter {

    public ImagePartPrinter(PartAlign align, Bitmap bitmap ) {
        super(PartType.IMAGE, align);
        this.data = bitmap;
    }

    public ImagePartPrinter(Bitmap bitmap) {
        this(PartAlign.CENTER, bitmap);
    }
}
