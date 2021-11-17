package com.lymytz.android.component.printer.part;

public class BarPartPrinter extends PartPrinter {
    public BarPartPrinter() {
        super(PartType.BAR, PartAlign.CENTER);
    }

    public BarPartPrinter(String text) {
        this();
        this.data = text;
    }
}
