package first.tapdeveloper.dolarete;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

public class SobreEstaAppDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Hola!");
        builder.setMessage("Dolarete muestra las cotizaciones del dólar y euro de los principales bancos de Argentina, con un solo click podés acceder a la pagina oficial de cada uno de ellos para operar.\n" +
                "Los desarrolladores de esta aplicación no se responsabilizan por diferencias, omisiones o errores en los datos aquí mostrados, por eso recomendamos verificar la información con el banco el cual se quiera operar.\n" +
                "Los datos mostrados en la aplicación son de carácter informativo.\n" +
                "Al usar Dolarete acepta las presentes condiciones.\n");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}