package com.lymytz.android.component.printer.part;

import com.lymytz.android.component.printer.PrinterCommands;

public class TextPartPrinter extends PartPrinter {

    public TextPartPrinter(PartAlign align, String text) {
        super(PartType.TEXT, align);
        this.data = text + "\n";
    }

    public TextPartPrinter(PartAlign align, String text, TextSize size, TextStyle style) {
        this(align, text);
        this.size = PrinterCommands.TEXT_SIZE[size.ordinal()];
        this.style = PrinterCommands.TEXT_WEIGHT[style.ordinal()];
    }

    public TextPartPrinter(PartAlign align, String text, TextSize size) {
        this(align, text, size, TextStyle.TEXT_NORMAL);
    }

    public TextPartPrinter(PartAlign align, String text, TextStyle style) {
        this(align, text, TextSize.TEXT_MEDIUM, style);
    }

    public TextPartPrinter(String textLeft, String textRigth) {
        this(PartAlign.LEFT, concat(textLeft, textRigth));
    }

    public static String concat(String textLeft, String textRigth) {
        String text = textLeft + textRigth;
        if (text.length() < WIDTH) {
            int n = WIDTH - text.length();
            text = textLeft + new String(new char[n]).replace("\0", " ") + textRigth;
        }
        return text;
    }

    public static String concat(String textLeft, String textCenter, String textRigth) {
        String text = textLeft + textCenter + textRigth;
        if (text.length() < WIDTH) {
            int n = WIDTH - text.length();
            text = textLeft + new String(new char[n / 2]).replace("\0", " ") + textCenter + new String(new char[n / 2]).replace("\0", " ") + textRigth;
        }
        return text;
    }
}
