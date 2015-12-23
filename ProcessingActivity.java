what i posted use intent to display item from sdcard and send selected item on ftp server via AndFTP by intent...
P.S: u must install before AndFTP on ur smartphone

This is ProcessingActivity.java :
/** Master V copyright */
package com.Processing;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.Processing.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class ProcessingActivity extends Activity {
/** Called when the activity is first created. */
Button btnlaunch, btnrinex, btnnext, btncalc, btnaf, btnyuma, btndcb;

DatePicker d;
final int FILE_SELECT_CODE = 0;

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.main);
TabHost onglets = (TabHost) findViewById(R.id.onglet);
onglets.setup();

//Ajout du premier onglet
TabHost.TabSpec station= onglets.newTabSpec("Onglet_1");
station.setContent(R.id.Onglet1);
station.setIndicator("Station à traiter");
onglets.addTab(station);
//btnlaunch = (Button) findViewById(R.id.btnlaunch);
btnnext= (Button) findViewById(R.id.btnnext);


////J'attribue un écouteur à l'événement du bouton Suivant du premier Onglet\\\\\\

btnnext.setOnClickListener(new View.OnClickListener() {

@Override
public void onClick(View arg0) {
// TODO Auto-generated method stub
EditText numst=(EditText) findViewById(R.id.nbrest);
//EditText namst= (EditText) findViewById(R.id.nst);
//String nomst= namst.getText().toString();
String nbre= numst.getText().toString();
//Integer n= Integer.parseInt(nbre);

if(nbre.isEmpty()){

Toast.makeText(getBaseContext(),"Veuillez entrer le nombre de station à traiter! ",20000).show();

}
else{

TabHost onglet = (TabHost) findViewById(R.id.onglet);
onglet.setCurrentTab(1);

}


}
});



//Ajout du deuxième onglet
TabHost.TabSpec file= onglets.newTabSpec("Onglet_2");
file.setContent(R.id.Onglet2);
file.setIndicator("Fichier à traiter");
onglets.addTab(file);

btnrinex=(Button) findViewById(R.id.btnrinex);
btncalc=(Button) findViewById(R.id.btncalc);
btnyuma= (Button) findViewById(R.id.btnyuma);
btndcb=(Button) findViewById(R.id.btnbstl);

////J'attribue un écouteur à l'événement du bouton une fois cliqué au bouton Rinex\\\\\\
btnrinex.setOnClickListener(new View.OnClickListener() {

@Override
public void onClick(View arg0) {
// TODO Auto-generated method stub
getfilerinex(); 
}
});


////J'attribue un écouteur à l'événement du bouton une fois cliqué au bouton Yuma\\\\\\
btnyuma.setOnClickListener(new View.OnClickListener() {

@Override
public void onClick(View arg0) {
// TODO Auto-generated method stub
getfileyuma(); 
}
});


////J'attribue un écouteur à l'événement du bouton une fois cliqué au bouton Biais Satellite p1p2\\\\\\
btndcb.setOnClickListener(new View.OnClickListener() {

@Override
public void onClick(View arg0) {
// TODO Auto-generated method stub
getfiledcb();

}
});



////J'attribue un écouteur à l'événement du bouton lancement du deuxième Onglet\\\\\\

btncalc.setOnClickListener(new View.OnClickListener() {

@Override
public void onClick(View arg0) {
// TODO Auto-generated method stub
EditText namsti= (EditText) findViewById(R.id.nst);
String nomst= namsti.getText().toString();
EditText numsti=(EditText) findViewById(R.id.nbrest);
String nbre= numsti.getText().toString();
if(nomst.isEmpty()){
Toast.makeText(getBaseContext(), "Veuillez entrer le nom de la station!",Toast.LENGTH_SHORT).show();
}
else if(nbre.isEmpty()){
Toast.makeText(getBaseContext(), "Veuillez entrer le nombre de station à traiter!",Toast.LENGTH_SHORT).show();
}
else {
createfile();
decrem();
getfilerinex();

}
}
});

//Ajout du troisième onglet
TabHost.TabSpec res= onglets.newTabSpec("Onglet_3");
res.setContent(R.id.Onglet3);
res.setIndicator("Graphique");
onglets.addTab(res);

btnaf= (Button) findViewById(R.id.btnaf);

////J'attribue un écouteur à l'événement du bouton Suivant du deuxième Onglet\\\\\\

btnaf.setOnClickListener(new View.OnClickListener() {

@Override
public void onClick(View arg0) {
// TODO Auto-generated method stub
TabHost onglet = (TabHost) findViewById(R.id.onglet);
onglet.setCurrentTab(0);

}
});

onglets.setCurrentTab(0);

}

//La méthode pour la création du fichier.txt
public void createfile(){
EditText namstii= (EditText) findViewById(R.id.nst);
d= (DatePicker) findViewById(R.id.daterecept);
String nomst= namstii.getText().toString();
Integer month= d.getMonth()+1;
String dt= (d.getDayOfMonth()+" "+ month +" "+ d.getYear()).toString(); 


File f= new File("/sdcard/"+nomst+".txt") ;
String debut="dossier : 1=mesures, 2=almanac, 3=ionex 4=sortie 5=elev min 6=rec 7=mois annee 8=jour_deb jour_fin";

//Dialog dialog = ProgressDialog.show(this, 
// "Patientez svp...", "Envoi du fichier ...", true);
try {
f.createNewFile();
if(f.exists()){
FileWriter fichier= new FileWriter(f);
fichier.write(debut+"\n");
fichier.write(nomst+"\n");
fichier.write(dt);
fichier.flush();
fichier.close();
Toast.makeText(getBaseContext(), "Opération sur fichier réussie!",Toast.LENGTH_SHORT).show();


} 

else{
Toast.makeText(getBaseContext(), "Ehec d'écriture du fichier!",Toast.LENGTH_SHORT).show();
}
} catch (IOException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}



//La méthode pour la décrémentation du nombre de Satation à traiter
public void decrem(){

EditText namstiii= (EditText) findViewById(R.id.nst);
EditText numstii=(EditText) findViewById(R.id.nbrest);

String nbre= numstii.getText().toString();
Integer n= Integer.parseInt(nbre);
--n;
numstii.setText(n.toString());
namstiii.setText("");

Toast.makeText(getBaseContext(),"le nombre de station restante est: "+n,20000).show();
if(n<=0){ 
Toast.makeText(getBaseContext(),"Vous avez fini le traitement, Veuillez entrer le nom et le nombre de station",10000).show();

numstii.setText("");
namstiii.setText("");

TabHost onglet = (TabHost) findViewById(R.id.onglet);
onglet.setCurrentTab(0);
}

}

//La méthode pour parcourir et sélectionner le fichier rinex
public void getfilerinex() {


Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
intent.setType("*/*"); 
intent.addCategory(Intent.CATEGORY_OPENABLE);


try {
startActivityForResult(
Intent.createChooser(intent, "Choisir un fichier à uploader"),
FILE_SELECT_CODE);

} catch (android.content.ActivityNotFoundException ex) {
// Potentially direct the user to the Market with a Dialog
Toast.makeText(getBaseContext(), "Failure File Manager!",Toast.LENGTH_SHORT).show();
}


} 
//L'activité dediée à l'élément sélectionné de l'intent
@Override
public void onActivityResult(final int Fileselectcode, int resultCode, final Intent data) {
switch (Fileselectcode) {
case FILE_SELECT_CODE: 
if (resultCode == RESULT_OK) { 
// The URI of the selected file 
final Uri uri = data.getData();
// Get the path
final String r= uri.getPath();
Log.d("Selection", "File Path: " + r);

final Dialog t = ProgressDialog.show(this, 
"Patientez svp...", "Envoi du fichier ...", true);

try{
AlertDialog.Builder builder = new
AlertDialog.Builder(this);
builder.setMessage("Voulez-vous uploader le fichier:"+ r +"?")
.setCancelable(false)
.setPositiveButton("Oui", new
DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int id) {

Intent send = new Intent(Intent.ACTION_SEND, uri);
startActivity(send);
t.dismiss();

}
})
.setNegativeButton("Non", new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int id) {
dialog.cancel();
t.dismiss();
}
});
AlertDialog alert = builder.create();
alert.show(); 

}catch(android.content.ActivityNotFoundException ex){

}
break;
}
super.onActivityResult(Fileselectcode, resultCode, data);

}

}




//La méthode pour parcourir et sélectionner le fichier Yuma 
public void getfileyuma(){

Intent intention = new Intent(Intent.ACTION_GET_CONTENT); 
intention.setType("*/*"); 
intention.addCategory(Intent.CATEGORY_OPENABLE);


try {
startActivityForResult(
Intent.createChooser(intention, "Choisir un fichier à uploader"),
FILE_SELECT_CODE);

} catch (android.content.ActivityNotFoundException ex) {
// Potentially direct the user to the Market with a Dialog
Toast.makeText(getBaseContext(), "Failure File Manager!",Toast.LENGTH_SHORT).show();
} 
}

public void onActivityFile(final int Fileselectcode, int resultCode, final Intent data) {
switch (Fileselectcode) {
case FILE_SELECT_CODE: 
if (resultCode == RESULT_OK) { 
// The URI of the selected file 
final Uri ur = data.getData();
// Get the path
final String y= ur.getPath();
Log.d("Selection", "File Path: " + y);

final Dialog t = ProgressDialog.show(this, 
"Patientez svp...", "Envoi du fichier ...", true);

try{
AlertDialog.Builder builder = new
AlertDialog.Builder(this);
builder.setMessage("Voulez-vous uploader le fichier:"+ y +"?")
.setCancelable(false)
.setPositiveButton("Oui", new
DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int id) {



Intent send = new Intent(Intent.ACTION_SEND, ur);
startActivity(send);
t.dismiss();


}
})
.setNegativeButton("Non", new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int id) {
dialog.cancel();
t.dismiss();
}
});
AlertDialog alert = builder.create();
alert.show(); 

}catch(android.content.ActivityNotFoundException ex){

}
break;
}
super.onActivityResult(Fileselectcode, resultCode, data);

}

}

//La méthode pour parcourir et sélectionner le fichier DCB
public void getfiledcb(){

Intent intention = new Intent(Intent.ACTION_GET_CONTENT); 
intention.setType("*/*"); 
intention.addCategory(Intent.CATEGORY_OPENABLE);


try {
startActivityForResult(
Intent.createChooser(intention, "Choisir un fichier à uploader"),
FILE_SELECT_CODE);

} catch (android.content.ActivityNotFoundException ex) {
// Potentially direct the user to the Market with a Dialog
Toast.makeText(getBaseContext(), "Failure File Manager!",Toast.LENGTH_SHORT).show();
} 

}

public void onActivityFiledcb(final int Fileselectcode, int resultCode, final Intent data) {
switch (Fileselectcode) {
case FILE_SELECT_CODE: 
if (resultCode == RESULT_OK) { 
// The URI of the selected file 
final Uri u = data.getData();
// Get the path
final String dcb= u.getPath();
Log.d("Selection", "File Path: " + dcb);

final Dialog t = ProgressDialog.show(this, 
"Patientez svp...", "Envoi du fichier ...", true);

try{
AlertDialog.Builder builder = new
AlertDialog.Builder(this);
builder.setMessage("Voulez-vous uploader le fichier:"+ dcb +"?")
.setCancelable(false)
.setPositiveButton("Oui", new
DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int id) {


Intent send = new Intent(Intent.ACTION_SEND, u);
startActivity(send);
t.dismiss();

}
})
.setNegativeButton("Non", new DialogInterface.OnClickListener() {
public void onClick(DialogInterface dialog, int id) {
dialog.cancel();
t.dismiss();
}
});
AlertDialog alert = builder.create();
alert.show(); 

}catch(android.content.ActivityNotFoundException ex){

}
break;
}
super.onActivityResult(Fileselectcode, resultCode, data);

}

}

}

main.xml :

<?xml version="1.0" encoding="utf-8"?>
<TabHost xmlns:android="http://schemas.android.com/apk/res/android" 
android:id="@+id/onglet"
android:layout_width="fill_parent"
android:layout_height="fill_parent">

<ScrollView 
android:id="@+id/scrollview" 
android:layout_width="wrap_content"
android:layout_height="wrap_content">

<LinearLayout 
android:id="@+id/relativelayout" 
android:orientation="vertical"
android:layout_width="wrap_content"
android:layout_height="wrap_content">
<!-- TabWidget qui sert à afficher les onglets (boutons) -->
<TabWidget 
android:id="@android:id/tabs" 
android:layout_width="fill_parent"
android:layout_height="wrap_content" />

<!-- Tous les contenus des onglets--> 
<FrameLayout 
android:id="@android:id/tabcontent" 
android:layout_width="fill_parent"
android:layout_height="fill_parent"
>
<!-- Contenu de l’onglet N°1 -->
<RelativeLayout
android:orientation="vertical"
android:layout_width="fill_parent"
android:scrollbars="vertical"
android:id="@+id/Onglet1" android:layout_height="418dp">
<!-- réduction d'espace!!! 
<TextView
android:id="@+id/dot1"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text=" "
android:textStyle="bold"
android:textColor="#FF0000"
android:gravity="center"
android:layout_alignParentLeft="true"
/>

<TextView
android:id="@+id/dot4"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text=" "
android:textStyle="bold"
android:textColor="#FF0000"
android:gravity="center"
android:layout_below="@+id/dot1"
android:layout_alignParentLeft="true"
/>
-->



<TextView
android:id="@+id/n"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="Nombre de station : "
android:textStyle="bold"
android:textColor="#FF0000"
android:gravity="center"

android:layout_alignParentLeft="true"
/>
<EditText
android:id="@+id/nbrest"
android:hint="Nombre station"
android:inputType="numberDecimal" 
android:gravity="fill" 
android:layout_width="5dp" android:layout_height="36dp"
android:layout_toRightOf="@id/n"
android:layout_alignParentRight="true"/>

<TextView
android:id="@+id/d"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="Date de réception : "
android:textStyle="bold"
android:textColor="#FF0000"
android:layout_below="@+id/nbrest"
android:gravity="center" android:layout_alignParentLeft="true"
/>
<DatePicker
android:id="@+id/daterecept"
android:layout_gravity="fill_horizontal"
android:layout_width="wrap_content" android:layout_height="wrap_content"
android:layout_alignParentRight="true" android:layout_below="@+id/d"/>

<!-- Réduction d'espace!!!!! 
<TextView
android:id="@+id/dot5"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text=" "
android:textStyle="bold"
android:textColor="#FF0000"
android:gravity="center"
android:layout_below="@+id/daterecept"
android:layout_alignParentLeft="true"
/>
-->



<Button 
android:id="@+id/btnnext"
android:layout_width="wrap_content" 
android:text="Suivant" 
android:layout_height="36dp" 
android:layout_below="@+id/daterecept" 
android:layout_alignParentRight="true" 
android:layout_alignParentLeft="true"/>
<!--
<Button
android:id="@+id/btnlaunch"
android:layout_width="wrap_content"
android:text="Lancement"
android:layout_gravity="left"
android:gravity="center_horizontal" 
android:layout_height="36dp" 
android:layout_below="@+id/btnnext" android:layout_alignParentRight="true"
android:layout_alignParentLeft="true"/> android:layout_below="@+id/emi"-->
</RelativeLayout>

<!-- Contenu de l’onglet N°2 -->
<RelativeLayout
android:orientation="vertical"
android:layout_width="fill_parent"
android:layout_height="fill_parent"
android:scrollbars="vertical"
android:id="@+id/Onglet2">
<TextView
android:id="@+id/ns"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="Nom de la station : "
android:textStyle="bold"
android:textColor="#FF0000"
android:gravity="center" 
android:layout_alignParentLeft="true"
/>
<EditText
android:id="@+id/nst"
android:hint="Nom de la station"
android:inputType="text" 
android:gravity="fill" 
android:layout_width="wrap_content" android:layout_height="36dp"
android:layout_alignParentRight="true"/> 

<!--
<TextView
android:id="@+id/em"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text=" "
android:layout_below="@+id/nst"
android:textStyle="bold"
android:textColor="#FF0000"
android:gravity="center" android:layout_alignParentRight="true"
/> android:layout_below="@+id/u" -->


<TextView
android:id="@+id/r"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="Fichier Rinex : "
android:textStyle="bold"
android:textColor="#FF0000"
android:layout_below="@+id/nst"
android:gravity="center" android:layout_alignParentLeft="true"
/>

<ListView 
android:id="@+id/filelist"
android:layout_width="fill_parent" 
android:layout_height="fill_parent" 
android:text="Tous les fichiers"
android:drawSelectorOnTop="false"
/>

<Button
android:id="@+id/btnrinex"
android:layout_width="wrap_content"
android:text="Insérer"
android:layout_marginRight="25dip" android:layout_gravity="left" 
android:layout_height="36dp" 
android:gravity="center_horizontal" android:layout_below="@+id/nst"
android:layout_alignParentRight="true" 
android:layout_alignLeft="@+id/btnyuma"/>


<TextView
android:id="@+id/y"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="Fichier Yuma(almanach) : "
android:textStyle="bold"
android:textColor="#FF0000"
android:layout_below="@+id/btnrinex"
android:gravity="center" android:layout_alignParentLeft="true"
/>

<Button
android:id="@+id/btnyuma"
android:layout_width="wrap_content"
android:text="Insérer"
android:layout_marginRight="25dip" 
android:layout_gravity="left" android:layout_height="36dp" 
android:layout_toRightOf="@+id/y"
android:gravity="center_horizontal" 
android:layout_below="@+id/btnrinex" 
android:layout_alignParentRight="true"/>
<!-- Pas besoin de ça! 
<TextView
android:id="@+id/ion"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="Fichier Ionex/Codg : "
android:textStyle="bold"
android:textColor="#FF0000"
android:layout_below="@+id/btnyuma"
android:gravity="center"
android:layout_alignParentLeft="true"
/>

<Button
android:id="@+id/btnionex"
android:layout_width="wrap_content"
android:text="Télécharger"
android:layout_marginRight="25dip" android:layout_gravity="left" android:layout_height="36dp" 
android:gravity="top" android:layout_below="@+id/btnyuma"
android:layout_alignParentRight="true" android:layout_alignLeft="@+id/btnyuma"/>
-->

<TextView
android:id="@+id/bs"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="Fichier de biais récepteur : "
android:textStyle="bold"
android:textColor="#FF0000"
android:layout_below="@+id/btnyuma"
android:gravity="center" android:layout_alignParentLeft="true"
/>

<Button
android:id="@+id/btnbstl"
android:layout_width="120dp"
android:text="Insérer"
android:layout_marginRight="25dip" android:layout_gravity="left" 
android:layout_height="36dp" android:gravity="center_horizontal"
android:layout_below="@+id/btnyuma"
android:layout_alignParentRight="true"/>

<TextView
android:id="@+id/dot"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text=" "
android:textStyle="bold"
android:textColor="#FF0000"
android:gravity="center"
android:layout_below="@+id/btnbstl"
android:layout_alignParentLeft="true"
/>
<TextView
android:id="@+id/emi"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text=" "
android:textStyle="bold"
android:textColor="#FF0000"
android:layout_below="@+id/dot"
android:gravity="center" android:layout_alignParentRight="true"
/>

<Button
android:id="@+id/btncalc"
android:layout_width="wrap_content"
android:text="Lancement"
android:layout_marginRight="25dip" android:layout_gravity="left" 
android:layout_height="36dp" android:gravity="center_horizontal"
android:layout_below="@+id/emi"
android:layout_alignParentRight="true" 
android:layout_alignParentLeft="true"/>

</RelativeLayout>

<!-- Contenu de l’onglet N°3 -->
<RelativeLayout
android:orientation="vertical"
android:layout_width="fill_parent"
android:layout_height="fill_parent"
android:scrollbars="vertical"
android:id="@+id/Onglet3">

<ImageView
android:id="@+id/img"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:src="@drawable/icon"
>
</ImageView>
<Button
android:id="@+id/btnaf"
android:layout_width="wrap_content"
android:text="Afficher le résultat"
android:layout_alignParentRight="true" 
android:layout_alignParentLeft="true"
android:gravity="center_horizontal"
android:layout_marginRight="25dip" android:layout_gravity="left" 
android:layout_height="36dp"
android:layout_below="@+id/img"/>


</RelativeLayout>


<!-- --></FrameLayout>
</LinearLayout> 
</ScrollView>
</TabHost>
