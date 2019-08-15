package syssatelite.navegandroid;

import android.content.Context;
import android.content.Intent;
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

        
        // recupera (ou cria) uma instância do arquivo de preferencia do Android,
        // pelo seu nome/chave
        SharedPreferences pref = getSharedPreferences("configuracoes", MODE_PRIVATE);
        // recupera a informação
        String grau = pref.getString("grau", null);
        String unidade = pref.getString("unidade", null);
        String orientacao = pref.getString("orientacao", null);
        String tipo = pref.getString("tipo", null);
        String ligado = pref.getString("ligado", null);

        RadioGroup radioGroupGrauFormato = (RadioGroup) findViewById(R.id.formatoGroup);
        RadioGroup radioGroupGrauUnidade = (RadioGroup) findViewById(R.id.unidadeGroup);
        RadioGroup radioGroupGrauOrientacao = (RadioGroup) findViewById(R.id.orientacaoGroup);
        RadioGroup radioGroupGrauTipo = (RadioGroup) findViewById(R.id.tipoGroup);
        Switch switchTrafego = (Switch) findViewById(R.id.trafego);

        if(grau != null && unidade!= null && orientacao!= null && tipo!= null && ligado!= null)
        {
            System.out.println("Grau "+ grau);
            if(grau.equals("Decimal")){
                radioGroupGrauFormato.check(R.id.grauDecimal);
            }else if (grau.equals("Minuto")){
                radioGroupGrauFormato.check(R.id.grauMinuto);
            }else if(grau.equals("Segundo")){
                radioGroupGrauFormato.check(R.id.grauSegundo);
            }

            if(unidade.equals("km")){
                radioGroupGrauUnidade.check(R.id.km);
            }else if (unidade.equals("mph")){
                radioGroupGrauUnidade.check(R.id.mph);
            }

            if(orientacao.equals("Nenhuma")){
                radioGroupGrauOrientacao.check(R.id.nenhuma);
            }else if (orientacao.equals("North")){
                radioGroupGrauOrientacao.check(R.id.north);
            }else if(orientacao.equals("Course")){
                radioGroupGrauOrientacao.check(R.id.course);
            }

            if(tipo.equals("Vetorial")){
                radioGroupGrauTipo.check(R.id.vetorial);
            }else if (tipo.equals("Imagem")){
                radioGroupGrauTipo.check(R.id.imagem);
            }

            if(ligado.equals("Sim")){
                switchTrafego.setChecked(true);
            }else if (ligado.equals("Nao")){
                switchTrafego.setChecked(false);
            }
        }
        

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        editor.putString("orientacao", "Nenhuma");
                        break;
                    case R.id.north:
                        editor.putString("orientacao", "North");
                        break;
                    case R.id.course:
                        editor.putString("orientacao", "Course");
                        break;
                }

                RadioGroup tipoGroup = (RadioGroup) findViewById(R.id.tipoGroup);
                switch (tipoGroup.getCheckedRadioButtonId()) {

                    case R.id.vetorial:
                        //armazena os valores
                        editor.putString("tipo", "Vetorial");
                        break;
                    case R.id.imagem:
                        editor.putString("tipo", "Imagem");
                        break;

                }


                SharedPreferences prefs = getSharedPreferences("configuracoes", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = prefs.edit();
                if(escolha.isChecked())
                {
                    editor2.putString("ligado", "Sim");

                }else{

                    editor2.putString("ligado", "Nao");

                }
                //Amazena os valores
                editor2.apply();
                editor.apply();
                Toast toast = Toast.makeText(getApplicationContext(), "Configurações salvas!", Toast.LENGTH_SHORT);
                toast.show();
                Rediceriona();

            }
        });
    }

    public void Rediceriona()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}