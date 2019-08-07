package syssatelite.navegandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.prefs.Preferences;

import androidx.appcompat.app.AppCompatActivity;


public class Configurar extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurar);

        Context context = this;
        SharedPreferences sharedPreferences = context.getSharedPreferences("configuracoes", 0);
//        if(sharedPreferences.getString("grauDecimal","").equals("") || sharedPreferences.getString("grauMinuto","").equals("")|| sharedPreferences.getString("grauSegundo","").equals(""))
//        {
//            /*Se não estiver sido criado o banco, ele é criado agora para evitar que os dados sejam apagados*/
//            SharedPreferences sharedPreferences1 = getSharedPreferences("configuracoes",0);
//
//        }
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final Button botaoSalvar = findViewById(R.id.salvar);
         botaoSalvar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 RadioGroup formatoGroup = (RadioGroup)findViewById(R.id.formatoGroup);
                 switch (formatoGroup.getCheckedRadioButtonId())
                 {
                     case R.id.grauDecimal:
                         editor.putInt("grauDecimal",1);
                         break;
                     case R.id.grauMinuto:
                         editor.putInt("grauMinuto",1);
                         break;
                     case R.id.grauSegundo:
                         editor.putInt("grauSegundo",1);
                         break;
                 }
                editor.apply();
             }
         });
    }

//    public void verificaEscolha(View view) {
//        RadioButton rb1 = (RadioButton) findViewById(R.id.grauDecimal);
//        RadioButton rb2 = (RadioButton) findViewById(R.id.grauMinuto);
//        RadioButton rb3 = (RadioButton) findViewById(R.id.grauSegundo);
//        /*Declarando onde as informações serão salvas*/
//
//        boolean checked = ((RadioButton)view).isChecked();
//        switch (view.getId())
//        {
//            case R.id.grauDecimal:
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                break;
//            case R.id.grauMinuto:
//                break;
//            case R.id.grauSegundo:
//                break;
//        }
//    }
}
