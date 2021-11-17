package com.lymytz.android.component.printer.part;

import com.lymytz.android.component.printer.PrinterCommands;

public class LinePartPrinter extends PartPrinter {

    public LinePartPrinter(int lenght, String caratere, TextSize size, TextStyle style) {
        super(PartType.LINE, PartAlign.CENTER);
        this.data = new String(new char[lenght]).replace("\0", caratere) + "\n";
        this.size = PrinterCommands.TEXT_SIZE[size.ordinal()];
        this.style = PrinterCommands.TEXT_WEIGHT[style.ordinal()];
    }

    public LinePartPrinter(int lenght, String caratere, TextStyle style) {
        this(lenght, caratere, TextSize.TEXT_MEDIUM, style);
    }

    public LinePartPrinter(int lenght, String caratere) {
        this(lenght, caratere, TextSize.TEXT_MEDIUM, TextStyle.TEXT_NORMAL);
    }

    public LinePartPrinter(String caratere, TextStyle style) {
        this(WIDTH, caratere, TextSize.TEXT_MEDIUM, style);
    }

    public LinePartPrinter(String caratere) {
        this(WIDTH, caratere);
    }

    public LinePartPrinter() {
        this(" ");
    }
}
