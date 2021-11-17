package com.lymytz.android.component.printer.part;

public class QRPartPrinter extends PartPrinter {
    public QRPartPrinter() {
        super(PartType.QR, PartAlign.CENTER);
    }

    public QRPartPrinter(String text) {
        this();
        this.data = text;
    }
}
