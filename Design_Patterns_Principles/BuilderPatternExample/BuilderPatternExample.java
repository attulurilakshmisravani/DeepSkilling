class Computer {

    private String cpu;
    private String ram;
    private String storage;

    // Private constructor
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
    }

    public void display() {
        System.out.println("CPU: " + cpu);
        System.out.println("RAM: " + ram);
        System.out.println("Storage: " + storage);
    }

    // Static Nested Builder Class
    static class Builder {
        private String cpu;
        private String ram;
        private String storage;

        public Builder setCPU(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder setRAM(String ram) {
            this.ram = ram;
            return this;
        }

        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}

public class BuilderPatternExample {

    public static void main(String[] args) {

        Computer computer1 = new Computer.Builder()
                .setCPU("Intel i7")
                .setRAM("16 GB")
                .setStorage("512 GB SSD")
                .build();

        Computer computer2 = new Computer.Builder()
                .setCPU("AMD Ryzen 5")
                .setRAM("8 GB")
                .setStorage("1 TB HDD")
                .build();

        System.out.println("Computer 1:");
        computer1.display();

        System.out.println();

        System.out.println("Computer 2:");
        computer2.display();
    }
}