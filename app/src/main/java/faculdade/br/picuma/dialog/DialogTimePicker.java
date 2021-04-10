package faculdade.br.picuma.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import java.util.Calendar;

public class DialogTimePicker extends AppCompatDialogFragment {

    private TimePickerDialog.OnTimeSetListener listener;

    public int getCodRequest() {
        return codRequest;
    }

    public void setCodRequest(int codRequest) {
        this.codRequest = codRequest;
    }

    private int codRequest;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof TimePickerDialog.OnTimeSetListener)) {

            throw new IllegalArgumentException("Activity deve implementar TimePickerDialog.OnTimeSetListener");

        }

        this.listener = (TimePickerDialog.OnTimeSetListener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar now = Calendar.getInstance();

        int hour = now.get(Calendar.HOUR_OF_DAY);
        int min = now.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),listener, hour, min, true);
    }
}
