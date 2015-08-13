package comparador.com.comparadordecarros.objects;

/**
 * Created by Carlos on 12/08/2015.
 */
public class Motores {

    private String img_path = "/";
    private String[][] motores = new String[5][5];

    public String[][] getMotores() {
        return motores;
    }


    public void setMotores(){
        motores[0][0] = "70";
        motores[0][1] = "1.0";
        motores[0][2] = "'flex'";
        motores[0][3] = "8";
        motores[0][4] = "13";

        motores[1][0] = "65";
        motores[1][1] = "1.0";
        motores[1][2] = "'flex'";
        motores[1][3] = "7.5";
        motores[1][4] = "12.5";

        motores[2][0] = "130";
        motores[2][1] = "2.0";
        motores[2][2] = "'gasolina'";
        motores[2][3] = "0";
        motores[2][4] = "9";

        motores[3][0] = "135";
        motores[3][1] = "1.6";
        motores[3][2] = "'flex'";
        motores[3][3] = "7.4";
        motores[3][4] = "9.4";

        motores[4][0] = "106";
        motores[4][1] = "1.4";
        motores[4][2] = "'flex'";
        motores[4][3] = "8";
        motores[4][4] = "10.5";
    }

}
