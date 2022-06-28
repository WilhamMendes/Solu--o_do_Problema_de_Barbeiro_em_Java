import java.util.Random;

/*
 * O problema do barbeiro dorminhoco
 * Se não há clientes, o barbeiro adormece. Se a cadeira do barbeiro estiver livre, um cliente pode
 * ser atendido imediatamente (não senta na cadeira de espera).
 * O cliente espera pelo barbeiro se houver uma cadeira de espera vazia.
 * Se não tive onde sentar, o cliente vai embora.
 * 
 */



public class barbeiro implements Runnable{

    private int cadeira_De_Espera;      // Define quantas pessoas podem esperar pelo atendimento do barbeiro
    boolean cadeira_Ocupada = false;        // Se cadeira_Ocupada = true é pq ela está ocupada e se for false é pq está livre
    int[] clientes;     // Define um número aleatório de clientes
    boolean barbeiro_Dorme = false;     // Se barbeiro_Dorme = true é pq o barbeiro está dormindo e se for e false é pq ele está acordado e pode atender o cliente
    private String nome;    // Criando o nome da thread
    private int novos_Clientes;    // Gera random até numero máximo de clientes
    int total_clientes = 0;// inicializando a variável de clientes

    // Método construtor do barbeiro, ele inicializa o barbeiro com os parametros digitados
    barbeiro(String nome, int cadeira_De_Espera, int clientes){
        novos_Clientes = clientes;
        this.nome = nome;
        this.cadeira_De_Espera  = cadeira_De_Espera;
        System.out.println("O Barbeiro " + nome + " acabou de chegar no salão!");
    }

    // Método construtor que gera o numero dos clientes aleatoriamente e também cria o vetor de clientes
    public void clientes(){
        Random num_aleatorio = new Random();    // Gera um numero aleatório
        total_clientes = num_aleatorio.nextInt(novos_Clientes);     // total_clientes recebe o num. aleaório gerado
        clientes = new int[total_clientes]; // Passando o total_clientes pra declarar o tamanho do vetro de clientes
        System.out.println(clientes+ "acabaram de chegar.");

        // Prenchendo o vetor de clentes 
        for(int j = 0; total_clientes < clientes.length; j++)
            clientes[j] = j;
    }

    // Método que adormece o Barbeiro quando não há clientes a espera no salão
    public void barbeiro_Dorme() throws InterruptedException {
        System.out.println("O Barbeiro " +nome+ " está dormindo, porque não existe/m cliente/s no salão.");
        Thread.sleep(2000);     // Como não há clientes no salão a thread espera por 2 segundos
        System.out.println("No entando, a cadeira do Barbeiro " +nome+ " está livre.");
        
        // chamando o método que cria os clientes
        clientes();
    }

    // Método que acorda o Barbeiro se existem clientes esparando o atendimento do Barbeiro 
    public void barbeiro_Acorda() throws InterruptedException{
        
        // Se existem clinetes, ou seja, se total_clientes for dif. de zero entra no if
        if(total_clientes != 0){
            
            // Se tem mais de um cliente esperando e acadeira não está ocupada, entra no if
            if(total_clientes > 1 && cadeira_Ocupada == false){ 
                System.out.println("Entrou/Entraram " +total_clientes+ " cliente/s no salão.");
            } 
            else {  // Se tem mais de um cliente e tem cadeiras ocupadas é pq tem clientes esperando
                System.out.println("Existe/Existem " +total_clientes+ " cliente/s esperando pelo atendimento do Barbeiro " +nome+ ".");
            }

            // Se há clientes na espera, um deles já pode ser atendido
            System.out.println("Um cliente está sendo atendido pelo Barbeiro " + nome+ ".");
            total_clientes--;   // Um cliente foi atendido, logo decrementa o total_clientes
            cadeira_Ocupada = true;     //A acadeira do barbeiro está ocupada
            
            // Como o Barbeiro está atendendo um cliente, a thread espera (1s) para o atendimento terminar
            Thread.sleep(1000);
            
            // Se o total_clientes é maior que o número de cadeiras de espera
            if(total_clientes > cadeira_De_Espera){
                // verifica quantos clientes irão embora
                int n_cli_irao_embora = total_clientes -cadeira_De_Espera;
                // verifica quantos clientes estão esperando
                total_clientes = total_clientes - n_cli_irao_embora;
                
                // Enquanto o contador for menor que o número de clientes, o vetor é zerado nas posições
                for(int j = 0; j < clientes.length-1; j++){
                    clientes[j] = 0;
                }
                
                // Atualizaototal de clientes
                for(int j = 0; j < total_clientes; j++){
                    clientes[j] = j + 1;
                }

                // Imprime a quantidade de clientes que irão embora
                System.out.println(n_cli_irao_embora+ " clientes foram embora.");
                // Imprime a quantidade de clientes que ficaram
                System.out.println(total_clientes+ " clientes estão esperando pelo seu atendimento.");//
            }

            // Infroma o nome do Barbeiro que atendeu o cliente
            System.out.println("Um cliente já foi atendido pelo Barbeiro " +nome+ ".");


        // Se não, Se total_clientes for igual a 1
        } else if(total_clientes == 1){
            // Informa o nome do Barbeiro que está livre
            System.out.println("A cadeira do Barbeiro " +nome+ " está livre.");
            
            // Avisa que o Barbeiro vai atender
            System.out.println("Acadeira do Barbeiro " +nome+ " está ocupada e não existem clientes esperando!");
            
            Thread.sleep(1000);     // A thread esperar 1 segundo
            total_clientes--;       // Decrementa o total_clientes pq um cliente ja foi atendido pelo Barbeiro
            
            // Informa o nome de Barbeiro que atendeu o cliente
            System.out.println("Um cliente já foi atendido pelo Barbeiro " +nome+ ".");
        } else {
            // Avisa qual barbeiro está livre
            System.out.println("Acadeira do Barbeiro " +nome+ " está livre.");
            // Liberando as cadeiras de espera
            cadeira_Ocupada = false;
        }
    }


    // Método de execução da thread
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(true){    // Fica verificando todo o tempo
            if(total_clientes <= 0){ // Se total_clientes é menor ou igual a zero, isto é, se não tem clientes
                try {
                    barbeiro_Dorme();   // Chama o método que adormece o Barbeiro
                } catch (InterruptedException ex) {
                    System.out.println(ex);// Se der erro, mostra o erro
                }
            } else { // Se não, isto é, se tem cliente/es esperando
                try {
                    barbeiro_Acorda();  // Chama o método que acorda o Barbeiro
                } catch (InterruptedException ex) {
                    System.out.println(ex);// Se der erro, mostra o erro
                }
            }
        }
    }   
}
