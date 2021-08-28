public class Car {

    protected String name;
    protected AutoProducer autoProducer;

    public Car(AutoProducer autoProducer) {
        name = "Model 3";
        this.autoProducer = autoProducer;
    }

    public String getName() {
        return name;
    }

    public AutoProducer getAutoProducer() {
        return autoProducer;
    }

    @Override
    public String toString() {
        return name;
    }
}
