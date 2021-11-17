package com.lymytz.android.tools;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.lymytz.android.R;
import com.lymytz.android.component.ActionMethod;
import com.lymytz.android.enums.Action;
import com.lymytz.android.enums.TypeAction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Messages {
    Context context;
    String title;
    String text;
    int ressource;
    int style;

    static final String FILE_SEPARATOR = File.separator;

    @SuppressWarnings("rawtypes")
    public static void Show(int ressource, int style, final Context context, String title, String text, TypeAction action, final ActionMethod[] methodes) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context, style);
            dialog.setTitle(title);
            dialog.setMessage(text);
            dialog.setCancelable(false);
            if (ressource > 0)
                dialog.setIcon(ressource);
            if (action == null || action.equals(TypeAction.OK)) {
                ActionMethod methode = Utils.returnAction(methodes, Action.OK);
                String action_name = context.getString(R.string.ok);
                if (methode != null ? Utils.asString(methode.ACTIONNAME) : false) {
                    action_name = methode.ACTIONNAME;
                }
                dialog.setPositiveButton(Utils.MenuIconWithText(context.getResources().getDrawable(R.drawable.ic_thumb_up_18dp), action_name),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (methode != null) {
                                    Utils.InvokeMethod(methode.METHODE, methode.CLASSE, methode.RETURN, methode.PARAMTYPES, methode.PARAMS);
                                }
                            }
                        });
            } else {
                if (action.equals(TypeAction.YES_NO) || action.equals(TypeAction.YES_NO_CANCEL) || action.equals(TypeAction.YES_NO_RETRY) || action.equals(TypeAction.YES_RETRY)) {
                    ActionMethod methode = Utils.returnAction(methodes, Action.YES);
                    String action_name = context.getString(R.string.oui);
                    if (methode != null ? Utils.asString(methode.ACTIONNAME) : false) {
                        action_name = methode.ACTIONNAME;
                    }
                    dialog.setPositiveButton(Utils.MenuIconWithText(context.getResources().getDrawable(R.drawable.ic_done_18dp), action_name),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (methode != null) {
                                        Utils.InvokeMethod(methode.METHODE, methode.CLASSE, methode.RETURN, methode.PARAMTYPES, methode.PARAMS);
                                    }
                                }
                            });
                }
                if (action.equals(TypeAction.YES_NO_RETRY) || action.equals(TypeAction.YES_RETRY)) {
                    ActionMethod methode = Utils.returnAction(methodes, Action.RETRY);
                    String action_name = context.getString(R.string.reessayer);
                    if (methode != null ? Utils.asString(methode.ACTIONNAME) : false) {
                        action_name = methode.ACTIONNAME;
                    }
                    dialog.setNeutralButton(Utils.MenuIconWithText(context.getResources().getDrawable(R.drawable.ic_replay_18dp), action_name),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (methode != null) {
                                        Utils.InvokeMethod(methode.METHODE, methode.CLASSE, methode.RETURN, methode.PARAMTYPES, methode.PARAMS);
                                    }
                                }
                            });
                }
                if (action.equals(TypeAction.YES_NO) || action.equals(TypeAction.YES_NO_CANCEL) || action.equals(TypeAction.YES_NO_RETRY)) {
                    ActionMethod methode = Utils.returnAction(methodes, Action.NO);
                    String action_name = context.getString(R.string.non);
                    if (methode != null ? Utils.asString(methode.ACTIONNAME) : false) {
                        action_name = methode.ACTIONNAME;
                    }
                    dialog.setNegativeButton(Utils.MenuIconWithText(context.getResources().getDrawable(R.drawable.ic_clear_18dp), action_name),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (methode != null) {
                                        Utils.InvokeMethod(methode.METHODE, methode.CLASSE, methode.RETURN, methode.PARAMTYPES, methode.PARAMS);
                                    }
                                }
                            });
                }
                if (action.equals(TypeAction.YES_NO_CANCEL)) {
                    ActionMethod methode = Utils.returnAction(methodes, Action.CANCEL);
                    String action_name = context.getString(R.string.annuler);
                    if (methode != null ? Utils.asString(methode.ACTIONNAME) : false) {
                        action_name = methode.ACTIONNAME;
                    }
                    dialog.setNeutralButton(Utils.MenuIconWithText(context.getResources().getDrawable(R.drawable.ic_undo_18dp), action_name),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (methode != null) {
                                        Utils.InvokeMethod(methode.METHODE, methode.CLASSE, methode.RETURN, methode.PARAMTYPES, methode.PARAMS);
                                    }
                                }
                            });
                }
            }
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {

                }
            });
            dialog.show();
        } catch (Exception ex) {
            Exception(context, ex);
        }
    }

    public static void Exception(Context context, Throwable ex) {
        try {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
            String file = Chemins.Logs(context) + FILE_SEPARATOR + "Log.txt";
            WriteTxt(file, ex.getMessage());
        } catch (Exception e) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @SuppressLint("NewApi")
    public static void WriteTxt(String fileDestination, String message) {
        try {
            if (!Utils.asString(fileDestination)) {
                return;
            }
            boolean append = true;
            File file = new File(fileDestination);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    return;
                }
                append = false;
            }
            try (FileWriter fw = new FileWriter(file, append)) {
                try (BufferedWriter out = new BufferedWriter(fw)) {
                    out.write(Utils.dateTimeStr(new Date()) + " : " + message);
                    out.newLine();
                }
            }
        } catch (IOException e) {
            Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, fileDestination, e);
        }
    }

    public Messages() {

    }

    public Messages(Context context, int style, int ressource, String title, String text) {
        this.context = context;
        this.title = title;
        this.text = text;
        this.ressource = ressource;
        this.style = style;
    }

    public Messages(Context context, int style, String title, String text) {
        this(context, style, -1, title, text);
    }

    public AlertDialog showDialog(TypeAction action, MessagesInterface listener) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context, style);
            dialog.setTitle(title);
            dialog.setMessage(text);
            dialog.setCancelable(true);
            if (ressource > 0)
                dialog.setIcon(ressource);
            if (action == null || action.equals(TypeAction.OK)) {
                String action_name = context.getString(R.string.ok);
                dialog.setPositiveButton(Utils.MenuIconWithText(context.getResources().getDrawable(R.drawable.ic_thumb_up_18dp), action_name),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (listener instanceof OKListiner) {
                                    ((OKListiner) listener).ok();
                                }
                            }
                        });
            } else {
                if (action.equals(TypeAction.YES_NO) || action.equals(TypeAction.YES_NO_CANCEL) || action.equals(TypeAction.YES_NO_RETRY) || action.equals(TypeAction.YES_RETRY)) {
                    String action_name = context.getString(R.string.oui);
                    dialog.setPositiveButton(Utils.MenuIconWithText(context.getResources().getDrawable(R.drawable.ic_done_18dp), action_name),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (listener instanceof YesNoListiner) {
                                        ((YesNoListiner) listener).yes();
                                    } else if (listener instanceof YesRetryListiner) {
                                        ((YesRetryListiner) listener).yes();
                                    } else if (listener instanceof YesNoCancelListiner) {
                                        ((YesNoCancelListiner) listener).yes();
                                    } else if (listener instanceof YesNoRetryListiner) {
                                        ((YesNoRetryListiner) listener).yes();
                                    }
                                }
                            });
                }
                if (action.equals(TypeAction.YES_NO_RETRY) || action.equals(TypeAction.YES_RETRY) || action.equals(TypeAction.NO_RETRY)) {
                    String action_name = context.getString(R.string.reessayer);
                    dialog.setNeutralButton(Utils.MenuIconWithText(context.getResources().getDrawable(R.drawable.ic_replay_18dp), action_name),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (listener instanceof YesRetryListiner) {
                                        ((YesRetryListiner) listener).retry();
                                    } else if (listener instanceof NoRetryListiner) {
                                        ((NoRetryListiner) listener).retry();
                                    } else if (listener instanceof YesNoRetryListiner) {
                                        ((YesNoRetryListiner) listener).retry();
                                    }
                                }
                            });
                }
                if (action.equals(TypeAction.YES_NO) || action.equals(TypeAction.YES_NO_CANCEL) || action.equals(TypeAction.NO_RETRY) || action.equals(TypeAction.YES_NO_RETRY)) {
                    String action_name = context.getString(R.string.non);
                    dialog.setNegativeButton(Utils.MenuIconWithText(context.getResources().getDrawable(R.drawable.ic_clear_18dp), action_name),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (listener instanceof YesNoListiner) {
                                        ((YesNoListiner) listener).no();
                                    } else if (listener instanceof NoRetryListiner) {
                                        ((NoRetryListiner) listener).no();
                                    } else if (listener instanceof YesNoCancelListiner) {
                                        ((YesNoCancelListiner) listener).no();
                                    } else if (listener instanceof YesNoRetryListiner) {
                                        ((YesNoRetryListiner) listener).no();
                                    }
                                }
                            });
                }
                if (action.equals(TypeAction.YES_NO_CANCEL)) {
                    String action_name = context.getString(R.string.annuler);
                    dialog.setNeutralButton(Utils.MenuIconWithText(context.getResources().getDrawable(R.drawable.ic_undo_18dp), action_name),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (listener instanceof YesNoCancelListiner) {
                                        ((YesNoCancelListiner) listener).cancel();
                                    }
                                }
                            });
                }
            }
            return dialog.show();
        } catch (Exception ex) {
            Exception(context, ex);
        }
        return null;
    }

    public interface OKListiner extends MessagesInterface {
        void ok();
    }

    public interface YesNoListiner extends MessagesInterface {
        void yes();

        void no();
    }

    public interface YesRetryListiner extends MessagesInterface {
        void yes();

        void retry();
    }

    public interface NoRetryListiner extends MessagesInterface {

        void no();

        void retry();
    }

    public interface YesNoCancelListiner extends MessagesInterface {
        void yes();

        void no();

        void cancel();
    }

    public interface YesNoRetryListiner extends MessagesInterface {
        void yes();

        void no();

        void retry();
    }

    public abstract interface MessagesInterface {

    }
}
