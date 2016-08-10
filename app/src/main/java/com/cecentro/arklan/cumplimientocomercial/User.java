package com.cecentro.arklan.cumplimientocomercial;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by arnoldgustavocaballeromantilla on 2/06/16.
 */
public class User {
    String userId;
    String email;
    String rol;

    int pos,pre,ba,mv,tv,lb;

    public User(String userId, String email, String rol, int pos, int pre, int ba, int mv, int tv, int lb) {
        this.userId = userId;
        this.email = email;
        this.rol = rol;
        this.pos = pos;
        this.pre = pre;
        this.ba = ba;
        this.mv = mv;
        this.tv = tv;
        this.lb = lb;
    }

    public User(String userId, String email, String rol) {
        this.userId = userId;
        this.email = email;
        this.rol = rol;
    }

    public User(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}
