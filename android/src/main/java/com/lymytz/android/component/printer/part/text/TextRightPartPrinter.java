package com.lymytz.android.component.printer.part.text;

import com.lymytz.android.component.printer.part.TextPartPrinter;

public class TextRightPartPrinter extends TextPartPrinter {

    public TextRightPartPrinter(String text) {
        super(PartAlign.RIGHT, concat("", text));
    }

    public TextRightPartPrinter(String text, TextSize size) {
        super(PartAlign.RIGHT, concat("", text), size);
    }

    public TextRightPartPrinter(String text, TextSize size, TextStyle style) {
        super(PartAlign.RIGHT, concat("", text), size, style);
    }
}
