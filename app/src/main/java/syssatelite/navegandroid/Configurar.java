package syssatelite.navegandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.util.prefs.Preferences;

import androidx.appcompat.app.AppCompatActivity;


public class Configurar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurar);

        final Button botaoSalvar = findViewById(R.id.salvar);
        final Switch escolha = (Switch)findViewById(R.id.trafego);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("sou bonito");
                //shared para o usuario
                SharedPreferences prefs1 = getSharedPreferences("configuracoes", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs1.edit();

                RadioGroup formatoGroup = (RadioGroup) findViewById(R.id.formatoGroup);
                switch (formatoGroup.getCheckedRadioButtonId()) {
                    case R.id.grauDecimal:
                        //armazena os valores
                        editor.putString("grau", "Decimal");
                        break;
                    case R.id.grauMinuto:
                        editor.putString("grau", "Minuto");
                        break;
                    case R.id.grauSegundo:
                        editor.putString("grau", "Segundo");
                        break;
                }


                RadioGroup unidadeGroup = (RadioGroup) findViewById(R.id.unidadeGroup);
                switch (unidadeGroup.getCheckedRadioButtonId()) {
                    case R.id.km:
                        //armazena os valores
                        editor.putString("unidade", "km");
                        break;
                    case R.id.mph:
                        editor.putString("unidade", "mph");
                        break;
                }

                RadioGroup orientacaoGroup = (RadioGroup) findViewById(R.id.orientacaoGroup);
                switch (orientacaoGroup.getCheckedRadioButtonId()) {
                    case R.id.nenhuma:
                        //armazena os valores
                        editor.putString("orientacao", "nenhuma");
                        break;
                    case R.id.north:
                        editor.putString("orientacao", "north");
                        break;
                    case R.id.course:
                        editor.putString("orientacao", "course");
                        break;
                }

                RadioGroup tipoGroup = (RadioGroup) findViewById(R.id.tipoGroup);
                switch (tipoGroup.getCheckedRadioButtonId()) {

                    case R.id.vetorial:
                        //armazena os valores
                        editor.putString("tipo", "vetorial");
                        break;
                    case R.id.imagem:
                        editor.putString("tipo", "imagem");
                        break;

                }


                System.out.println("Sim paid");
                SharedPreferences prefs = getSharedPreferences("configuracoes", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = prefs.edit();
                if(escolha.isChecked())
                {
                    System.out.println("Sim pai");
                    editor2.putString("ligado", "sim");

                }else{

                    editor2.putString("ligado", "nao");
                }
                //Amazena os valores
                editor2.apply();
                editor.apply();
            }
        });

    }

}