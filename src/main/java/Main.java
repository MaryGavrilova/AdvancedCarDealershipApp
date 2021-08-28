public class Main {
    static final int NUMBER_OF_BUYERS = 2;
    static final int SALES_PLAN = 10;
    static final int TIME_OUT = 1000;

    public static void main(String[] args) {
        CarDealership carDealership = new CarDealership();
        AutoProducer autoProducer = new AutoProducer(null, "Tesla", carDealership);
        autoProducer.start();
        ThreadGroup buyers = new ThreadGroup("Покупатели");
        for (int i = 0; i < NUMBER_OF_BUYERS; i++) {
            new Buyer(buyers, "Покупатель " + (i + 1), carDealership).start();
        }
        while (carDealership.getSoldCars() < SALES_PLAN) {
            try {
                Thread.sleep(TIME_OUT);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        while (!autoProducer.isInterrupted()) {
            System.out.println("Автопроизводитель запустил процесс остановки производства");
            autoProducer.interrupt();
        }
        System.out.println("Автопроизводитель остановил работу производства");
        while (buyers.activeCount() > 0) {
            System.out.println("Прошу покупателей покинуть автосалон, осталось в автосалоне: " + buyers.activeCount() + " покупателей");
            buyers.interrupt();
        }
        System.out.println("Все покупатели покинули автосалон");
    }
}


