package faculdade.br.picuma.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import faculdade.br.picuma.model.Endereco;
import faculdade.br.picuma.webService.SolicitarCep;

/**
 * Created by lucas on 17/04/2018.
 */

public class Util {

    private static Util instance;

    private Util() {

    }

    public static Util getInstance() {

        if (instance == null) {

            instance = new Util();

        }

        return instance;
    }


    public static String getHash(String dados) {

        String resultado = null;

        try {

            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = algorithm.digest(dados.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }

            resultado = hexString.toString();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return resultado;

    }

    public static String formatacaoCalendarDataToString(Calendar calendar) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return dateFormat.format(calendar.getTime());

    }

    public static Calendar getStringDataToCalender(String valor) {

        Calendar cal = Calendar.getInstance();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            cal.setTime(sdf.parse(valor));
        } catch (Exception e) {
            e.printStackTrace();

        }

        return cal;

    }

    public static Calendar getStringTimeToCalender(int hora, int minuto) {
        Calendar inicioExpCal = null;
        try {
            inicioExpCal = new GregorianCalendar();
            inicioExpCal.set(Calendar.HOUR_OF_DAY, hora);
            inicioExpCal.set(Calendar.MINUTE, minuto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inicioExpCal;
    }

    public static Endereco buscaCep(String dados) {

        SolicitarCep buscaCep = new SolicitarCep();
        Endereco endereco = null;

        try {
            endereco = buscaCep.execute(dados).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return endereco;
    }

    public static String formatacaoCalendarParaHoraMinuto(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date hora = calendar.getTime();
        return sdf.format(hora);
    }

//    List<Pessoa> registros = busca.consultaTudoPessoa();
//
//    JsonArray dados = new JsonArray();
//
//		for (Pessoa registro : registros) {
//
//        Gson conteudo = new Gson();
//
//        dados.add(conteudo.toJsonTree(registro));
//
//
//    }

    //        try {
//
//            areaAtuacao = buscaAreaAtuacao.execute().get();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }


}
