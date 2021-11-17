package com.lymytz.android.component.printer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lymytz.android.R;
import com.lymytz.android.component.MyToast;
import com.lymytz.android.component.printer.part.PartPrinter;
import com.lymytz.android.thread.MySpinner;
import com.lymytz.android.tools.Utils;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Printer {
    protected Context context;
    protected FilePrinter content;
    protected BluetoothAdapter mBluetoothAdapter;
    protected UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    protected ProgressDialog mBluetoothConnectProgressDialog;
    protected BluetoothSocket mBluetoothSocket;
    protected BluetoothDevice mBluetoothDevice;

    PrinterListener mPrinterListener;
    static String mDeviceInfo;
    String mDeviceAddress;

    public void onPrinterListetenr(PrinterListener mPrinterListener) {
        this.mPrinterListener = mPrinterListener;
    }

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(getContext(), "DeviceConnected", Toast.LENGTH_SHORT).show();
        }
    };

    public Context getContext() {
        return context;
    }

    public Printer(Context context, View view, FilePrinter content) {
        this(context, content);
        Dialog dialog = new Dialog(getContext(), view);
        dialog.show();
    }

    public Printer(Context context, FilePrinter content) {
        this.context = context;
        this.content = content;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void print(String mDeviceAddress) {
        if (!Utils.asString(mDeviceAddress)) {
            Toast.makeText(context, "Veuillez choisir un périphérique", Toast.LENGTH_SHORT).show();
            return;
        }
        mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
        mBluetoothConnectProgressDialog = ProgressDialog.show(getContext(), "Connecting...", mBluetoothDevice.getName() + " : "
                + mBluetoothDevice.getAddress(), true, true);
        new Thread(() -> {
            try {
                mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID);
                mBluetoothAdapter.cancelDiscovery();
                mBluetoothSocket.connect();
                mHandler.sendEmptyMessage(0);

                OutputStream outputStream = mBluetoothSocket.getOutputStream();
                try {
                    if (content != null) {
                        if (content.getType().equals(FilePrinter.ContentType.FILE) ? content.getFile() != null : false) {

                        } else if (content.getType().equals(FilePrinter.ContentType.PART) ? content.getParts() != null ? !content.getParts().isEmpty() : false : false) {
                            byte[] printformat = new byte[]{0x1B, 0x21, 0x03};
                            outputStream.write(printformat);
                            for (PartPrinter part : content.getParts()) {
                                if (part.getType().equals(PartPrinter.PartType.BAR)) {

                                } else if (part.getType().equals(PartPrinter.PartType.QR)) {

                                } else if (part.getType().equals(PartPrinter.PartType.TEXT)) {
                                    Write.Text(outputStream, (String) part.getData(), part.getAlign(), part.getSize(), part.getStyle());
                                } else if (part.getType().equals(PartPrinter.PartType.IMAGE)) {
                                    Write.Image(outputStream, (Bitmap) part.getData(), part.getAlign());
                                } else if (part.getType().equals(PartPrinter.PartType.LINE)) {
                                    Write.Text(outputStream, (String) part.getData(), part.getAlign(), part.getSize(), part.getStyle());
                                } else if (part.getType().equals(PartPrinter.PartType.SPACE)) {
                                    Write.Space(outputStream);
                                }
                                Thread.sleep(com.lymytz.android.component.printer.PrinterCommands.TIME_BETWEEN_TWO_PRINT * 2);
                            }
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeSocket(mBluetoothSocket);
            }
        }).start();
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dismiss() {
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Bitmap pdfToBitmap(File pdfFile) {
        try {
            PDDocument pd = PDDocument.load(pdfFile);
            PDFRenderer pr = new PDFRenderer(pd);
            Bitmap bitmap = pr.renderImageWithDPI(0, 300);
            String destination = pdfFile.getAbsolutePath().replace(pdfFile.getName(), "");
            Utils.saveBitmap(destination, bitmap);
            return bitmap;
        } catch (Exception ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public interface PrinterListener {
        void Send();
    }

    private static class Write {

        public static void Image(OutputStream outputStream, Bitmap image, byte[] align) {
            try {
                if (image != null) {
                    byte[] command = Utils.decodeBitmap(image);
                    if (command == null) {
                        return;
                    }
                    outputStream.write(align);
                    outputStream.write(command);
                    outputStream.write(com.lymytz.android.component.printer.PrinterCommands.FEED_LINE);
                } else {
                    Log.e("Print Photo error", "the file isn't exists");
                }
            } catch (Exception ex) {
                Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
                Log.e("PrintTools", "the file isn't exists");
            }
        }

        //print new line
        public static void Space(OutputStream outputStream) {
            try {
                outputStream.write(PrinterCommands.FEED_LINE);
            } catch (IOException ex) {
                Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //print text
        public static void Text(OutputStream outputStream, String text, byte[] textAlign, byte[] textSize, byte[] textBold) {
            Text(outputStream, text, textAlign, textSize, textBold, 0);
        }

        //print text
        public static void Text(OutputStream outputStream, String text, byte[] align, byte[] size, byte[] style, int length) {
            try {
                byte[] textBytes = text.getBytes("ISO-8859-1");
                if (length == 0) {
                    length = textBytes.length;
                }
                outputStream.write(com.lymytz.android.component.printer.PrinterCommands.WESTERN_EUROPE_ENCODING);
                outputStream.write(com.lymytz.android.component.printer.PrinterCommands.TEXT_ALIGN_LEFT);
                outputStream.write(com.lymytz.android.component.printer.PrinterCommands.TEXT_SIZE_NORMAL);
                outputStream.write(com.lymytz.android.component.printer.PrinterCommands.TEXT_WEIGHT_NORMAL);
                outputStream.write(com.lymytz.android.component.printer.PrinterCommands.TEXT_UNDERLINE_OFF);

                if (align != null) {
                    outputStream.write(align);
                }
                if (style != null) {
                    outputStream.write(style);
                }
                if (size != null) {
                    outputStream.write(size);
                }
                outputStream.write(textBytes, 0, length);
            } catch (IOException ex) {
                Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class Dialog extends AlertDialog.Builder {

        View root;
        AlertDialog dialog;
        LinearLayout blog;

        public Dialog(Context context, View view) {
            super(context, AlertDialog.THEME_HOLO_LIGHT);
            LayoutInflater factory = LayoutInflater.from(getContext());
            root = factory.inflate(R.layout.component_dialog_dynamique, null);
            blog = root.findViewById(R.id.blog);
            TextView textView = new TextView(getContext());
            textView.setText("Selectionner un périphérique");
            textView.setPadding(10, 10, 10, 10);
            Spinner spinner = new Spinner(getContext());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View mView, int position, long id) {
                    try {
                        String value = ((TextView) mView).getText().toString();
                        if (Utils.asString(value) ? !value.equals("----") : false) {
                            mDeviceInfo = value;
                            mDeviceAddress = mDeviceInfo.substring(mDeviceInfo.length() - 17);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    mDeviceAddress = "";
                }
            });
            new Thread(() -> {
                if (mBluetoothAdapter == null) {
                    MyToast.makeText(context, "Action impossible!!!", MyToast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        MyToast.makeText(context, "Veuillez activer votre bluetooth", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.add("----");
                        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter.getBondedDevices();
                        if (mPairedDevices.size() > 0) {
                            for (BluetoothDevice mDevice : mPairedDevices) {
                                adapter.add(mDevice.getName() + "\n" + mDevice.getAddress());
                            }
                        }
                        if (Utils.asString(mDeviceInfo)) {
                            int index = adapter.getPosition(mDeviceInfo);
                            if (index > -1) {
                                new MySpinner(spinner, getContext()).setSelection(index);
                            }
                        }
                    }
                }
            }).start();
            blog.addView(textView);
            blog.addView(spinner);
            blog.addView(view);
        }

        @Override
        public AlertDialog show() {
            try {
                // TODO Auto-generated method stub
                setView(root);
                setNegativeButton("Envoyer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPrinterListener.Send();
                    }
                });
                setPositiveButton("Imprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        printIt(mDeviceAddress);
                        print(mDeviceAddress);
                    }
                });
                if (dialog != null ? !dialog.isShowing() : true) {
                    dialog = super.show();
                }
                return dialog;
            } catch (Exception ex) {
                Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
}
