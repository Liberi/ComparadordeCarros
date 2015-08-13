package comparador.com.comparadordecarros.objects;

/**
 * Created by Carlos on 12/08/2015.
 */
public class Carros {

    private String img_path = "/";
    private String[][] carros = new String[6][8];

    public String[][] getCarros() {
        return carros;
    }

    public void setCarros(){
        carros[0][0] = "'Gol'";
        carros[0][1] = "1";
        carros[0][2] = "'Volkswagen'";
        carros[0][3] = "2015";
        carros[0][4] = "25000";
        carros[0][5] = "325.00";
        carros[0][6] = "1850.00";
        carros[0][7] = "'" + img_path + "gol.png'";

        carros[1][0] = "'Palio'";
        carros[1][1] = "2";
        carros[1][2] = "'Fiat'";
        carros[1][3] = "2013";
        carros[1][4] = "19000";
        carros[1][5] = "199.00";
        carros[1][6] = "1200.00";
        carros[1][7] = "'" + img_path + "palio.jpg'";

        carros[2][0] = "'Fox'";
        carros[2][1] = "1";
        carros[2][2] = "'Volkswagen'";
        carros[2][3] = "2014";
        carros[2][4] = "22400";
        carros[2][5] = "250.00";
        carros[2][6] = "1550.00";
        carros[2][7] = "'" + img_path + "fox.jpg'";

        carros[3][0] = "'Corolla'";
        carros[3][1] = "3";
        carros[3][2] = "'Toyota'";
        carros[3][3] = "2010";
        carros[3][4] = "43000";
        carros[3][5] = "400.00";
        carros[3][6] = "2500.00";
        carros[3][7] = "'" + img_path + "corolla.jpg'";

        carros[4][0] = "'Focus'";
        carros[4][1] = "4";
        carros[4][2] = "'Ford'";
        carros[4][3] = "2014";
        carros[4][4] = "75000";
        carros[4][5] = "950.00";
        carros[4][6] = "3200.00";
        carros[4][7] = "'" + img_path + "focus.jpg'";

        carros[5][0] = "'Onix'";
        carros[5][1] = "4";
        carros[5][2] = "'Chevrolet'";
        carros[5][3] = "2015";
        carros[5][4] = "37790";
        carros[5][5] = "550.00";
        carros[5][6] = "2000.00";
        carros[5][7] = "'" + img_path + "onix.png'";
    }

}
