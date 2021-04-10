package faculdade.br.picuma.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import java.util.Calendar;

public class DialogDatePicker extends AppCompatDialogFragment {

    private DatePickerDialog.OnDateSetListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof DatePickerDialog.OnDateSetListener)) {

            throw new IllegalArgumentException("Activity deve implementar DatePickerDialog.OnDateSetListener");

        }

        this.listener = (DatePickerDialog.OnDateSetListener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar now = Calendar.getInstance();

        return new DatePickerDialog(getActivity(),listener, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
    }
}
