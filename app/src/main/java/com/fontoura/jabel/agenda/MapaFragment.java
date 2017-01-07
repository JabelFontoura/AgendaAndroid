package com.fontoura.jabel.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.fontoura.jabel.agenda.dao.AlunoDAO;
import com.fontoura.jabel.agenda.model.Aluno;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by Jabel on 06/01/2017.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mapa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mapa = googleMap;

        LatLng posicaoEscola = pegeCordenadaDoEndereco("Rua Paulo Provenzano, Novo Hamburgo");

        if(posicaoEscola != null) {
            centralizaEm(posicaoEscola);
        }

        AlunoDAO dao = new AlunoDAO(getContext());

        for(Aluno aluno : dao.buscaAlunos()){
            LatLng cordenana = pegeCordenadaDoEndereco(aluno.getEndereco());

            if(cordenana != null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(cordenana);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }
        dao.close();

        new Localizador(getContext(), this);
    }

    public void centralizaEm(LatLng cordenada) {
        if(mapa != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(cordenada, 12);
            mapa.moveCamera(update);
        }
    }

    private LatLng pegeCordenadaDoEndereco(String endereco) {
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultados = geocoder.getFromLocationName(endereco, 1);

            if(!resultados.isEmpty()){
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
