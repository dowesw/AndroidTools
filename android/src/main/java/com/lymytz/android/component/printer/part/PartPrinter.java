package com.lymytz.android.component.printer.part;

import com.lymytz.android.component.printer.PrinterCommands;

public abstract class PartPrinter {
    public static final int WIDTH = 32;
    Object data = null;
    PartType type = PartType.LINE;

    byte[] size = null;
    byte[] style = null;
    byte[] align = null;

    public Object getData() {
        return data;
    }

    public PartType getType() {
        return type;
    }

    public byte[] getSize() {
        return size;
    }

    public byte[] getStyle() {
        return style;
    }

    public byte[] getAlign() {
        return align;
    }

    public PartPrinter(PartType type, PartAlign align) {
        this.type = type;
        this.size = PrinterCommands.TEXT_SIZE_MEDIUM;
        this.style = PrinterCommands.TEXT_WEIGHT_NORMAL;
        this.align = PrinterCommands.TEXT_ALIGN[align.ordinal()];
    }

    public enum PartType {
        TEXT, IMAGE, LINE, SPACE, BAR, QR
    }

    public enum PartAlign {
        LEFT, CENTER, RIGHT
    }

    public enum TextStyle {
        TEXT_NORMAL, TEXT_BOLD
    }

    public enum TextSize {
        TEXT_NORMAL, TEXT_MEDIUM, TEXT_LARGE, TEXT_DOUBLE_HEIGHT, TEXT_SIZE_DOUBLE_WIDTH
    }
}
