package dialogos;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import grupodesarrollo.insitu.R;

/**
 * Created by GrupoDesarrollo on 23/03/2017.
 */

public class DialogValidacion extends DialogFragment implements TextView.OnEditorActionListener {

    public interface DialogCodigoAperturaListener{
        void onFinishEditDialog(String inputText);
    }

    private EditText codigoApertura;

    public DialogValidacion(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_codigo_activacion, container);
        codigoApertura = (EditText) view.findViewById(R.id.txt_codigo_apertura);
        getDialog().setTitle("Codigo Apertura");

        codigoApertura.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        codigoApertura.setOnEditorActionListener(this);

        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(EditorInfo.IME_ACTION_DONE==actionId){
            DialogCodigoAperturaListener activity = (DialogCodigoAperturaListener)getActivity();
            activity.onFinishEditDialog(codigoApertura.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }
}


