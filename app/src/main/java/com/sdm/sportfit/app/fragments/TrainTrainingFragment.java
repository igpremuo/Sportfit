package com.sdm.sportfit.app.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.sdm.sportfit.app.MainActivity;
import com.sdm.sportfit.app.R;
import com.sdm.sportfit.app.persistence.misSharedPreferences;

/**
 * Created by nacho on 1/04/14.
 */
public class TrainTrainingFragment extends Fragment {

    public static final String ANDAR = "andar";
    public static final String CORRER = "correr";
    public static final String BICI = "bici";
    public static final String DISTANCIA = "distancia";
    public static final String TIEMPO = "tiempo";


    MainActivity mMainActivity;
    //Views
    Button mButton;
    RadioGroup mRadioGroupDeporte;
    RadioGroup mRadioGroupAviso;

    String mDeporte;
    String mAviso;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_train_training, container, false);

        mButton = (Button) rootView.findViewById(R.id.train_training_button);
        mRadioGroupDeporte =(RadioGroup)rootView.findViewById(R.id.train_training_rg_tipo_deporte);
        mRadioGroupAviso =(RadioGroup)rootView.findViewById(R.id.train_training_rg_tipo_aviso);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        restaurarDatos();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deporte = mRadioGroupDeporte.getCheckedRadioButtonId();
                switch (deporte) {
                    case R.id.train_training_rb_andar:
                        mDeporte = ANDAR;
                        break;
                    case R.id.train_training_rb_correr:
                        mDeporte=CORRER;
                        break;
                    case R.id.train_training_rb_bicicleta:
                        mDeporte=BICI;
                        break;
                    default:
                        mDeporte=CORRER;
                        break;
                }
                int aviso =mRadioGroupAviso.getCheckedRadioButtonId();
                switch (aviso){
                    case R.id.train_training_rb_aviso_distancia:
                        mAviso = DISTANCIA;
                        break;
                    case R.id.train_training_rb_aviso_tiempo:
                        mAviso = TIEMPO;
                        break;
                    default:
                        mAviso=DISTANCIA;
                        break;
                }

                //Guarda configuracion de entreanmiento
                misSharedPreferences.guardarConfiguracionDeporte(getActivity(),mDeporte,mAviso);
                mMainActivity.openFragmentAtPos(0, 0);
            }
        });

    }
    private void restaurarDatos(){
        Bundle datos = misSharedPreferences.restaurarConfiguracionDeporte(getActivity());
        mDeporte = datos.getString(misSharedPreferences.TIPODEPORTE);
        mAviso =datos.getString(misSharedPreferences.TIPOAVISO);

        if(mDeporte.equals(ANDAR)){
            mRadioGroupDeporte.check(R.id.train_training_rb_andar);
        }
        else if(mDeporte.equals(CORRER)){
            mRadioGroupDeporte.check(R.id.train_training_rb_correr);
        }
        else  mRadioGroupDeporte.check(R.id.train_training_rb_bicicleta);

        if(mAviso.equals(DISTANCIA)){
            mRadioGroupAviso.check(R.id.train_training_rb_aviso_distancia);
        }
        else mRadioGroupAviso.check(R.id.train_training_rb_aviso_tiempo);
    }
}
