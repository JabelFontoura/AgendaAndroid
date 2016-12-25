package com.fontoura.jabel.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.fontoura.jabel.agenda.R;
import com.fontoura.jabel.agenda.dao.AlunoDAO;

/**
 * Created by Jabel on 12/22/2016.
 */

public class SMSReceiver extends BroadcastReceiver {

    @RequiresApi(api = VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");

        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);
        String telefone = sms.getDisplayOriginatingAddress();

        AlunoDAO dao = new AlunoDAO(context);

        if(dao.isAluno(telefone)){
            Toast.makeText(context, "Chegou um SMS de aluno", Toast.LENGTH_SHORT).show();
            MediaPlayer m = MediaPlayer.create(context, R.raw.msg);
            m.start();
        }

        dao.close();
    }
}
