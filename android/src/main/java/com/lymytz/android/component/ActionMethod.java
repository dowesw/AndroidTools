package com.lymytz.android.component;

import com.lymytz.android.enums.Action;

public class ActionMethod {
    public String METHODE = null;
    public Object CLASSE = null;
    public Class RETURN = void.class;
    public Class[] PARAMTYPES = null;
    public Object[] PARAMS = null;
    public Action ACTION = Action.OK;
    public String ACTIONNAME = null;

    public ActionMethod(String methode, Object classe, Action action, Class returns, Class[] paramTypes, Object[] params) {
        this.METHODE = methode;
        this.CLASSE = classe;
        this.ACTION = action;
        this.RETURN = returns;
        this.PARAMTYPES = paramTypes;
        this.PARAMS = params;
    }

    public ActionMethod(String methode, Object classe, Action action, Class[] paramTypes, Object[] params) {
        this(methode, classe, action, void.class, paramTypes, params);
    }

    public ActionMethod(String methode, Object classe, Action action, Class[] paramTypes, Object[] params, String action_name) {
        this(methode, classe, action, void.class, paramTypes, params);
        this.ACTIONNAME = action_name;
    }

    public ActionMethod(String methode, Object classe, Action action, Class returns) {
        this(methode, classe, action, returns, null, null);
    }

    public ActionMethod(String methode, Object classe, Action action) {
        this(methode, classe, action, void.class, null, null);
    }
}
