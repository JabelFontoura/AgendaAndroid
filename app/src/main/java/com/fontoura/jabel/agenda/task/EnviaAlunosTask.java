package com.fontoura.jabel.agenda.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.fontoura.jabel.agenda.converter.AlunoConverter;
import com.fontoura.jabel.agenda.dao.AlunoDAO;
import com.fontoura.jabel.agenda.model.Aluno;
import com.fontoura.jabel.agenda.web.WebClient;

import java.util.List;

/**
 * Created by Jabel on 12/25/2016.
 */

public class EnviaAlunosTask extends AsyncTask<Void, Void, String>{

    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.conveterParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPreExecute(){
        dialog = ProgressDialog.show(context, "Aguarde", "Enviando alunos...", true, true);
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_SHORT).show();
    }
}
