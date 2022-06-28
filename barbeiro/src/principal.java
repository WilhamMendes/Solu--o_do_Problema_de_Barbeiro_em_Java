public class principal {

    public static void main(String[] args) {
        
        barbeiro barbeiro1 = new barbeiro("Wil", 2, 5);
        Thread threadbarbeiro1 = new Thread(barbeiro1);
        threadbarbeiro1.start();

    }
    
}
