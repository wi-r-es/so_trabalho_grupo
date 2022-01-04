import java.util.Scanner;

public class coinMaster implements Runnable
{

    private static double Custo;
    double recieved;
    private byte handfull; //variavel para controlar se esta a reter dinheiro

    public coinMaster(double access_cost){
        recieved=0;
        Custo= access_cost;
    }

    /**   DESCRICAO DE introduzirMontante


     **/
    public static void introduzirMontante(coinMaster user) { //provavelmente simplesmente inserir o valor logo de uma vez....

        double valorRecebido = 0;
        String t= "";

        valorRecebido = insertCoin();

        user.recieved += valorRecebido;
        if(user.recieved!=0){user.handfull=1;}
        else{ System.err.println(); }
    }


    private static double insertCoin(){
        Scanner ler = new Scanner(System.in);

        double x;
        System.out.println("Introduza as moedas");
        x= ler.nextDouble();
        return x;
    }


    @Override
    public void run() {
        System.out.println( "INICIAR MODULO INTRODUZIR MOEDA..." );
        Scanner ler = new Scanner(System.in);
        double troco = 0;
        String resposta2 = "";


        //receber valor para o montante total
        System.out.println("Valor a pagar:" + Custo);
        coinMaster.introduzirMontante(this);

        do {
            System.out.println("Deseja retirar o dinheiro? (Digite 'C' se desejar e qualquer outra para continuar para o pagamento)");
            resposta2 = ler.nextLine();
            if (resposta2.equalsIgnoreCase("C")) {
                this.recieved = 0;
                System.out.println("Cancelado com sucesso");
                break;
            } else {
                if (Custo > this.recieved) {
                    System.out.println("Valor a pagar: " + Custo);
                    System.out.println("Valor atual: " + this.recieved);
                    System.out.println("Valor insuficiente");
                    coinMaster.introduzirMontante(this);
                } else {
                    troco = this.recieved - Custo;
                    System.out.println("Troco: " + troco);
                    this.recieved = Custo;
                    System.out.println("Espere a sua vez para iniciar a lavagem ");
                }
            }
        }while (troco==0);
        System.out.println("Escolha a opção na janela aberta");
    }
}
