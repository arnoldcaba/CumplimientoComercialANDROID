package com.cecentro.arklan.cumplimientocomercial;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Global extends  FragmentActivity {

    Button btn_pospago,btn_prepago,btn_ba,btn_mv,btn_tv,btn_lb,btn_misMetas,btn_napster;
    LinearLayout linear_pospago, linear_prepago,linear_ba,linear_tv,linear_lb,linear_mv,linear_napster;
    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
    TextView txt_metapospago, txt_ejecutadopospago, txt_parrafo,txt_faltapospago,txt_gappospago,
            txt_metaprepago,txt_ejecutadoprepago,txt_faltaprepago,txt_gapprepago,txt_ususe,txt_close,
            txt_metaba,txt_ejecutadoba,txt_faltaba,txt_gapba,
            txt_metamv,txt_ejecutadomv,txt_faltamv,txt_gapmv,
            txt_metatv,txt_ejecutadotv,txt_faltatv,txt_gaptv,
            txt_metalb,txt_ejecutadolb,txt_faltalb,txt_gaplb,
            txt_metaNapster,txt_ejecutadoNapster,txt_faltaNapster,txt_gapNapster,last_update;
    int habiles,transcurridos;
    
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    public String userId,rol;

    

    DatabaseReference posRef=mRootRef.child("pospago");
    DatabaseReference preRef=mRootRef.child("prepago");
    DatabaseReference baRef=mRootRef.child("ba");
    DatabaseReference mvRef=mRootRef.child("mv");
    DatabaseReference tvRef=mRootRef.child("tv");
    DatabaseReference lbRef=mRootRef.child("lb");
    DatabaseReference napRef=mRootRef.child("napster");
    DatabaseReference habilesRef=mRootRef.child("habiles");
    DatabaseReference transcurridosRef=mRootRef.child("transcurridos");
    DatabaseReference lastUpdateRef=mRootRef.child("lastUpdate");

    boolean negativo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);

        
        btn_pospago=(Button)findViewById(R.id.btn_pospago);
        btn_prepago=(Button)findViewById(R.id.btn_prepago);
        btn_ba=(Button)findViewById(R.id.btn_ba);
        btn_tv=(Button)findViewById(R.id.btn_tv);
        btn_mv=(Button)findViewById(R.id.btn_mv);
        btn_lb=(Button)findViewById(R.id.btn_lb);
        btn_misMetas=(Button)findViewById(R.id.btn_misMetas);
        btn_napster=(Button)findViewById(R.id.btn_napster);
        linear_pospago=(LinearLayout)findViewById(R.id.linear_pospago);
        linear_prepago=(LinearLayout)findViewById(R.id.linear_prepago);
        linear_ba=(LinearLayout)findViewById(R.id.linear_ba);
        linear_mv=(LinearLayout)findViewById(R.id.linear_mv);
        linear_tv=(LinearLayout)findViewById(R.id.linear_tv);
        linear_lb=(LinearLayout)findViewById(R.id.linear_lb);
        linear_napster=(LinearLayout)findViewById(R.id.linear_napster);
        txt_metapospago=(TextView)findViewById(R.id.txt_metapospago);
        txt_parrafo=(TextView)findViewById(R.id.txt_parrafo);
        txt_faltapospago=(TextView)findViewById(R.id.txt_faltapospago);
        txt_gappospago=(TextView)findViewById(R.id.txt_gappospago);
        txt_metaprepago=(TextView)findViewById(R.id.txt_metaprepago);
        txt_faltaprepago=(TextView)findViewById(R.id.txt_faltaprepago);
        txt_gapprepago=(TextView)findViewById(R.id.txt_gapprepago);
        txt_metaba=(TextView)findViewById(R.id.txt_metaba);
        txt_faltaba=(TextView)findViewById(R.id.txt_faltaba);
        txt_gapba=(TextView)findViewById(R.id.txt_gapba);
        txt_metamv=(TextView)findViewById(R.id.txt_metamv);
        txt_faltamv=(TextView)findViewById(R.id.txt_faltava);
        txt_gapmv=(TextView)findViewById(R.id.txt_gapva);
        txt_metatv=(TextView)findViewById(R.id.txt_metatv);
        txt_faltatv=(TextView)findViewById(R.id.txt_faltatv);
        txt_gaptv=(TextView)findViewById(R.id.txt_gaptv);
        txt_metalb=(TextView)findViewById(R.id.txt_metalb);
        txt_faltalb=(TextView)findViewById(R.id.txt_faltalb);
        txt_gaplb=(TextView)findViewById(R.id.txt_gaplb);
        txt_metaNapster=(TextView)findViewById(R.id.txt_metaNapster);
        txt_ejecutadoNapster=(TextView)findViewById(R.id.txt_ejecutadoNapster);
        txt_faltaNapster=(TextView)findViewById(R.id.txt_faltaNapster);
        txt_gapNapster=(TextView)findViewById(R.id.txt_gapNapster);
        last_update=(TextView)findViewById(R.id.last_update);
        txt_ususe=(TextView)findViewById(R.id.txt_ususe);
        txt_close=(TextView)findViewById(R.id.txt_close);
        txt_ejecutadopospago=(TextView)findViewById(R.id.txt_ejecutadopospago);
        txt_ejecutadoprepago=(TextView)findViewById(R.id.txt_ejecutadoprepago);
        txt_ejecutadoba=(TextView)findViewById(R.id.txt_ejecutadoba);
        txt_ejecutadomv=(TextView)findViewById(R.id.txt_ejecutadomv);
        txt_ejecutadotv=(TextView)findViewById(R.id.txt_ejecutadotv);
        txt_ejecutadolb=(TextView)findViewById(R.id.txt_ejecutadolb);
        txt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });

        negativo=false;

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            public String TAG;

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    txt_ususe.setText("Bienvenido: "+user.getEmail());
                    userId=user.getUid();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Intent intent=new Intent(Global.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                // ...
            }
        };

        lastUpdateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data=dataSnapshot.getValue(String.class);
                last_update.setText("Actualizado el "+data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        habilesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String data=dataSnapshot.getValue(String.class);


                transcurridosRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String data2=dataSnapshot.getValue(String.class);
                        txt_parrafo.setText(Html.fromHtml("Asì vamos en el cumplimiento comercial a: "+"<b>"+data2+"</b>"+" dias Transcurridos de: "+"<b>"+data+"</b>"+" dias habiles del mes"));
                        habiles= Integer.valueOf(data);
                        transcurridos= Integer.valueOf(data2);

                        data(linear_pospago,btn_pospago,posRef,txt_metapospago,txt_ejecutadopospago,txt_faltapospago,txt_gappospago);
                        data(linear_prepago,btn_prepago,preRef,txt_metaprepago,txt_ejecutadoprepago,txt_faltaprepago,txt_gapprepago);
                        data(linear_ba,btn_ba,baRef,txt_metaba,txt_ejecutadoba,txt_faltaba,txt_gapba);
                        data(linear_mv,btn_mv,mvRef,txt_metamv,txt_ejecutadomv,txt_faltamv,txt_gapmv);
                        data(linear_napster,btn_napster,napRef,txt_metaNapster,txt_ejecutadoNapster,txt_faltaNapster,txt_gapNapster);
                        data(linear_tv,btn_tv,tvRef,txt_metatv,txt_ejecutadotv,txt_faltatv,txt_gaptv);
                        data(linear_lb,btn_lb,lbRef,txt_metalb,txt_ejecutadolb,txt_faltalb,txt_gaplb);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        btn_misMetas.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                  //roles.show(getFragmentManager(),"roles");
                final Dialog dialog=new Dialog(Global.this);
                dialog.setTitle("Selecciona tu rol");
                dialog.setContentView(R.layout.custom_dialog);
                dialog.show();
                final RadioButton rbt_estandar=(RadioButton)dialog.findViewById(R.id.rbt_estandar);
                final RadioButton rbt_ventas=(RadioButton)dialog.findViewById(R.id.rbt_ventas);
                final RadioButton rbt_fidelizacion=(RadioButton)dialog.findViewById(R.id.rbt_fidelizacion);
                final RadioButton rbt_pyme=(RadioButton)dialog.findViewById(R.id.rbt_pyme);
                Button btn_guardar=(Button)dialog.findViewById(R.id.btn_guardar);
                Button btn_cancelar=(Button)dialog.findViewById(R.id.btn_cancelar);
                btn_cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                btn_guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(rbt_estandar.isChecked()){
                            rol="estandar";
                        }else {
                            if (rbt_fidelizacion.isChecked()){
                                rol="fidelizacion";
                            }else {
                                if (rbt_pyme.isChecked()){
                                    rol="pyme";
                                }else {
                                    if (rbt_ventas.isChecked()){
                                        rol="ventas";
                                    }else{
                                        dialog.cancel();
                                    }
                                }
                            }
                        }
                        if (rol==null){
                            Toast.makeText(getApplicationContext(),"No seleccionaste ningun rol!",Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent=new Intent(Global.this,MetasUsuario.class);
                            intent.putExtra("rol",rol);
                            dialog.cancel();
                            startActivity(intent);
                        }

                    }
                });
            }
        });
    }

    private void data(LinearLayout linear, Button button, final DatabaseReference databaseReference, final TextView meta, final TextView ejecutado, final TextView falta, final TextView gap) {


        DatabaseReference dbrMeta=databaseReference.child("meta");
        dbrMeta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Long datameta = dataSnapshot.getValue(Long.class);
                meta.setText(String.valueOf(datameta));

                DatabaseReference dbrEjecutado=databaseReference.child("ejecutado");
                dbrEjecutado.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Long data=dataSnapshot.getValue(Long.class);
                        ejecutado.setText(String.valueOf(data));
                        falta.setText(resta(datameta.longValue(),data.longValue()));
                        gap.setText(gap(datameta.longValue(),data.longValue()));
                        if (negativo)
                            gap.setTextColor(getResources().getColor(R.color.colorAccent));
                        else
                            gap.setTextColor(getResources().getColor(R.color.colorVerde));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String resta(long m, long e){
        long resultado=m-e;
        return(String.valueOf(resultado));
    }
    private String gap(long m, long e){
        float meta, ejecutado;
        long res;
        meta=Float.valueOf(m);
        ejecutado=Float.valueOf(e);
        res=(long)(ejecutado-((meta/habiles)*transcurridos));
        if (res<0){
            negativo=true;
        }else{
            negativo=false;
        }
        String result= String.valueOf(res);
        return  result;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);



    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
