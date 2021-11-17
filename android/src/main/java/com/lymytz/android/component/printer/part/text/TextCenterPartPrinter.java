package com.lymytz.android.component.printer.part.text;

import com.lymytz.android.component.printer.part.TextPartPrinter;

public class TextCenterPartPrinter extends TextPartPrinter {

    public TextCenterPartPrinter(String text) {
        super(PartAlign.CENTER, concat("", text, ""));
    }

    public TextCenterPartPrinter(String text, TextSize size) {
        super(PartAlign.CENTER, concat("", text, ""), size);
    }

    public TextCenterPartPrinter(String text, TextStyle style) {
        super(PartAlign.CENTER, concat("", text, ""), style);
    }

    public TextCenterPartPrinter(String text, TextSize size, TextStyle style) {
        super(PartAlign.CENTER, concat("", text, ""), size, style);
    }
}
