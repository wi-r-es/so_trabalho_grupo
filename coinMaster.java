import java.util.Scanner;

public class coinMaster {

    private static double CASH;
    double recieved;
    private byte handfull; //variavel para controlar se esta a reter dinheiro

    public coinMaster(){
        CASH = 0;
        recieved=0;
        handfull=0;
    }



    /*
    public static void main(String[] args) {

        double montanteTotal = 14.5, valorTotal = 0, troco = 0;
        String resposta2 = "";
        //receber valor para o montante total
        System.out.println("Valor a pagar:" + montanteTotal);
        valorTotal = introduzirMontante();
        System.out.println("Deseja retirar o dinheiro? (Digite 'C' se desejar e qualquer outra para continuar para o pagamento)");
        resposta2 = ler.nextLine();
        if (resposta2.equalsIgnoreCase("C")){
            valorTotal = 0;
        } else {
            if (montanteTotal > valorTotal) {
                System.out.println("Valor insuficiente");
                valorTotal = introduzirMontante();
            } else {
                troco = devolverTroco(montanteTotal, valorTotal);
                System.out.println("Troco: " + troco);
            }
        }
    }
    */

    /**   DESCRICAO DE introduzirMontante


    **/
    public static void introduzirMontante(coinMaster user) {
        Scanner ler = new Scanner(System.in);
        double valorRecebido = 0;
        String resposta = "";
        do {
            double temp;
            System.out.println("Introduza as moedas");
            temp = ler.nextDouble();
            valorRecebido += temp;
            System.out.println("Valor total introduzido: " + valorRecebido);
            System.out.println("Deseja introduzir mais moedas? (sim(y) ou nao(n))");
            resposta = ler.nextLine();
        } while (!resposta.equalsIgnoreCase("n"));
        user.recieved = valorRecebido;
        if(user.recieved!=0){user.handfull=1;}
        else{ System.err.println();}
    }

    /**   DESCRICAO DE devolverTroco


    **/
    public static void devolverTroco(double montante, double valorIntroduzido) {

    }



    /**   DESCRICAO DE cancelOperation


    **/
    public static void cancelOperation(coinMaster user){
      //codigo de cancelamento de operacoes


    }

    /**   DESCRICAO DE lerMoeda


    **/
    /*
    public static double lerMoeda() {
        double temp = 0;
        int count = 0;
        do {
            if (count != 0){
                System.out.println("Valor não é válido, introduza um valor válido");
            }
            temp = ler.nextDouble();
            count++;
        } while (temp != 0.01 && temp != 0.02 && temp != 0.05 && temp != 0.10 && temp != 0.2 && temp != 0.5 && temp != 1.0 && temp != 2.0);
        return temp;
    }
    */
}
