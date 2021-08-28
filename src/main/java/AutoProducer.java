public class AutoProducer extends Thread {
    static final int TIME_FOR_PRODUCING_CAR = 3000;

    final CarDealership carDealership;

    public AutoProducer(ThreadGroup group, String name, CarDealership carDealership) {
        super(group, name);
        this.carDealership = carDealership;
    }

    @Override
    public void run() {
        produceCar();
    }

    public void produceCar() {
        try {
            while (!isInterrupted()) {
                System.out.println("Производитель " + Thread.currentThread().getName() + " начал сборку автомобиля");
                Car car = new Car(this);
                Thread.sleep(TIME_FOR_PRODUCING_CAR);
                System.out.println("Производитель " + Thread.currentThread().getName() + " выпустил 1 автомобиль " + car);
                carDealership.deliverCar(car);
            }
        } catch (InterruptedException e) {
            System.out.println("Производитель остановил конвейр во время сборки автомобиля");
        }
    }
}

