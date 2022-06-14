package net.openhft.chronicle.wire;

public class LongConversionExampleA {
    public static class House {
        @LongConversion(Base64LongConverter.class)
        long owner;

        public void owner(CharSequence owner) {
            this.owner = Base64LongConverter.INSTANCE.parse(owner);
        }
        @Override
        public String toString() {
            return "House{" +
                    "owner=" + owner +
                    '}';
        }
    }
    public static void main(String[] args) {
        House house = new House();
        house.owner("Bill");

        System.out.println(house);
    }
}



