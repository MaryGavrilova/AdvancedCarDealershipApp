import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarDealership {
    static final int TIME_FOR_PREPARING_CAR_FOR_SALE = 1000;
    static final int TIME_FOR_PAPER_WORK = 500;

    private final Lock lock = new ReentrantLock(true);
    private final Condition condition = lock.newCondition();

    List<Car> carsForSelling = new ArrayList<>();
    int soldCars = 0;

    public int getSoldCars() {
        return soldCars;
    }

    public void deliverCar(Car car) {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " доставил автомобиль " + car + " в автосалон ");
            carsForSelling.add(car);
            System.out.println("Автосалон готовит к продаже автомобиль");
            Thread.sleep(TIME_FOR_PREPARING_CAR_FOR_SALE);
            System.out.println("Автосалон: машина готова к продаже");
            condition.signalAll();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "Автосалон закрылся в момент подготовки машины к продаже");
        } finally {
            lock.unlock();
        }
    }

    public void sellCar() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " интересуется, если ли в наличии машины?");
            while (carsForSelling.size() == 0) {
                System.out.println("Автосалон отвечает " + Thread.currentThread().getName() + ": в наличии машин нет, ждите");
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + " оформляет покупку машины");
            Thread.sleep(TIME_FOR_PAPER_WORK);
            System.out.println(Thread.currentThread().getName() + " уехал на новой машине");
            carsForSelling.remove(0);
            soldCars++;
            System.out.println("*******************************");
            System.out.println("Количество проданных машин: " + soldCars);
            System.out.println("*******************************");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " покинул автосалон в момент оформления бумаг");
        } finally {
            lock.unlock();
        }
    }
}


