import java.util.Scanner;

public class coinInterface {
    static Scanner ler = new Scanner(System.in);
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

    /**   DESCRICAO DE introduzirMontante


    **/
    public static double introduzirMontante() {
        double valorRecebido = 0, valorTotal = 0;
        String resposta = "";
        do {
            System.out.println("Introduza as moedas");
            valorRecebido = lerMoeda();
            ler.nextLine();
            valorTotal = valorTotal + valorRecebido;
            System.out.println("Valor total introduzido: " + valorTotal);
            System.out.println("Deseja introduzir mais moedas? (sim ou nao)");
            resposta = ler.nextLine();
        } while (!resposta.equalsIgnoreCase("nao"));
        return valorTotal;
    }

    /**   DESCRICAO DE lerMoeda


    **/
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

    /**   DESCRICAO DE devolverTroco


    **/
    public static double devolverTroco(double montante, double valorIntroduzido) {
        return calcularTroco(montante, valorIntroduzido);
    }

    /**   DESCRICAO DE calcularTroco


    **/
    public static double calcularTroco(double montante, double valorIntroduzido) {
        return valorIntroduzido - montante;
    }
}
