package com.fontoura.jabel.agenda;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fontoura.jabel.agenda.model.Prova;

public class DetalhesProvaFragment extends Fragment {


    private TextView campoMateria;
    private TextView campoData;
    private ListView listaTopicos;

    public DetalhesProvaFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);

        campoMateria = (TextView) view.findViewById(R.id.detalhas_prova_materia);
        campoData = (TextView) view.findViewById(R.id.detalhas_prova_data);
        listaTopicos = (ListView) view.findViewById(R.id.detalhes_prova_topico);

        Bundle parametros = getArguments();

        if(parametros != null) {
            Prova prova = (Prova) parametros.getSerializable("prova");
            populaCampos(prova);
        }
        return view;
    }


    public void populaCampos(Prova prova){
        ArrayAdapter<String> adapterTopicos = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, prova.getTopicos());

        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());
        listaTopicos.setAdapter(adapterTopicos);
    }
}
