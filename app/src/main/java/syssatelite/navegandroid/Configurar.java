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

//        if(sharedPreferences.getString("grauDecimal","").equals("") || sharedPreferences.getString("grauMinuto","").equals("")|| sharedPreferences.getString("grauSegundo","").equals(""))
//        {
//            /*Se não estiver sido criado o banco, ele é criado agora para evitar que os dados sejam apagados*/
//            SharedPreferences sharedPreferences1 = getSharedPreferences("configuracoes",0);
//
//        }
//        //recuperamos a senha e o login
//        senha_armazenada = (prefs1.getString ("senha", ""));
//        login_armazanado = (prefs1.getString ("login", ""));
//
//        if(senha_armazenada.equals("") && login_armazanado.equals(""))
//        {
//            //armazena os valores senha e login para serem inseridos no arquivo xml
//            edUser.putString("senha", "admin");
//            edUser.putString("login", "admin");
//            //armazenamos a senha e o login com admin no banco
//            edUser.apply();
//        }

        final Button botaoSalvar = findViewById(R.id.salvar);

         botaoSalvar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 System.out.println("sou bonito");
                 //shared para o usuario
                 SharedPreferences prefs1 = getSharedPreferences("configuracoes", Context.MODE_PRIVATE);
                 SharedPreferences.Editor  editor = prefs1.edit ();

                 RadioGroup formatoGroup = (RadioGroup)findViewById(R.id.formatoGroup);
                 switch (formatoGroup.getCheckedRadioButtonId())
                 {
                     case R.id.grauDecimal:
                         //armazena os valores
                         System.out.println("sou bonito");
                         editor.putString("grau" , "Decimal");
                         break;
                     case R.id.grauMinuto:
                         editor.putString("grau","Minuto");
                         break;
                     case R.id.grauSegundo:
                         editor.putString("grau","Segundo");
                         break;
                 }

                 //Amazena os valores
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
